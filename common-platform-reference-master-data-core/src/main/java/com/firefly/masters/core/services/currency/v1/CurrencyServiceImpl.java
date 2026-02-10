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

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.currency.v1.CurrencyMapper;
import com.firefly.masters.interfaces.dtos.country.v1.CountryDTO;
import com.firefly.masters.interfaces.dtos.currency.v1.CurrencyDTO;
import com.firefly.masters.models.entities.country.v1.Country;
import com.firefly.masters.models.entities.currency.v1.Currency;
import com.firefly.masters.models.repositories.currency.v1.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    @Autowired
    private CurrencyMapper mapper;

    @Override
    public Mono<PaginationResponse<CurrencyDTO>> listCurrencies(FilterRequest<CurrencyDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Currency.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<CurrencyDTO> createCurrency(CurrencyDTO currencyDto) {
        Currency currency = mapper.toEntity(currencyDto);
        return repository.save(currency)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CurrencyDTO> getCurrency(UUID currencyId) {
        return repository.findById(currencyId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CurrencyDTO> updateCurrency(UUID currencyId, CurrencyDTO currencyDto) {
        return repository.findById(currencyId)
                .flatMap(existingCurrency -> {
                    Currency updatedCurrency = mapper.toEntity(currencyDto);
                    updatedCurrency.setCurrencyId(currencyId); // Ensure ID matches the existing record
                    return repository.save(updatedCurrency);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCurrency(UUID currencyId) {
        return repository.deleteById(currencyId);
    }
}
