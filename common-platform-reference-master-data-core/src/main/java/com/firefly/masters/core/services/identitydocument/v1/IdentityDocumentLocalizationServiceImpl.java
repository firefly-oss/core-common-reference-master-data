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


package com.firefly.masters.core.services.identitydocument.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.identitydocument.v1.IdentityDocumentLocalizationMapper;
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentLocalizationDTO;
import com.firefly.masters.models.entities.identitydocument.v1.IdentityDocumentLocalization;
import com.firefly.masters.models.repositories.identitydocument.v1.IdentityDocumentLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the IdentityDocumentLocalizationService interface.
 */
@Service
@Transactional
public class IdentityDocumentLocalizationServiceImpl implements IdentityDocumentLocalizationService {

    @Autowired
    private IdentityDocumentLocalizationRepository repository;

    @Autowired
    private IdentityDocumentLocalizationMapper mapper;

    @Override
    public Mono<PaginationResponse<IdentityDocumentLocalizationDTO>> listIdentityDocumentLocalizations(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<PaginationResponse<IdentityDocumentLocalizationDTO>> getLocalizationsByDocumentId(UUID documentId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findByDocumentId(documentId, pageable),
                () -> repository.countByDocumentId(documentId)
        );
    }

    @Override
    public Mono<IdentityDocumentLocalizationDTO> createIdentityDocumentLocalization(IdentityDocumentLocalizationDTO localizationDTO) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        localizationDTO.setDateCreated(now);
        localizationDTO.setDateUpdated(now);

        return Mono.just(localizationDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating identity document localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<IdentityDocumentLocalizationDTO> getIdentityDocumentLocalizationByDocumentAndLocale(UUID documentId, UUID localeId) {
        return repository.findByDocumentIdAndLocaleId(documentId, localeId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document localization not found with document ID: " + documentId + " and locale ID: " + localeId)));
    }

    @Override
    public Mono<IdentityDocumentLocalizationDTO> updateIdentityDocumentLocalization(UUID localizationId, IdentityDocumentLocalizationDTO localizationDTO) {
        return repository.findById(localizationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document localization not found with ID: " + localizationId)))
                .flatMap(existingEntity -> {
                    IdentityDocumentLocalization updatedEntity = mapper.toEntity(localizationDTO);
                    updatedEntity.setLocalizationId(localizationId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    updatedEntity.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error updating identity document localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteIdentityDocumentLocalization(UUID localizationId) {
        return repository.findById(localizationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document localization not found with ID: " + localizationId)))
                .flatMap(entity -> repository.deleteById(localizationId))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting identity document localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteLocalizationsByDocumentId(UUID documentId) {
        return repository.deleteByDocumentId(documentId)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting localizations for document ID: " + documentId, e)));
    }
}
