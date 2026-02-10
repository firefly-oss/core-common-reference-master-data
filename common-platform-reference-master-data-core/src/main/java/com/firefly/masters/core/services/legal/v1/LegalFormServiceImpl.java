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

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.legal.v1.LegalFormMapper;
import com.firefly.masters.interfaces.dtos.legal.v1.LegalFormDTO;
import com.firefly.masters.models.entities.currency.v1.Currency;
import com.firefly.masters.models.entities.legal.v1.LegalForm;
import com.firefly.masters.models.repositories.legal.v1.LegalFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class LegalFormServiceImpl implements LegalFormService {

    @Autowired
    private LegalFormRepository repository;

    @Autowired
    private LegalFormMapper mapper;

    @Override
    public Mono<PaginationResponse<LegalFormDTO>> listLegalForms(FilterRequest<LegalFormDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        LegalForm.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Flux<LegalFormDTO> getLegalFormsByCountry(UUID countryId) {
        return repository.findByCountryId(countryId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LegalFormDTO> createLegalForm(LegalFormDTO legalFormDto) {
        LegalForm legalForm = mapper.toEntity(legalFormDto);
        return repository.save(legalForm)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LegalFormDTO> getLegalForm(UUID legalFormId) {
        return repository.findById(legalFormId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LegalFormDTO> updateLegalForm(UUID legalFormId, LegalFormDTO legalFormDto) {
        return repository.findById(legalFormId)
                .flatMap(foundLegalForm -> {
                    LegalForm updatedLegalForm = mapper.toEntity(legalFormDto);
                    updatedLegalForm.setLegalFormId(foundLegalForm.getLegalFormId());
                    return repository.save(updatedLegalForm);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteLegalForm(UUID legalFormId) {
        return repository.findById(legalFormId)
                .flatMap(repository::delete);
    }
}