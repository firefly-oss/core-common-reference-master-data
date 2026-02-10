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


package com.firefly.masters.core.services.relationships.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import com.firefly.masters.core.utils.TestPaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.relationships.v1.RelationshipTypeMasterMapper;
import com.firefly.masters.interfaces.dtos.relationships.v1.RelationshipTypeMasterDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.relationships.v1.RelationshipTypeMaster;
import com.firefly.masters.models.repositories.relationships.v1.RelationshipTypeMasterRepository;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RelationshipTypeMasterServiceImplTest {

    @Mock
    private RelationshipTypeMasterRepository repository;

    @Mock
    private RelationshipTypeMasterMapper mapper;

    @InjectMocks
    private RelationshipTypeMasterServiceImpl service;

    private RelationshipTypeMaster entity;
    private RelationshipTypeMasterDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testRelationshipTypeId;

    @BeforeEach
    void setUp() {
        // Setup test data
        testRelationshipTypeId = UUID.randomUUID();

        entity = new RelationshipTypeMaster();
        entity.setRelationshipTypeId(testRelationshipTypeId);
        entity.setCode("BENEFICIARY");
        entity.setDescription("Beneficiary");
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setDateCreated(LocalDateTime.now());
        entity.setDateUpdated(LocalDateTime.now());

        dto = new RelationshipTypeMasterDTO();
        dto.setRelationshipTypeId(testRelationshipTypeId);
        dto.setCode("BENEFICIARY");
        dto.setDescription("Beneficiary");
        dto.setStatus(StatusEnum.ACTIVE);
        dto.setDateCreated(LocalDateTime.now());
        dto.setDateUpdated(LocalDateTime.now());

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listRelationshipTypes_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(RelationshipTypeMaster.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<RelationshipTypeMasterDTO>> result = service.listRelationshipTypes(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getRelationshipTypeId().equals(testRelationshipTypeId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(RelationshipTypeMaster.class));
    }

    @Test
    void createRelationshipType_ShouldReturnCreatedEntity() {
        // Arrange
        when(mapper.toEntity(any(RelationshipTypeMasterDTO.class))).thenReturn(entity);
        when(repository.save(any(RelationshipTypeMaster.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(RelationshipTypeMaster.class))).thenReturn(dto);

        // Act
        Mono<RelationshipTypeMasterDTO> result = service.createRelationshipType(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(RelationshipTypeMasterDTO.class));
        verify(repository).save(any(RelationshipTypeMaster.class));
        verify(mapper).toDTO(any(RelationshipTypeMaster.class));
    }

    @Test
    void getRelationshipType_ShouldReturnEntityWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(RelationshipTypeMaster.class))).thenReturn(dto);

        // Act
        Mono<RelationshipTypeMasterDTO> result = service.getRelationshipType(testRelationshipTypeId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(RelationshipTypeMaster.class));
    }

    @Test
    void getRelationshipType_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<RelationshipTypeMasterDTO> result = service.getRelationshipType(testRelationshipTypeId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toDTO(any(RelationshipTypeMaster.class));
    }

    @Test
    void updateRelationshipType_ShouldReturnUpdatedEntityWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(RelationshipTypeMasterDTO.class))).thenReturn(entity);
        when(repository.save(any(RelationshipTypeMaster.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(RelationshipTypeMaster.class))).thenReturn(dto);

        // Act
        Mono<RelationshipTypeMasterDTO> result = service.updateRelationshipType(testRelationshipTypeId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(RelationshipTypeMasterDTO.class));
        verify(repository).save(any(RelationshipTypeMaster.class));
        verify(mapper).toDTO(any(RelationshipTypeMaster.class));
    }

    @Test
    void updateRelationshipType_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<RelationshipTypeMasterDTO> result = service.updateRelationshipType(testRelationshipTypeId, dto);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(RelationshipTypeMasterDTO.class));
        verify(repository, never()).save(any(RelationshipTypeMaster.class));
        verify(mapper, never()).toDTO(any(RelationshipTypeMaster.class));
    }

    @Test
    void deleteRelationshipType_ShouldDeleteWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(repository.delete(any(RelationshipTypeMaster.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteRelationshipType(testRelationshipTypeId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository).delete(any(RelationshipTypeMaster.class));
    }

    @Test
    void deleteRelationshipType_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteRelationshipType(testRelationshipTypeId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository, never()).delete(any(RelationshipTypeMaster.class));
    }
}
