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


package com.firefly.masters.core.services.identitydocument.v1;

import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.identitydocument.v1.IdentityDocumentCategoryCatalogMapper;
import com.firefly.masters.core.utils.TestPaginationRequest;
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCategoryCatalogDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.identitydocument.v1.IdentityDocumentCategoryCatalog;
import com.firefly.masters.models.repositories.identitydocument.v1.IdentityDocumentCategoryCatalogRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class IdentityDocumentCategoryCatalogServiceImplTest {

    @Mock
    private IdentityDocumentCategoryCatalogRepository repository;

    @Mock
    private IdentityDocumentCategoryCatalogMapper mapper;

    @InjectMocks
    private IdentityDocumentCategoryCatalogServiceImpl service;

    private IdentityDocumentCategoryCatalog entity;
    private IdentityDocumentCategoryCatalogDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testCategoryId;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        testCategoryId = UUID.randomUUID();

        entity = new IdentityDocumentCategoryCatalog();
        entity.setCategoryId(testCategoryId);
        entity.setCategoryCode("GOVERNMENT");
        entity.setCategoryName("Government");
        entity.setDescription("Government-issued identification documents");
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setDateCreated(now);
        entity.setDateUpdated(now);

        dto = new IdentityDocumentCategoryCatalogDTO();
        dto.setCategoryId(testCategoryId);
        dto.setCategoryCode("GOVERNMENT");
        dto.setCategoryName("Government");
        dto.setDescription("Government-issued identification documents");
        dto.setStatus(StatusEnum.ACTIVE);
        dto.setDateCreated(now);
        dto.setDateUpdated(now);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listIdentityDocumentCategories_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(IdentityDocumentCategoryCatalog.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<IdentityDocumentCategoryCatalogDTO>> result = service.listIdentityDocumentCategories(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getCategoryId().equals(testCategoryId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(IdentityDocumentCategoryCatalog.class));
    }

    @Test
    void createIdentityDocumentCategory_ShouldReturnCreatedCategory() {
        // Arrange
        when(mapper.toEntity(any(IdentityDocumentCategoryCatalogDTO.class))).thenReturn(entity);
        when(repository.save(any(IdentityDocumentCategoryCatalog.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentCategoryCatalog.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentCategoryCatalogDTO> result = service.createIdentityDocumentCategory(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(IdentityDocumentCategoryCatalogDTO.class));
        verify(repository).save(any(IdentityDocumentCategoryCatalog.class));
        verify(mapper).toDTO(any(IdentityDocumentCategoryCatalog.class));
    }

    @Test
    void getIdentityDocumentCategory_ShouldReturnCategory_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentCategoryCatalog.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentCategoryCatalogDTO> result = service.getIdentityDocumentCategory(testCategoryId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(IdentityDocumentCategoryCatalog.class));
    }

    @Test
    void getIdentityDocumentCategory_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<IdentityDocumentCategoryCatalogDTO> result = service.getIdentityDocumentCategory(testCategoryId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document category not found with ID: " + testCategoryId))
                .verify();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toDTO(any(IdentityDocumentCategoryCatalog.class));
    }

    @Test
    void getIdentityDocumentCategoryByCode_ShouldReturnCategory_WhenFound() {
        // Arrange
        when(repository.findByCategoryCode(anyString())).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentCategoryCatalog.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentCategoryCatalogDTO> result = service.getIdentityDocumentCategoryByCode("GOVERNMENT");

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findByCategoryCode(anyString());
        verify(mapper).toDTO(any(IdentityDocumentCategoryCatalog.class));
    }

    @Test
    void getIdentityDocumentCategoryByCode_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findByCategoryCode(anyString())).thenReturn(Mono.empty());

        // Act
        Mono<IdentityDocumentCategoryCatalogDTO> result = service.getIdentityDocumentCategoryByCode("UNKNOWN");

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document category not found with code: UNKNOWN"))
                .verify();

        verify(repository).findByCategoryCode(anyString());
        verify(mapper, never()).toDTO(any(IdentityDocumentCategoryCatalog.class));
    }

    @Test
    void updateIdentityDocumentCategory_ShouldReturnUpdatedCategory_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(IdentityDocumentCategoryCatalogDTO.class))).thenReturn(entity);
        when(repository.save(any(IdentityDocumentCategoryCatalog.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentCategoryCatalog.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentCategoryCatalogDTO> result = service.updateIdentityDocumentCategory(testCategoryId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(IdentityDocumentCategoryCatalogDTO.class));
        verify(repository).save(any(IdentityDocumentCategoryCatalog.class));
        verify(mapper).toDTO(any(IdentityDocumentCategoryCatalog.class));
    }

    @Test
    void updateIdentityDocumentCategory_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<IdentityDocumentCategoryCatalogDTO> result = service.updateIdentityDocumentCategory(testCategoryId, dto);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document category not found with ID: " + testCategoryId))
                .verify();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(IdentityDocumentCategoryCatalogDTO.class));
        verify(repository, never()).save(any(IdentityDocumentCategoryCatalog.class));
        verify(mapper, never()).toDTO(any(IdentityDocumentCategoryCatalog.class));
    }

    @Test
    void deleteIdentityDocumentCategory_ShouldDeleteCategory_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteIdentityDocumentCategory(testCategoryId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository).deleteById(any(UUID.class));
    }

    @Test
    void deleteIdentityDocumentCategory_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteIdentityDocumentCategory(testCategoryId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document category not found with ID: " + testCategoryId))
                .verify();

        verify(repository).findById(any(UUID.class));
        verify(repository, never()).deleteById(any(UUID.class));
    }
}
