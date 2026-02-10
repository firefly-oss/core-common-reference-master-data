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
import com.firefly.masters.core.mappers.identitydocument.v1.IdentityDocumentCatalogMapper;
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCatalogDTO;
import com.firefly.masters.models.entities.identitydocument.v1.IdentityDocumentCatalog;
import com.firefly.masters.models.repositories.identitydocument.v1.IdentityDocumentCatalogRepository;
import com.firefly.masters.models.repositories.identitydocument.v1.IdentityDocumentLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the IdentityDocumentCatalogService interface.
 */
@Service
@Transactional
public class IdentityDocumentCatalogServiceImpl implements IdentityDocumentCatalogService {

    @Autowired
    private IdentityDocumentCatalogRepository repository;

    @Autowired
    private IdentityDocumentLocalizationRepository localizationRepository;

    @Autowired
    private IdentityDocumentCatalogMapper mapper;

    @Override
    public Mono<PaginationResponse<IdentityDocumentCatalogDTO>> listIdentityDocuments(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<PaginationResponse<IdentityDocumentCatalogDTO>> listIdentityDocumentsByCategory(UUID categoryId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findByCategoryId(categoryId, pageable),
                () -> repository.countByCategoryId(categoryId)
        );
    }

    @Override
    public Mono<PaginationResponse<IdentityDocumentCatalogDTO>> listIdentityDocumentsByCountry(UUID countryId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findByCountryId(countryId, pageable),
                () -> repository.countByCountryId(countryId)
        );
    }

    @Override
    public Mono<IdentityDocumentCatalogDTO> createIdentityDocument(IdentityDocumentCatalogDTO identityDocumentDTO) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        identityDocumentDTO.setDateCreated(now);
        identityDocumentDTO.setDateUpdated(now);

        return Mono.just(identityDocumentDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating identity document: " + e.getMessage(), e)));
    }

    @Override
    public Mono<IdentityDocumentCatalogDTO> getIdentityDocument(UUID documentId) {
        return repository.findById(documentId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document not found with ID: " + documentId)));
    }

    @Override
    public Mono<IdentityDocumentCatalogDTO> getIdentityDocumentByCode(String documentCode) {
        return repository.findByDocumentCode(documentCode)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document not found with code: " + documentCode)));
    }

    @Override
    public Mono<IdentityDocumentCatalogDTO> updateIdentityDocument(UUID documentId, IdentityDocumentCatalogDTO identityDocumentDTO) {
        return repository.findById(documentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document not found with ID: " + documentId)))
                .flatMap(existingEntity -> {
                    IdentityDocumentCatalog updatedEntity = mapper.toEntity(identityDocumentDTO);
                    updatedEntity.setDocumentId(documentId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    updatedEntity.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error updating identity document: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteIdentityDocument(UUID documentId) {
        return repository.findById(documentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document not found with ID: " + documentId)))
                .flatMap(entity -> localizationRepository.deleteByDocumentId(documentId)
                        .then(repository.deleteById(documentId)))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting identity document: " + e.getMessage(), e)));
    }
}
