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

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.locale.v1.LanguageLocaleMapper;
import com.firefly.masters.interfaces.dtos.locale.v1.LanguageLocaleDTO;
import com.firefly.masters.models.entities.locale.v1.LanguageLocale;
import com.firefly.masters.models.repositories.locale.v1.LanguageLocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class LanguageLocaleServiceImpl implements LanguageLocaleService {

    @Autowired
    private LanguageLocaleRepository repository;

    @Autowired
    private LanguageLocaleMapper mapper;

    @Override
    public Mono<PaginationResponse<LanguageLocaleDTO>> listLanguageLocales(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<LanguageLocaleDTO> createLanguageLocale(LanguageLocaleDTO dto) {
        LanguageLocale entity = mapper.toEntity(dto);
        entity.setLocaleId(null); // Ensure the ID is null for a new entity
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LanguageLocaleDTO> getLanguageLocale(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LanguageLocaleDTO> updateLanguageLocale(UUID id, LanguageLocaleDTO dto) {
        return repository.findById(id)
                .flatMap(existingEntity -> {
                    LanguageLocale updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setLocaleId(id); // Ensure the ID is preserved
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteLanguageLocale(UUID id) {
        return repository.findById(id)
                .flatMap(repository::delete);
    }
}
