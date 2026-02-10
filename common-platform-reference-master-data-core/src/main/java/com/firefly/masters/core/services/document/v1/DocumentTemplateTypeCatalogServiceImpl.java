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


package com.firefly.masters.core.services.document.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.document.v1.DocumentTemplateTypeCatalogMapper;
import com.firefly.masters.interfaces.dtos.document.v1.DocumentTemplateTypeCatalogDTO;
import com.firefly.masters.models.entities.document.v1.DocumentTemplateTypeCatalog;
import com.firefly.masters.models.repositories.document.v1.DocumentTemplateTypeCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the DocumentTemplateTypeCatalogService interface.
 */
@Service
@Transactional
public class DocumentTemplateTypeCatalogServiceImpl implements DocumentTemplateTypeCatalogService {

    @Autowired
    private DocumentTemplateTypeCatalogRepository repository;

    @Autowired
    private DocumentTemplateTypeCatalogMapper mapper;

    @Override
    public Mono<PaginationResponse<DocumentTemplateTypeCatalogDTO>> listDocumentTemplateTypes(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<DocumentTemplateTypeCatalogDTO> createDocumentTemplateType(DocumentTemplateTypeCatalogDTO documentTemplateTypeDTO) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        documentTemplateTypeDTO.setDateCreated(now);
        documentTemplateTypeDTO.setDateUpdated(now);

        return Mono.just(documentTemplateTypeDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating document template type: " + e.getMessage(), e)));
    }

    @Override
    public Mono<DocumentTemplateTypeCatalogDTO> getDocumentTemplateType(UUID typeId) {
        return repository.findById(typeId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Document template type not found with ID: " + typeId)));
    }

    @Override
    public Mono<DocumentTemplateTypeCatalogDTO> getDocumentTemplateTypeByCode(String typeCode) {
        return repository.findByTypeCode(typeCode)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Document template type not found with code: " + typeCode)));
    }

    @Override
    public Mono<DocumentTemplateTypeCatalogDTO> updateDocumentTemplateType(UUID typeId, DocumentTemplateTypeCatalogDTO documentTemplateTypeDTO) {
        return repository.findById(typeId)
                .switchIfEmpty(Mono.error(new RuntimeException("Document template type not found with ID: " + typeId)))
                .flatMap(existingEntity -> {
                    DocumentTemplateTypeCatalog updatedEntity = mapper.toEntity(documentTemplateTypeDTO);
                    updatedEntity.setTypeId(typeId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    updatedEntity.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error updating document template type: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteDocumentTemplateType(UUID typeId) {
        return repository.findById(typeId)
                .switchIfEmpty(Mono.error(new RuntimeException("Document template type not found with ID: " + typeId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting document template type: " + e.getMessage(), e)));
    }
}
