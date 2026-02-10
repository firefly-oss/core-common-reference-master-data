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


package com.firefly.masters.core.services.locale.v1;

import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.locale.v1.LanguageLocaleMapper;
import com.firefly.masters.core.utils.TestPaginationRequest;
import com.firefly.masters.interfaces.dtos.locale.v1.LanguageLocaleDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.locale.v1.LanguageLocale;
import com.firefly.masters.models.repositories.locale.v1.LanguageLocaleRepository;
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
import static org.mockito.Mockito.*;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LanguageLocaleServiceImplTest {

    @Mock
    private LanguageLocaleRepository repository;

    @Mock
    private LanguageLocaleMapper mapper;

    @InjectMocks
    private LanguageLocaleServiceImpl service;

    private LanguageLocale entity;
    private LanguageLocaleDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testLocaleId;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        testLocaleId = UUID.randomUUID();

        entity = new LanguageLocale();
        entity.setLocaleId(testLocaleId);
        entity.setLanguageCode("en");
        entity.setCountryCode("US");
        entity.setLocaleCode("en-US");
        entity.setLanguageName("English");
        entity.setNativeName("English");
        entity.setRegionName("United States");
        entity.setRtl(false);
        entity.setSortOrder(1);
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setDateCreated(now);
        entity.setDateUpdated(now);

        dto = new LanguageLocaleDTO();
        dto.setLocaleId(testLocaleId);
        dto.setLanguageCode("en");
        dto.setCountryCode("US");
        dto.setLocaleCode("en-US");
        dto.setLanguageName("English");
        dto.setNativeName("English");
        dto.setRegionName("United States");
        dto.setRtl(false);
        dto.setSortOrder(1);
        dto.setStatus(StatusEnum.ACTIVE);
        dto.setDateCreated(now);
        dto.setDateUpdated(now);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listLanguageLocales_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(LanguageLocale.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<LanguageLocaleDTO>> result = service.listLanguageLocales(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getLocaleId().equals(testLocaleId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(LanguageLocale.class));
    }

    @Test
    void createLanguageLocale_ShouldReturnCreatedLanguageLocale() {
        // Arrange
        when(mapper.toEntity(any(LanguageLocaleDTO.class))).thenReturn(entity);
        when(repository.save(any(LanguageLocale.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LanguageLocale.class))).thenReturn(dto);

        // Act
        Mono<LanguageLocaleDTO> result = service.createLanguageLocale(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(LanguageLocaleDTO.class));
        verify(repository).save(any(LanguageLocale.class));
        verify(mapper).toDTO(any(LanguageLocale.class));
    }

    @Test
    void getLanguageLocale_ShouldReturnLanguageLocaleWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LanguageLocale.class))).thenReturn(dto);

        // Act
        Mono<LanguageLocaleDTO> result = service.getLanguageLocale(testLocaleId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(LanguageLocale.class));
    }

    @Test
    void getLanguageLocale_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<LanguageLocaleDTO> result = service.getLanguageLocale(testLocaleId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toDTO(any(LanguageLocale.class));
    }

    @Test
    void updateLanguageLocale_ShouldReturnUpdatedLanguageLocaleWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(LanguageLocaleDTO.class))).thenReturn(entity);
        when(repository.save(any(LanguageLocale.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LanguageLocale.class))).thenReturn(dto);

        // Act
        Mono<LanguageLocaleDTO> result = service.updateLanguageLocale(testLocaleId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(LanguageLocaleDTO.class));
        verify(repository).save(any(LanguageLocale.class));
        verify(mapper).toDTO(any(LanguageLocale.class));
    }

    @Test
    void updateLanguageLocale_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<LanguageLocaleDTO> result = service.updateLanguageLocale(testLocaleId, dto);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(LanguageLocaleDTO.class));
        verify(repository, never()).save(any(LanguageLocale.class));
        verify(mapper, never()).toDTO(any(LanguageLocale.class));
    }

    @Test
    void deleteLanguageLocale_ShouldDeleteWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(repository.delete(any(LanguageLocale.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteLanguageLocale(testLocaleId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository).delete(any(LanguageLocale.class));
    }

    @Test
    void deleteLanguageLocale_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteLanguageLocale(testLocaleId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository, never()).delete(any(LanguageLocale.class));
    }
}
