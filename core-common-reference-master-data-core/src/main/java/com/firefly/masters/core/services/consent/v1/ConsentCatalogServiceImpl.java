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


package com.firefly.masters.core.services.consent.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.consent.v1.ConsentCatalogMapper;
import com.firefly.masters.interfaces.dtos.consent.v1.ConsentCatalogDTO;
import com.firefly.masters.models.entities.consent.v1.ConsentCatalog;
import com.firefly.masters.models.repositories.consent.v1.ConsentCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the ConsentCatalogService interface.
 */
@Service
@Transactional
public class ConsentCatalogServiceImpl implements ConsentCatalogService {

    @Autowired
    private ConsentCatalogRepository repository;

    @Autowired
    private ConsentCatalogMapper mapper;

    @Override
    public Mono<PaginationResponse<ConsentCatalogDTO>> listConsentCatalog(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<PaginationResponse<ConsentCatalogDTO>> listConsentCatalogByType(String consentType, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findByConsentType(consentType, pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ConsentCatalogDTO> createConsentCatalog(ConsentCatalogDTO dto) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        dto.setDateCreated(now);
        dto.setDateUpdated(now);

        ConsentCatalog entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ConsentCatalogDTO> getConsentCatalog(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ConsentCatalogDTO> updateConsentCatalog(UUID id, ConsentCatalogDTO dto) {
        return repository.findById(id)
                .flatMap(existingEntity -> {
                    // Update audit fields
                    dto.setDateUpdated(LocalDateTime.now());
                    dto.setDateCreated(existingEntity.getDateCreated());

                    ConsentCatalog updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setConsentId(existingEntity.getConsentId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteConsentCatalog(UUID id) {
        return repository.deleteById(id);
    }
}