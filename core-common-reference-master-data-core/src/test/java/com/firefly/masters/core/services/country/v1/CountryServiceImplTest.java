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


package com.firefly.masters.core.services.country.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationRequest;
import com.firefly.masters.core.utils.TestPaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.country.v1.CountryMapper;
import com.firefly.masters.interfaces.dtos.country.v1.CountryDTO;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.models.entities.country.v1.Country;
import com.firefly.masters.models.repositories.country.v1.CountryRepository;
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
public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CountryServiceImpl countryService;

    private Country country;
    private CountryDTO countryDTO;
    private TestPaginationRequest paginationRequest;
    private UUID testCountryId;

    @Mock
    private FilterRequest<CountryDTO> filterRequest;

    @BeforeEach
    void setUp() {
        // Setup test data
        testCountryId = UUID.randomUUID();
        country = new Country();
        country.setCountryId(testCountryId);
        country.setIsoCode("US");
        country.setCountryName("United States");
        country.setStatus(StatusEnum.ACTIVE);
        country.setDateCreated(LocalDateTime.now());
        country.setDateUpdated(LocalDateTime.now());

        countryDTO = new CountryDTO();
        countryDTO.setIsoCode("US");
        countryDTO.setCountryName("United States");
        countryDTO.setStatus(StatusEnum.ACTIVE);
        countryDTO.setDateCreated(LocalDateTime.now());
        countryDTO.setDateUpdated(LocalDateTime.now());

        paginationRequest = new TestPaginationRequest(0, 10);
    }

    @Test
    void createCountry_ShouldReturnCreatedCountry() {
        // Arrange
        when(countryMapper.toEntity(any(CountryDTO.class))).thenReturn(country);
        when(countryRepository.save(any(Country.class))).thenReturn(Mono.just(country));
        when(countryMapper.toDTO(any(Country.class))).thenReturn(countryDTO);

        // Act
        Mono<CountryDTO> result = countryService.createCountry(countryDTO);

        // Assert
        StepVerifier.create(result)
                .expectNext(countryDTO)
                .verifyComplete();

        verify(countryMapper).toEntity(any(CountryDTO.class));
        verify(countryRepository).save(any(Country.class));
        verify(countryMapper).toDTO(any(Country.class));
    }

    @Test
    void getCountry_ShouldReturnCountryWhenFound() {
        // Arrange
        when(countryRepository.findById(any(UUID.class))).thenReturn(Mono.just(country));
        when(countryMapper.toDTO(any(Country.class))).thenReturn(countryDTO);

        // Act
        Mono<CountryDTO> result = countryService.getCountry(testCountryId);

        // Assert
        StepVerifier.create(result)
                .expectNext(countryDTO)
                .verifyComplete();

        verify(countryRepository).findById(any(UUID.class));
        verify(countryMapper).toDTO(any(Country.class));
    }

    @Test
    void getCountry_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(countryRepository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<CountryDTO> result = countryService.getCountry(testCountryId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(countryRepository).findById(any(UUID.class));
        verify(countryMapper, never()).toDTO(any(Country.class));
    }

    @Test
    void updateCountry_ShouldReturnUpdatedCountryWhenFound() {
        // Arrange
        when(countryRepository.findById(any(UUID.class))).thenReturn(Mono.just(country));
        when(countryMapper.toEntity(any(CountryDTO.class))).thenReturn(country);
        when(countryRepository.save(any(Country.class))).thenReturn(Mono.just(country));
        when(countryMapper.toDTO(any(Country.class))).thenReturn(countryDTO);

        // Act
        Mono<CountryDTO> result = countryService.updateCountry(testCountryId, countryDTO);

        // Assert
        StepVerifier.create(result)
                .expectNext(countryDTO)
                .verifyComplete();

        verify(countryRepository).findById(any(UUID.class));
        verify(countryMapper).toEntity(any(CountryDTO.class));
        verify(countryRepository).save(any(Country.class));
        verify(countryMapper).toDTO(any(Country.class));
    }

    @Test
    void updateCountry_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(countryRepository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<CountryDTO> result = countryService.updateCountry(testCountryId, countryDTO);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(countryRepository).findById(any(UUID.class));
        verify(countryMapper, never()).toEntity(any(CountryDTO.class));
        verify(countryRepository, never()).save(any(Country.class));
        verify(countryMapper, never()).toDTO(any(Country.class));
    }

    @Test
    void deleteCountry_ShouldDeleteWhenFound() {
        // Arrange
        when(countryRepository.findById(any(UUID.class))).thenReturn(Mono.just(country));
        when(countryRepository.delete(any(Country.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = countryService.deleteCountry(testCountryId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(countryRepository).findById(any(UUID.class));
        verify(countryRepository).delete(any(Country.class));
    }

    @Test
    void deleteCountry_ShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(countryRepository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = countryService.deleteCountry(testCountryId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(countryRepository).findById(any(UUID.class));
        verify(countryRepository, never()).delete(any(Country.class));
    }
}
