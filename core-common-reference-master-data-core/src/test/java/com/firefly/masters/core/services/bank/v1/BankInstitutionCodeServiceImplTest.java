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


package com.firefly.masters.core.services.bank.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import com.firefly.masters.core.utils.TestPaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.bank.v1.BankInstitutionCodeMapper;
import com.firefly.masters.interfaces.dtos.bank.v1.BankInstitutionCodeDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.bank.v1.BankInstitutionCode;
import com.firefly.masters.models.repositories.bank.v1.BankInstitutionCodeRepository;
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
public class BankInstitutionCodeServiceImplTest {

    @Mock
    private BankInstitutionCodeRepository repository;

    @Mock
    private BankInstitutionCodeMapper mapper;

    @InjectMocks
    private BankInstitutionCodeServiceImpl service;

    private BankInstitutionCode entity;
    private BankInstitutionCodeDTO dto;
    private TestPaginationRequest paginationRequest;
    private UUID testInstitutionId;
    private UUID testCountryId;
    private UUID testInstitutionTypeLkpId;

    @BeforeEach
    void setUp() {
        // Setup test data
        testInstitutionId = UUID.randomUUID();
        testCountryId = UUID.randomUUID();
        testInstitutionTypeLkpId = UUID.randomUUID();

        entity = new BankInstitutionCode();
        entity.setInstitutionId(testInstitutionId);
        entity.setBankName("Chase Bank");
        entity.setSwiftCode("CHASUS33");
        entity.setRoutingNumber("021000021");
        entity.setIbanPrefix("US");
        entity.setCountryId(testCountryId);
        entity.setInstitutionTypeLkpId(testInstitutionTypeLkpId);
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setSvgIcon("data:image/svg+xml;base64,...");
        entity.setDateCreated(LocalDateTime.now());
        entity.setDateUpdated(LocalDateTime.now());

        dto = new BankInstitutionCodeDTO();
        dto.setInstitutionId(testInstitutionId);
        dto.setBankName("Chase Bank");
        dto.setSwiftCode("CHASUS33");
        dto.setRoutingNumber("021000021");
        dto.setIbanPrefix("US");
        dto.setCountryId(testCountryId);
        dto.setInstitutionTypeLkpId(testInstitutionTypeLkpId);
        dto.setStatus(StatusEnum.ACTIVE);
        dto.setSvgIcon("data:image/svg+xml;base64,...");
        dto.setDateCreated(LocalDateTime.now());
        dto.setDateUpdated(LocalDateTime.now());

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void listBankInstitutionCodes_ShouldReturnPaginatedResponse() {
        // Arrange
        Pageable pageable = paginationRequest.toPageable();
        when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(entity));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDTO(any(BankInstitutionCode.class))).thenReturn(dto);

        // Act
        Mono<PaginationResponse<BankInstitutionCodeDTO>> result = service.listBankInstitutionCodes(paginationRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getContent().size() == 1 &&
                            response.getContent().get(0).getInstitutionId().equals(testInstitutionId) &&
                            response.getTotalElements() == 1L;
                })
                .verifyComplete();

        verify(repository).findAllBy(any(Pageable.class));
        verify(repository).count();
        verify(mapper).toDTO(any(BankInstitutionCode.class));
    }

    @Test
    void createBankInstitutionCode_ShouldReturnCreatedEntity() {
        // Arrange
        when(mapper.toEntity(any(BankInstitutionCodeDTO.class))).thenReturn(entity);
        when(repository.save(any(BankInstitutionCode.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(BankInstitutionCode.class))).thenReturn(dto);

        // Act
        Mono<BankInstitutionCodeDTO> result = service.createBankInstitutionCode(dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(mapper).toEntity(any(BankInstitutionCodeDTO.class));
        verify(repository).save(any(BankInstitutionCode.class));
        verify(mapper).toDTO(any(BankInstitutionCode.class));
    }

    @Test
    void getBankInstitutionCode_ShouldReturnEntityWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(BankInstitutionCode.class))).thenReturn(dto);

        // Act
        Mono<BankInstitutionCodeDTO> result = service.getBankInstitutionCode(testInstitutionId);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toDTO(any(BankInstitutionCode.class));
    }

    @Test
    void getBankInstitutionCode_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<BankInstitutionCodeDTO> result = service.getBankInstitutionCode(testInstitutionId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toDTO(any(BankInstitutionCode.class));
    }

    @Test
    void updateBankInstitutionCode_ShouldReturnUpdatedEntityWhenFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.toEntity(any(BankInstitutionCodeDTO.class))).thenReturn(entity);
        when(repository.save(any(BankInstitutionCode.class))).thenReturn(Mono.just(entity));
        when(mapper.toDTO(any(BankInstitutionCode.class))).thenReturn(dto);

        // Act
        Mono<BankInstitutionCodeDTO> result = service.updateBankInstitutionCode(testInstitutionId, dto);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper).toEntity(any(BankInstitutionCodeDTO.class));
        verify(repository).save(any(BankInstitutionCode.class));
        verify(mapper).toDTO(any(BankInstitutionCode.class));
    }

    @Test
    void updateBankInstitutionCode_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<BankInstitutionCodeDTO> result = service.updateBankInstitutionCode(testInstitutionId, dto);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(any(UUID.class));
        verify(mapper, never()).toEntity(any(BankInstitutionCodeDTO.class));
        verify(repository, never()).save(any(BankInstitutionCode.class));
        verify(mapper, never()).toDTO(any(BankInstitutionCode.class));
    }

    @Test
    void deleteBankInstitutionCode_ShouldDeleteWhenFound() {
        // Arrange
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteBankInstitutionCode(testInstitutionId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).deleteById(any(UUID.class));
    }

    @Test
    void deleteBankInstitutionCode_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteBankInstitutionCode(testInstitutionId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).deleteById(any(UUID.class));
    }
}
