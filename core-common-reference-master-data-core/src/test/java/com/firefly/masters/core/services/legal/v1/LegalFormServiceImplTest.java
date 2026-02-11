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


package com.firefly.masters.core.services.legal.v1;

import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.legal.v1.LegalFormMapper;
import com.firefly.masters.core.utils.TestPaginationRequest;
import com.firefly.masters.interfaces.dtos.legal.v1.LegalFormDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.legal.v1.LegalForm;
import com.firefly.masters.models.repositories.legal.v1.LegalFormRepository;
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
public class LegalFormServiceImplTest {

    @Mock
    private LegalFormRepository repository;

    @Mock
    private LegalFormMapper mapper;

    @InjectMocks
    private LegalFormServiceImpl service;

    private LegalForm entity;
    private LegalFormDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testLegalFormId;
    private UUID testCountryId;

    @BeforeEach
    void setUp() {
        // Setup test data
        testLegalFormId = UUID.randomUUID();
        testCountryId = UUID.randomUUID();

        entity = new LegalForm();
        entity.setLegalFormId(testLegalFormId);
        entity.setCountryId(testCountryId);
        entity.setCode("LLC");
        entity.setName("Limited Liability Company");
        entity.setDescription("A business structure that combines the pass-through taxation of a partnership with the limited liability of a corporation");
        entity.setIsCorporate(true);
        entity.setEntityType("FOR_PROFIT");
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setDateCreated(LocalDateTime.now());
        entity.setDateUpdated(LocalDateTime.now());

        dto = new LegalFormDTO();
        dto.setLegalFormId(testLegalFormId);
        dto.setCountryId(testCountryId);
        dto.setCode("LLC");
        dto.setName("Limited Liability Company");
        dto.setDescription("A business structure that combines the pass-through taxation of a partnership with the limited liability of a corporation");
        dto.setIsCorporate(true);
        dto.setEntityType("FOR_PROFIT");
        dto.setStatus(StatusEnum.ACTIVE);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void getLegalFormsByCountry_ShouldReturnLegalForms() {
        // Arrange
        when(repository.findByCountryId(any(UUID.class))).thenReturn(Flux.just(entity));
        when(mapper.toDTO(any(LegalForm.class))).thenReturn(dto);

        // Act
        Flux<LegalFormDTO> result = service.getLegalFormsByCountry(testCountryId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findByCountryId(any(UUID.class));
        verify(mapper).toDTO(any(LegalForm.class));
    }

    @Test
    void createLegalForm_ShouldReturnCreatedLegalForm() {
        // Arrange
        when(mapper.toEntity(any(LegalFormDTO.class))).thenReturn(entity);
        when(repository.save(any(LegalForm.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LegalForm.class))).thenReturn(dto);

        // Act
        Mono<LegalFormDTO> result = service.createLegalForm(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(LegalFormDTO.class));
        verify(repository).save(any(LegalForm.class));
        verify(mapper).toDTO(any(LegalForm.class));
    }

    @Test
    void getLegalForm_ShouldReturnLegalFormWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LegalForm.class))).thenReturn(dto);

        // Act
        Mono<LegalFormDTO> result = service.getLegalForm(testLegalFormId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(LegalForm.class));
    }

    @Test
    void getLegalForm_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<LegalFormDTO> result = service.getLegalForm(testLegalFormId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toDTO(any(LegalForm.class));
    }

    @Test
    void updateLegalForm_ShouldReturnUpdatedLegalFormWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(LegalFormDTO.class))).thenReturn(entity);
        when(repository.save(any(LegalForm.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(LegalForm.class))).thenReturn(dto);

        // Act
        Mono<LegalFormDTO> result = service.updateLegalForm(testLegalFormId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(LegalFormDTO.class));
        verify(repository).save(any(LegalForm.class));
        verify(mapper).toDTO(any(LegalForm.class));
    }

    @Test
    void updateLegalForm_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<LegalFormDTO> result = service.updateLegalForm(testLegalFormId, dto);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(LegalFormDTO.class));
        verify(repository, never()).save(any(LegalForm.class));
        verify(mapper, never()).toDTO(any(LegalForm.class));
    }

    @Test
    void deleteLegalForm_ShouldDeleteWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(repository.delete(any(LegalForm.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteLegalForm(testLegalFormId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository).delete(any(LegalForm.class));
    }

    @Test
    void deleteLegalForm_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteLegalForm(testLegalFormId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(repository, never()).delete(any(LegalForm.class));
    }
}
