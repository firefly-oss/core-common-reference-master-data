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


package com.firefly.masters.core.services.activity.v1;

import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.activity.v1.ActivityCodeMapper;
import com.firefly.masters.core.utils.TestPaginationRequest;
import com.firefly.masters.interfaces.dtos.activity.v1.ActivityCodeDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.activity.v1.ActivityCode;
import com.firefly.masters.models.repositories.activity.v1.ActivityCodeRepository;
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
public class ActivityCodeServiceImplTest {

    @Mock
    private ActivityCodeRepository repository;

    @Mock
    private ActivityCodeMapper mapper;

    @InjectMocks
    private ActivityCodeServiceImpl service;

    private ActivityCode entity;
    private ActivityCodeDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testActivityCodeId;
    private UUID testCountryId;

    @BeforeEach
    void setUp() {
        // Setup test data
        testActivityCodeId = UUID.randomUUID();
        testCountryId = UUID.randomUUID();

        entity = new ActivityCode();
        entity.setActivityCodeId(testActivityCodeId);
        entity.setCountryId(testCountryId);
        entity.setCode("A01");
        entity.setClassificationSys("NAICS");
        entity.setDescription("Crop Production");
        entity.setParentCodeId(null);
        entity.setHighRisk(false);
        entity.setRiskFactors(null);
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setDateCreated(LocalDateTime.now());
        entity.setDateUpdated(LocalDateTime.now());

        dto = new ActivityCodeDTO();
        dto.setActivityCodeId(testActivityCodeId);
        dto.setCountryId(testCountryId);
        dto.setCode("A01");
        dto.setClassificationSys("NAICS");
        dto.setDescription("Crop Production");
        dto.setParentCodeId(null);
        dto.setHighRisk(false);
        dto.setRiskFactors(null);
        dto.setStatus(StatusEnum.ACTIVE);

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listActivityCodes_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(ActivityCode.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<ActivityCodeDTO>> result = service.listActivityCodes(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getActivityCodeId().equals(testActivityCodeId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(ActivityCode.class));
    }

    @Test
    void getActivityCodesByCountry_ShouldReturnActivityCodes() {
        // Arrange
        when(repository.findByCountryId(any(UUID.class))).thenReturn(Flux.just(entity));
        when(mapper.toDTO(any(ActivityCode.class))).thenReturn(dto);

        // Act
        Flux<ActivityCodeDTO> result = service.getActivityCodesByCountry(testCountryId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findByCountryId(any(UUID.class));
        verify(mapper).toDTO(any(ActivityCode.class));
    }

    @Test
    void getChildActivityCodes_ShouldReturnChildActivityCodes() {
        // Arrange
        when(repository.findByParentCodeId(any(UUID.class))).thenReturn(Flux.just(entity));
        when(mapper.toDTO(any(ActivityCode.class))).thenReturn(dto);

        // Act
        Flux<ActivityCodeDTO> result = service.getChildActivityCodes(testActivityCodeId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findByParentCodeId(any(UUID.class));
        verify(mapper).toDTO(any(ActivityCode.class));
    }

    @Test
    void createActivityCode_ShouldReturnCreatedActivityCode() {
        // Arrange
        when(mapper.toEntity(any(ActivityCodeDTO.class))).thenReturn(entity);
        when(repository.save(any(ActivityCode.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(ActivityCode.class))).thenReturn(dto);

        // Act
        Mono<ActivityCodeDTO> result = service.createActivityCode(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(ActivityCodeDTO.class));
        verify(repository).save(any(ActivityCode.class));
        verify(mapper).toDTO(any(ActivityCode.class));
    }

    @Test
    void getActivityCode_ShouldReturnActivityCodeWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(ActivityCode.class))).thenReturn(dto);

        // Act
        Mono<ActivityCodeDTO> result = service.getActivityCode(testActivityCodeId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(ActivityCode.class));
    }

    @Test
    void getActivityCode_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<ActivityCodeDTO> result = service.getActivityCode(testActivityCodeId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toDTO(any(ActivityCode.class));
    }

    @Test
    void updateActivityCode_ShouldReturnUpdatedActivityCodeWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(ActivityCodeDTO.class))).thenReturn(entity);
        when(repository.save(any(ActivityCode.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(ActivityCode.class))).thenReturn(dto);

        // Act
        Mono<ActivityCodeDTO> result = service.updateActivityCode(testActivityCodeId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(ActivityCodeDTO.class));
        verify(repository).save(any(ActivityCode.class));
        verify(mapper).toDTO(any(ActivityCode.class));
    }

    @Test
    void updateActivityCode_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<ActivityCodeDTO> result = service.updateActivityCode(testActivityCodeId, dto);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(ActivityCodeDTO.class));
        verify(repository, never()).save(any(ActivityCode.class));
        verify(mapper, never()).toDTO(any(ActivityCode.class));
    }

    @Test
    void deleteActivityCode_ShouldDeleteWhenFound() {
        // Arrange
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteActivityCode(testActivityCodeId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).deleteById(any(UUID.class));
    }
}
