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
import com.firefly.masters.core.mappers.identitydocument.v1.IdentityDocumentLocalizationMapper;
import com.firefly.masters.core.utils.TestPaginationRequest;
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentLocalizationDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.identitydocument.v1.IdentityDocumentLocalization;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IdentityDocumentLocalizationServiceImplTest {

    @Mock
    private IdentityDocumentLocalizationRepository repository;

    @Mock
    private IdentityDocumentLocalizationMapper mapper;

    @InjectMocks
    private IdentityDocumentLocalizationServiceImpl service;

    private IdentityDocumentLocalization entity;
    private IdentityDocumentLocalizationDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testDocumentId;
    private UUID testLocaleId;
    private UUID testLocalizationId;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        testDocumentId = UUID.randomUUID();
        testLocaleId = UUID.randomUUID();
        testLocalizationId = UUID.randomUUID();

        entity = new IdentityDocumentLocalization();
        entity.setLocalizationId(testLocalizationId);
        entity.setDocumentId(testDocumentId);
        entity.setLocaleId(testLocaleId);
        entity.setDocumentName("Passport");
        entity.setDescription("International passport for travel and identification");
        entity.setFormatDescription("9 characters, alphanumeric");
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setDateCreated(now);
        entity.setDateUpdated(now);

        dto = new IdentityDocumentLocalizationDTO();
        dto.setLocalizationId(testLocalizationId);
        dto.setDocumentId(testDocumentId);
        dto.setLocaleId(testLocaleId);
        dto.setDocumentName("Passport");
        dto.setDescription("International passport for travel and identification");
        dto.setFormatDescription("9 characters, alphanumeric");
        dto.setStatus(StatusEnum.ACTIVE);
        dto.setDateCreated(now);
        dto.setDateUpdated(now);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listIdentityDocumentLocalizations_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(IdentityDocumentLocalization.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<IdentityDocumentLocalizationDTO>> result = service.listIdentityDocumentLocalizations(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getLocalizationId().equals(testLocalizationId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(IdentityDocumentLocalization.class));
    }

    @Test
    void getLocalizationsByDocumentId_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findByDocumentId(any(UUID.class), any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.countByDocumentId(any(UUID.class))).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(IdentityDocumentLocalization.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<IdentityDocumentLocalizationDTO>> result = service.getLocalizationsByDocumentId(testDocumentId, paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getLocalizationId().equals(testLocalizationId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findByDocumentId(any(UUID.class), any(Pageable.class));
        verify(repository).countByDocumentId(any(UUID.class));
        verify(mapper).toDTO(any(IdentityDocumentLocalization.class));
    }

    @Test
    void createIdentityDocumentLocalization_ShouldReturnCreatedLocalization() {
        // Arrange
        when(mapper.toEntity(any(IdentityDocumentLocalizationDTO.class))).thenReturn(entity);
        when(repository.save(any(IdentityDocumentLocalization.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentLocalization.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentLocalizationDTO> result = service.createIdentityDocumentLocalization(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(IdentityDocumentLocalizationDTO.class));
        verify(repository).save(any(IdentityDocumentLocalization.class));
        verify(mapper).toDTO(any(IdentityDocumentLocalization.class));
    }

    @Test
    void getIdentityDocumentLocalizationByDocumentAndLocale_ShouldReturnLocalization_WhenFound() {
        // Arrange
        when(repository.findByDocumentIdAndLocaleId(any(UUID.class), any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentLocalization.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentLocalizationDTO> result = service.getIdentityDocumentLocalizationByDocumentAndLocale(testDocumentId, testLocaleId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findByDocumentIdAndLocaleId(any(UUID.class), any(UUID.class));
        verify(mapper).toDTO(any(IdentityDocumentLocalization.class));
    }

    @Test
    void getIdentityDocumentLocalizationByDocumentAndLocale_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findByDocumentIdAndLocaleId(any(UUID.class), any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<IdentityDocumentLocalizationDTO> result = service.getIdentityDocumentLocalizationByDocumentAndLocale(testDocumentId, testLocaleId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document localization not found with document ID: " + testDocumentId + " and locale ID: " + testLocaleId))
                .verify();

        verify(repository).findByDocumentIdAndLocaleId(any(UUID.class), any(UUID.class));
        verify(mapper, never()).toDTO(any(IdentityDocumentLocalization.class));
    }

    @Test
    void updateIdentityDocumentLocalization_ShouldReturnUpdatedLocalization_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(IdentityDocumentLocalizationDTO.class))).thenReturn(entity);
        when(repository.save(any(IdentityDocumentLocalization.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(IdentityDocumentLocalization.class))).thenReturn(dto);

        // Act
        Mono<IdentityDocumentLocalizationDTO> result = service.updateIdentityDocumentLocalization(testLocalizationId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(IdentityDocumentLocalizationDTO.class));
        verify(repository).save(any(IdentityDocumentLocalization.class));
        verify(mapper).toDTO(any(IdentityDocumentLocalization.class));
    }

    @Test
    void updateIdentityDocumentLocalization_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<IdentityDocumentLocalizationDTO> result = service.updateIdentityDocumentLocalization(testLocalizationId, dto);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document localization not found with ID: " + testLocalizationId))
                .verify();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(IdentityDocumentLocalizationDTO.class));
        verify(repository, never()).save(any(IdentityDocumentLocalization.class));
        verify(mapper, never()).toDTO(any(IdentityDocumentLocalization.class));
    }

    @Test
    void deleteIdentityDocumentLocalization_ShouldDeleteLocalization_WhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteIdentityDocumentLocalization(testLocalizationId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository).deleteById(any(UUID.class));
    }

    @Test
    void deleteIdentityDocumentLocalization_ShouldReturnError_WhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteIdentityDocumentLocalization(testLocalizationId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Identity document localization not found with ID: " + testLocalizationId))
                .verify();

        verify(repository).findById(any(UUID.class));
        verify(repository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteLocalizationsByDocumentId_ShouldDeleteLocalizations() {
        // Arrange
        when(repository.deleteByDocumentId(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteLocalizationsByDocumentId(testDocumentId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).deleteByDocumentId(any(UUID.class));
    }
}
