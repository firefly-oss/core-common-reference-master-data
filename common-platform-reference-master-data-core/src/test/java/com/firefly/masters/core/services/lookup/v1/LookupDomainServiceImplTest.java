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
import com.firefly.masters.core.mappers.lookup.v1.LookupDomainMapper;
import com.firefly.masters.interfaces.dtos.lookup.v1.LookupDomainDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.lookup.v1.LookupDomain;
import com.firefly.masters.models.repositories.lookup.v1.LookupDomainRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LookupDomainServiceImplTest {

    @Mock
    private LookupDomainRepository repository;

    @Mock
    private LookupDomainMapper mapper;

    @InjectMocks
    private LookupDomainServiceImpl service;

    private LookupDomain entity;
    private LookupDomainDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testDomainId;

    @BeforeEach
    void setUp() {
        // Setup test data
        testDomainId = UUID.randomUUID();

        entity = new LookupDomain();
        entity.setDomainId(testDomainId);
        entity.setDomainCode("BRANCH_TYPE");
        entity.setDomainName("Branch Types");
        entity.setDomainDesc("Types of bank branches");
        entity.setParentDomainId(null);
        entity.setMultiselectAllowed(false);
        entity.setHierarchyAllowed(false);
        entity.setTenantOverridable(true);
        entity.setExtraJson(null);
        entity.setTenantId(null);
        entity.setStatus(StatusEnum.ACTIVE);

        dto = new LookupDomainDTO();
        dto.setDomainId(testDomainId);
        dto.setDomainCode("BRANCH_TYPE");
        dto.setDomainName("Branch Types");
        dto.setDomainDesc("Types of bank branches");
        dto.setParentDomainId(null);
        dto.setMultiselectAllowed(false);
        dto.setHierarchyAllowed(false);
        dto.setTenantOverridable(true);
        dto.setExtraJson(null);
        dto.setTenantId(null);
        dto.setStatus(StatusEnum.ACTIVE);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listDomains_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(LookupDomain.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<LookupDomainDTO>> result = service.listDomains(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getDomainId().equals(testDomainId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(LookupDomain.class));
    }

    @Test
    void createDomain_ShouldReturnCreatedEntity() {
        // Arrange
        when(mapper.toEntity(any(LookupDomainDTO.class))).thenReturn(entity);
        when(repository.save(any(LookupDomain.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LookupDomain.class))).thenReturn(dto);

        // Act
        Mono<LookupDomainDTO> result = service.createDomain(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(LookupDomainDTO.class));
        verify(repository).save(any(LookupDomain.class));
        verify(mapper).toDTO(any(LookupDomain.class));
    }

    @Test
    void getDomain_ShouldReturnEntityWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LookupDomain.class))).thenReturn(dto);

        // Act
        Mono<LookupDomainDTO> result = service.getDomain(testDomainId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(LookupDomain.class));
    }

    @Test
    void getDomain_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<LookupDomainDTO> result = service.getDomain(testDomainId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toDTO(any(LookupDomain.class));
    }

    @Test
    void updateDomain_ShouldReturnUpdatedEntityWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(LookupDomainDTO.class))).thenReturn(entity);
        when(repository.save(any(LookupDomain.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LookupDomain.class))).thenReturn(dto);

        // Act
        Mono<LookupDomainDTO> result = service.updateDomain(testDomainId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(LookupDomainDTO.class));
        verify(repository).save(any(LookupDomain.class));
        verify(mapper).toDTO(any(LookupDomain.class));
    }

    @Test
    void updateDomain_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<LookupDomainDTO> result = service.updateDomain(testDomainId, dto);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(LookupDomainDTO.class));
        verify(repository, never()).save(any(LookupDomain.class));
        verify(mapper, never()).toDTO(any(LookupDomain.class));
    }

    @Test
    void deleteDomain_ShouldDeleteWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(repository.delete(any(LookupDomain.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteDomain(testDomainId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository).delete(any(LookupDomain.class));
    }

    @Test
    void deleteDomain_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteDomain(testDomainId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository, never()).delete(any(LookupDomain.class));
    }
}
