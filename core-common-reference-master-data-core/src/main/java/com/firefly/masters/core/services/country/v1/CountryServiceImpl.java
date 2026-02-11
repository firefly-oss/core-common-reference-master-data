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
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.country.v1.CountryMapper;
import com.firefly.masters.interfaces.dtos.country.v1.CountryDTO;
import com.firefly.masters.models.entities.country.v1.Country;
import com.firefly.masters.models.repositories.country.v1.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository repository;

    @Autowired
    private CountryMapper mapper;

    @Override
    public Mono<PaginationResponse<CountryDTO>> listCountries(FilterRequest<CountryDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Country.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<CountryDTO> createCountry(CountryDTO countryDto) {
        Country country = mapper.toEntity(countryDto);
        return repository.save(country)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CountryDTO> getCountry(UUID countryId) {
        return repository.findById(countryId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CountryDTO> updateCountry(UUID countryId, CountryDTO countryDto) {
        return repository.findById(countryId)
                .flatMap(foundCountry -> {
                    Country updatedCountry = mapper.toEntity(countryDto);
                    updatedCountry.setCountryId(foundCountry.getCountryId());
                    return repository.save(updatedCountry);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCountry(UUID countryId) {
        return repository.findById(countryId)
                .flatMap(repository::delete);
    }
}
