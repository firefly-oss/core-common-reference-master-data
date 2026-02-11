/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.masters.core.services.notification.v1;

import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.notification.v1.NotificationMessageCatalogMapper;
import com.firefly.masters.core.utils.TestPaginationRequest;
import com.firefly.masters.interfaces.dtos.notification.v1.MessageTypeCatalogDTO;
import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageCatalogDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.notification.v1.NotificationMessageCatalog;
import com.firefly.masters.models.repositories.notification.v1.NotificationMessageCatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class NotificationMessageCatalogServiceImplTest {

    @Mock
    private NotificationMessageCatalogRepository repository;

    @Mock
    private NotificationMessageCatalogMapper mapper;

    @InjectMocks
    private NotificationMessageCatalogServiceImpl service;

    private NotificationMessageCatalog entity;
    private NotificationMessageCatalogDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testMessageId;
    private UUID testTypeId;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        testMessageId = UUID.randomUUID();
        testTypeId = UUID.randomUUID();

        entity = new NotificationMessageCatalog();
        entity.setMessageId(testMessageId);
        entity.setMessageCode("LOW_BALANCE");
        entity.setTypeId(testTypeId); // Reference to EMAIL message type
        entity.setEventType("ACCOUNT_BALANCE");
        entity.setDescription("Notification for low account balance");
        entity.setDefaultSubject("Low Balance Alert");
        entity.setDefaultMessage("Your account balance is below {threshold}");
        entity.setParameters("{\"threshold\":\"number\"}");
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setDateCreated(now);
        entity.setDateUpdated(now);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("threshold", "number");

        // Create MessageTypeCatalogDTO for EMAIL type
        MessageTypeCatalogDTO messageTypeDTO = new MessageTypeCatalogDTO();
        messageTypeDTO.setTypeId(testTypeId);
        messageTypeDTO.setTypeCode("EMAIL");
        messageTypeDTO.setTypeName("Email Message");
        messageTypeDTO.setDescription("Notification messages sent via email");
        messageTypeDTO.setStatus(StatusEnum.ACTIVE);
        messageTypeDTO.setDateCreated(now);
        messageTypeDTO.setDateUpdated(now);

        dto = new NotificationMessageCatalogDTO();
        dto.setMessageId(testMessageId);
        dto.setMessageCode("LOW_BALANCE");
        dto.setTypeId(testTypeId);
        dto.setMessageType(messageTypeDTO);
        dto.setEventType("ACCOUNT_BALANCE");
        dto.setDescription("Notification for low account balance");
        dto.setDefaultSubject("Low Balance Alert");
        dto.setDefaultMessage("Your account balance is below {threshold}");
        dto.setParameters(parameters);
        dto.setStatus(StatusEnum.ACTIVE);
        dto.setDateCreated(now);
        dto.setDateUpdated(now);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listNotificationMessages_ShouldReturnPaginatedResponse() {
        // Arrange
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(NotificationMessageCatalog.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<NotificationMessageCatalogDTO>> result = service.listNotificationMessages(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getContent().size() == 1 &&
                        response.getTotalElements() == 1L &&
                        response.getContent().get(0).getMessageCode().equals("LOW_BALANCE"))
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(NotificationMessageCatalog.class));
    }

    @Test
    void createNotificationMessage_ShouldReturnCreatedMessage() {
        // Arrange
        when(mapper.toEntity(any(NotificationMessageCatalogDTO.class))).thenReturn(entity);
        when(repository.save(any(NotificationMessageCatalog.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(NotificationMessageCatalog.class))).thenReturn(dto);

        // Act
        Mono<NotificationMessageCatalogDTO> result = service.createNotificationMessage(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(NotificationMessageCatalogDTO.class));
        verify(repository).save(any(NotificationMessageCatalog.class));
        verify(mapper).toDTO(any(NotificationMessageCatalog.class));
    }

    @Test
    void getNotificationMessage_ShouldReturnMessage_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(NotificationMessageCatalog.class))).thenReturn(dto);

        // Act
        Mono<NotificationMessageCatalogDTO> result = service.getNotificationMessage(testMessageId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(NotificationMessageCatalog.class));
    }

    @Test
    void getNotificationMessage_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<NotificationMessageCatalogDTO> result = service.getNotificationMessage(testMessageId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().contains("Notification message not found"))
                .verify();

        verify(repository).findById(any(UUID.class));
    }

    @Test
    void getNotificationMessageByCode_ShouldReturnMessage_WhenFound() {
        // Arrange
        when(repository.findByMessageCode(anyString())).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(NotificationMessageCatalog.class))).thenReturn(dto);

        // Act
        Mono<NotificationMessageCatalogDTO> result = service.getNotificationMessageByCode("LOW_BALANCE");

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findByMessageCode(anyString());
        verify(mapper).toDTO(any(NotificationMessageCatalog.class));
    }

    @Test
    void updateNotificationMessage_ShouldReturnUpdatedMessage_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(NotificationMessageCatalogDTO.class))).thenReturn(entity);
        when(repository.save(any(NotificationMessageCatalog.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(NotificationMessageCatalog.class))).thenReturn(dto);

        // Act
        Mono<NotificationMessageCatalogDTO> result = service.updateNotificationMessage(testMessageId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(NotificationMessageCatalogDTO.class));
        verify(repository).save(any(NotificationMessageCatalog.class));
        verify(mapper).toDTO(any(NotificationMessageCatalog.class));
    }

    @Test
    void deleteNotificationMessage_ShouldDeleteMessage_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(repository.delete(any(NotificationMessageCatalog.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteNotificationMessage(testMessageId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository).delete(any(NotificationMessageCatalog.class));
    }
}
