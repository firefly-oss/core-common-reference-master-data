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
import com.firefly.masters.core.mappers.identitydocument.v1.IdentityDocumentCatalogMapper;
import com.firefly.masters.core.utils.TestPaginationRequest;
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCatalogDTO;
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCategoryCatalogDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.identitydocument.v1.IdentityDocumentCatalog;
import com.firefly.masters.models.repositories.identitydocument.v1.IdentityDocumentCatalogRepository;
import com.firefly.masters.models.repositories.identitydocument.v1.IdentityDocumentLocalizationRepository;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IdentityDocumentCatalogServiceImplTest {

    @Mock
    private IdentityDocumentCatalogRepository repository;

    @Mock
    private IdentityDocumentLocalizationRepository localizationRepository;

    @Mock
    private IdentityDocumentCatalogMapper mapper;

    @InjectMocks
    private IdentityDocumentCatalogServiceImpl service;

    private IdentityDocumentCatalog entity;
    private IdentityDocumentCatalogDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testDocumentId;
    private UUID testCategoryId;
    private UUID testCountryId;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        testDocumentId = UUID.randomUUID();
        testCategoryId = UUID.randomUUID();
        testCountryId = UUID.randomUUID();

        entity = new IdentityDocumentCatalog();
        entity.setDocumentId(testDocumentId);
        entity.setDocumentCode("PASSPORT");
        entity.setDocumentName("Passport");
        entity.setCategoryId(testCategoryId);
        entity.setCountryId(testCountryId);
        entity.setDescription("International passport for travel and identification");
        entity.setValidationRegex("^[A-Z0-9]{9}$");
        entity.setFormatDescription("9 characters, alphanumeric");
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setDateCreated(now);
        entity.setDateUpdated(now);

        IdentityDocumentCategoryCatalogDTO categoryDTO = new IdentityDocumentCategoryCatalogDTO();
        categoryDTO.setCategoryId(testCategoryId);
        categoryDTO.setCategoryCode("GOVERNMENT");
        categoryDTO.setCategoryName("Government");
        categoryDTO.setDescription("Government-issued identification documents");
        categoryDTO.setStatus(StatusEnum.ACTIVE);
        categoryDTO.setDateCreated(now);
        categoryDTO.setDateUpdated(now);

        dto = new IdentityDocumentCatalogDTO();
        dto.setDocumentId(testDocumentId);
        dto.setDocumentCode("PASSPORT");
        dto.setDocumentName("Passport");
        dto.setCategoryId(testCategoryId);
        dto.setCategory(categoryDTO);
        dto.setCountryId(testCountryId);
        dto.setDescription("International passport for travel and identification");
        dto.setValidationRegex("^[A-Z0-9]{9}$");
        dto.setFormatDescription("9 characters, alphanumeric");
        dto.setStatus(StatusEnum.ACTIVE);
        dto.setDateCreated(now);
        dto.setDateUpdated(now);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listIdentityDocuments_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(IdentityDocumentCatalog.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<IdentityDocumentCatalogDTO>> result = service.listIdentityDocuments(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getDocumentId().equals(testDocumentId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void listIdentityDocumentsByCategory_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findByCategoryId(any(UUID.class), any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.countByCategoryId(any(UUID.class))).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(IdentityDocumentCatalog.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<IdentityDocumentCatalogDTO>> result = service.listIdentityDocumentsByCategory(testCategoryId, paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getDocumentId().equals(testDocumentId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findByCategoryId(any(UUID.class), any(Pageable.class));
        verify(repository).countByCategoryId(any(UUID.class));
        verify(mapper).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void listIdentityDocumentsByCountry_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findByCountryId(any(UUID.class), any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.countByCountryId(any(UUID.class))).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(IdentityDocumentCatalog.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<IdentityDocumentCatalogDTO>> result = service.listIdentityDocumentsByCountry(testCountryId, paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getDocumentId().equals(testDocumentId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findByCountryId(any(UUID.class), any(Pageable.class));
        verify(repository).countByCountryId(any(UUID.class));
        verify(mapper).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void createIdentityDocument_ShouldReturnCreatedDocument() {
        // Arrange
        when(mapper.toEntity(any(IdentityDocumentCatalogDTO.class))).thenReturn(entity);
        when(repository.save(any(IdentityDocumentCatalog.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentCatalog.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentCatalogDTO> result = service.createIdentityDocument(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(IdentityDocumentCatalogDTO.class));
        verify(repository).save(any(IdentityDocumentCatalog.class));
        verify(mapper).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void getIdentityDocument_ShouldReturnDocument_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentCatalog.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentCatalogDTO> result = service.getIdentityDocument(testDocumentId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void getIdentityDocument_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<IdentityDocumentCatalogDTO> result = service.getIdentityDocument(testDocumentId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document not found with ID: " + testDocumentId))
                .verify();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void getIdentityDocumentByCode_ShouldReturnDocument_WhenFound() {
        // Arrange
        when(repository.findByDocumentCode(anyString())).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentCatalog.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentCatalogDTO> result = service.getIdentityDocumentByCode("PASSPORT");

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findByDocumentCode(anyString());
        verify(mapper).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void getIdentityDocumentByCode_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findByDocumentCode(anyString())).thenReturn(Mono.empty());

        // Act
        Mono<IdentityDocumentCatalogDTO> result = service.getIdentityDocumentByCode("UNKNOWN");

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document not found with code: UNKNOWN"))
                .verify();

        verify(repository).findByDocumentCode(anyString());
        verify(mapper, never()).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void updateIdentityDocument_ShouldReturnUpdatedDocument_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(IdentityDocumentCatalogDTO.class))).thenReturn(entity);
        when(repository.save(any(IdentityDocumentCatalog.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentCatalog.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentCatalogDTO> result = service.updateIdentityDocument(testDocumentId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(IdentityDocumentCatalogDTO.class));
        verify(repository).save(any(IdentityDocumentCatalog.class));
        verify(mapper).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void updateIdentityDocument_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<IdentityDocumentCatalogDTO> result = service.updateIdentityDocument(testDocumentId, dto);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document not found with ID: " + testDocumentId))
                .verify();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(IdentityDocumentCatalogDTO.class));
        verify(repository, never()).save(any(IdentityDocumentCatalog.class));
        verify(mapper, never()).toDTO(any(IdentityDocumentCatalog.class));
    }

    @Test
    void deleteIdentityDocument_ShouldDeleteDocument_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(localizationRepository.deleteByDocumentId(any(UUID.class))).thenReturn(Mono.empty());
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteIdentityDocument(testDocumentId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(localizationRepository).deleteByDocumentId(any(UUID.class));
        verify(repository).deleteById(any(UUID.class));
    }

    @Test
    void deleteIdentityDocument_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteIdentityDocument(testDocumentId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document not found with ID: " + testDocumentId))
                .verify();

        verify(repository).findById(any(UUID.class));
        verify(localizationRepository, never()).deleteByDocumentId(any(UUID.class));
        verify(repository, never()).deleteById(any(UUID.class));
    }
}
