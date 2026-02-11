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

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.notification.v1.MessageTypeCatalogMapper;
import com.firefly.masters.core.utils.TestPaginationRequest;
import com.firefly.masters.interfaces.dtos.notification.v1.MessageTypeCatalogDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.notification.v1.MessageTypeCatalog;
import com.firefly.masters.models.repositories.notification.v1.MessageTypeCatalogRepository;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MessageTypeCatalogServiceImplTest {

    @Mock
    private MessageTypeCatalogRepository repository;

    @Mock
    private MessageTypeCatalogMapper mapper;

    @InjectMocks
    private MessageTypeCatalogServiceImpl service;

    private MessageTypeCatalog entity;
    private MessageTypeCatalogDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testTypeId;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        
        testTypeId = UUID.randomUUID();

        entity = new MessageTypeCatalog();
        entity.setTypeId(testTypeId);
        entity.setTypeCode("EMAIL");
        entity.setTypeName("Email Message");
        entity.setDescription("Notification messages sent via email");
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setDateCreated(now);
        entity.setDateUpdated(now);

        dto = new MessageTypeCatalogDTO();
        dto.setTypeId(testTypeId);
        dto.setTypeCode("EMAIL");
        dto.setTypeName("Email Message");
        dto.setDescription("Notification messages sent via email");
        dto.setStatus(StatusEnum.ACTIVE);
        dto.setDateCreated(now);
        dto.setDateUpdated(now);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listMessageTypes_ShouldReturnPaginatedResponse() {
        // Arrange
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(MessageTypeCatalog.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<MessageTypeCatalogDTO>> result = service.listMessageTypes(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> 
                        response.getContent().size() == 1 &&
                        response.getTotalElements() == 1L &&
                        response.getContent().get(0).getTypeCode().equals("EMAIL"))
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(MessageTypeCatalog.class));
    }

    @Test
    void createMessageType_ShouldReturnCreatedMessageType() {
        // Arrange
        when(mapper.toEntity(any(MessageTypeCatalogDTO.class))).thenReturn(entity);
        when(repository.save(any(MessageTypeCatalog.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(MessageTypeCatalog.class))).thenReturn(dto);

        // Act
        Mono<MessageTypeCatalogDTO> result = service.createMessageType(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(MessageTypeCatalogDTO.class));
        verify(repository).save(any(MessageTypeCatalog.class));
        verify(mapper).toDTO(any(MessageTypeCatalog.class));
    }

    @Test
    void getMessageType_ShouldReturnMessageType_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(MessageTypeCatalog.class))).thenReturn(dto);

        // Act
        Mono<MessageTypeCatalogDTO> result = service.getMessageType(testTypeId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(MessageTypeCatalog.class));
    }

    @Test
    void getMessageTypeByCode_ShouldReturnMessageType_WhenFound() {
        // Arrange
        when(repository.findByTypeCode(anyString())).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(MessageTypeCatalog.class))).thenReturn(dto);

        // Act
        Mono<MessageTypeCatalogDTO> result = service.getMessageTypeByCode("EMAIL");

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findByTypeCode(anyString());
        verify(mapper).toDTO(any(MessageTypeCatalog.class));
    }

    @Test
    void updateMessageType_ShouldReturnUpdatedMessageType_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(MessageTypeCatalogDTO.class))).thenReturn(entity);
        when(repository.save(any(MessageTypeCatalog.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(MessageTypeCatalog.class))).thenReturn(dto);

        // Act
        Mono<MessageTypeCatalogDTO> result = service.updateMessageType(testTypeId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(MessageTypeCatalogDTO.class));
        verify(repository).save(any(MessageTypeCatalog.class));
        verify(mapper).toDTO(any(MessageTypeCatalog.class));
    }

    @Test
    void deleteMessageType_ShouldDeleteMessageType_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(repository.delete(any(MessageTypeCatalog.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteMessageType(testTypeId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository).delete(any(MessageTypeCatalog.class));
    }
}
