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


package com.firefly.masters.core.services.currency.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import com.firefly.masters.core.utils.TestPaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.currency.v1.CurrencyMapper;
import com.firefly.masters.interfaces.dtos.currency.v1.CurrencyDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.currency.v1.Currency;
import com.firefly.masters.models.repositories.currency.v1.CurrencyRepository;
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
public class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CurrencyMapper currencyMapper;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    private Currency currency;
    private CurrencyDTO currencyDTO;
    private TestPaginationRequest paginationRequest;
    private UUID testCurrencyId;

    @BeforeEach
    void setUp() {
        // Setup test data
        testCurrencyId = UUID.randomUUID();

        currency = new Currency();
        currency.setCurrencyId(testCurrencyId);
        currency.setIsoCode("USD");
        currency.setCurrencyName("US Dollar");
        currency.setSymbol("$");
        currency.setDecimalPrecision(2);
        currency.setIsMajor(true);
        currency.setStatus(StatusEnum.ACTIVE);
        currency.setDateCreated(LocalDateTime.now());
        currency.setDateUpdated(LocalDateTime.now());

        currencyDTO = new CurrencyDTO();
        currencyDTO.setCurrencyId(testCurrencyId);
        currencyDTO.setIsoCode("USD");
        currencyDTO.setCurrencyName("US Dollar");
        currencyDTO.setSymbol("$");
        currencyDTO.setDecimalPrecision(2);
        currencyDTO.setIsMajor(true);
        currencyDTO.setStatus(StatusEnum.ACTIVE);
        currencyDTO.setDateCreated(LocalDateTime.now());
        currencyDTO.setDateUpdated(LocalDateTime.now());

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void createCurrency_ShouldReturnCreatedCurrency() {
        // Arrange
        when(currencyMapper.toEntity(any(CurrencyDTO.class))).thenReturn(currency);
        when(currencyRepository.save(any(Currency.class))).thenReturn(Mono.just(currency));
        when(currencyMapper.toDTO(any(Currency.class))).thenReturn(currencyDTO);

        // Act
        Mono<CurrencyDTO> result = currencyService.createCurrency(currencyDTO);

        // Assert
        StepVerifier.create(result)
                .expectNext(currencyDTO)
                .verifyComplete();

        verify(currencyMapper).toEntity(any(CurrencyDTO.class));
        verify(currencyRepository).save(any(Currency.class));
        verify(currencyMapper).toDTO(any(Currency.class));
    }

    @Test
    void getCurrency_ShouldReturnCurrencyWhenFound() {
        // Arrange
        when(currencyRepository.findById(any(UUID.class))).thenReturn(Mono.just(currency));
        when(currencyMapper.toDTO(any(Currency.class))).thenReturn(currencyDTO);

        // Act
        Mono<CurrencyDTO> result = currencyService.getCurrency(testCurrencyId);

        // Assert
        StepVerifier.create(result)
                .expectNext(currencyDTO)
                .verifyComplete();

        verify(currencyRepository).findById(any(UUID.class));
        verify(currencyMapper).toDTO(any(Currency.class));
    }

    @Test
    void getCurrency_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(currencyRepository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<CurrencyDTO> result = currencyService.getCurrency(testCurrencyId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(currencyRepository).findById(any(UUID.class));
        verify(currencyMapper, never()).toDTO(any(Currency.class));
    }

    @Test
    void updateCurrency_ShouldReturnUpdatedCurrencyWhenFound() {
        // Arrange
        when(currencyRepository.findById(any(UUID.class))).thenReturn(Mono.just(currency));
        when(currencyMapper.toEntity(any(CurrencyDTO.class))).thenReturn(currency);
        when(currencyRepository.save(any(Currency.class))).thenReturn(Mono.just(currency));
        when(currencyMapper.toDTO(any(Currency.class))).thenReturn(currencyDTO);

        // Act
        Mono<CurrencyDTO> result = currencyService.updateCurrency(testCurrencyId, currencyDTO);

        // Assert
        StepVerifier.create(result)
                .expectNext(currencyDTO)
                .verifyComplete();

        verify(currencyRepository).findById(any(UUID.class));
        verify(currencyMapper).toEntity(any(CurrencyDTO.class));
        verify(currencyRepository).save(any(Currency.class));
        verify(currencyMapper).toDTO(any(Currency.class));
    }

    @Test
    void updateCurrency_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(currencyRepository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<CurrencyDTO> result = currencyService.updateCurrency(testCurrencyId, currencyDTO);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(currencyRepository).findById(any(UUID.class));
        verify(currencyMapper, never()).toEntity(any(CurrencyDTO.class));
        verify(currencyRepository, never()).save(any(Currency.class));
        verify(currencyMapper, never()).toDTO(any(Currency.class));
    }

    @Test
    void deleteCurrency_ShouldDeleteWhenFound() {
        // Arrange
        when(currencyRepository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = currencyService.deleteCurrency(testCurrencyId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(currencyRepository).deleteById(any(UUID.class));
    }

    @Test
    void deleteCurrency_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(currencyRepository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = currencyService.deleteCurrency(testCurrencyId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(currencyRepository).deleteById(any(UUID.class));
    }
}
