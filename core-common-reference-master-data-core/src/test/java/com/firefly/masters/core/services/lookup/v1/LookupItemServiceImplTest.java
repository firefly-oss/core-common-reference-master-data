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


package com.firefly.masters.core.services.lookup.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import com.firefly.masters.core.utils.TestPaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.lookup.v1.LookupItemMapper;
import com.firefly.masters.interfaces.dtos.lookup.v1.LookupItemDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.lookup.v1.LookupItem;
import com.firefly.masters.models.repositories.lookup.v1.LookupItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LookupItemServiceImplTest {

    @Mock
    private LookupItemRepository repository;

    @Mock
    private LookupItemMapper mapper;

    @InjectMocks
    private LookupItemServiceImpl service;

    private LookupItem entity;
    private LookupItemDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testItemId;
    private UUID testDomainId;

    @BeforeEach
    void setUp() {
        // Setup test data
        testItemId = UUID.randomUUID();
        testDomainId = UUID.randomUUID();

        entity = new LookupItem();
        entity.setItemId(testItemId);
        entity.setDomainId(testDomainId);
        entity.setItemCode("MAIN");
        entity.setItemLabelDefault("Main Branch");
        entity.setItemDesc("Main bank branch with full services");
        entity.setParentItemId(null);
        entity.setSortOrder(1);
        entity.setEffectiveFrom(LocalDate.now());
        entity.setEffectiveTo(null);
        entity.setIsCurrent(true);
        entity.setExtraJson(null);
        entity.setTenantId(null);
        entity.setStatus(StatusEnum.ACTIVE);

        dto = new LookupItemDTO();
        dto.setItemId(testItemId);
        dto.setDomainId(testDomainId);
        dto.setItemCode("MAIN");
        dto.setItemLabelDefault("Main Branch");
        dto.setItemDesc("Main bank branch with full services");
        dto.setParentItemId(null);
        dto.setSortOrder(1);
        dto.setEffectiveFrom(LocalDate.now());
        dto.setEffectiveTo(null);
        dto.setIsCurrent(true);
        dto.setExtraJson(null);
        dto.setTenantId(null);
        dto.setStatus(StatusEnum.ACTIVE);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listItems_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(LookupItem.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<LookupItemDTO>> result = service.listItems(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getItemId().equals(testItemId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(LookupItem.class));
    }

    @Test
    void getItemsByDomain_ShouldReturnItems() {
        // Arrange
        when(repository.findByDomainId(any(UUID.class))).thenReturn(Flux.just(entity));
        when(mapper.toDTO(any(LookupItem.class))).thenReturn(dto);

        // Act
        Flux<LookupItemDTO> result = service.getItemsByDomain(testDomainId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findByDomainId(any(UUID.class));
        verify(mapper).toDTO(any(LookupItem.class));
    }

    @Test
    void createItem_ShouldReturnCreatedEntity() {
        // Arrange
        when(mapper.toEntity(any(LookupItemDTO.class))).thenReturn(entity);
        when(repository.save(any(LookupItem.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LookupItem.class))).thenReturn(dto);

        // Act
        Mono<LookupItemDTO> result = service.createItem(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(LookupItemDTO.class));
        verify(repository).save(any(LookupItem.class));
        verify(mapper).toDTO(any(LookupItem.class));
    }

    @Test
    void getItem_ShouldReturnEntityWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LookupItem.class))).thenReturn(dto);

        // Act
        Mono<LookupItemDTO> result = service.getItem(testItemId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(LookupItem.class));
    }

    @Test
    void getItem_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<LookupItemDTO> result = service.getItem(testItemId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toDTO(any(LookupItem.class));
    }

    @Test
    void updateItem_ShouldReturnUpdatedEntityWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(LookupItemDTO.class))).thenReturn(entity);
        when(repository.save(any(LookupItem.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LookupItem.class))).thenReturn(dto);

        // Act
        Mono<LookupItemDTO> result = service.updateItem(testItemId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(LookupItemDTO.class));
        verify(repository).save(any(LookupItem.class));
        verify(mapper).toDTO(any(LookupItem.class));
    }

    @Test
    void updateItem_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<LookupItemDTO> result = service.updateItem(testItemId, dto);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(LookupItemDTO.class));
        verify(repository, never()).save(any(LookupItem.class));
        verify(mapper, never()).toDTO(any(LookupItem.class));
    }

    @Test
    void deleteItem_ShouldDeleteWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(repository.delete(any(LookupItem.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteItem(testItemId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository).delete(any(LookupItem.class));
    }

    @Test
    void deleteItem_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteItem(testItemId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository, never()).delete(any(LookupItem.class));
    }
}
