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
import com.firefly.masters.interfaces.dtos.document.v1.DocumentTemplateTypeCatalogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing document template type catalog operations.
 */
public interface DocumentTemplateTypeCatalogService {

    /**
     * List all document template types with pagination.
     *
     * @param paginationRequest pagination parameters
     * @return a paginated response of document template type DTOs
     */
    Mono<PaginationResponse<DocumentTemplateTypeCatalogDTO>> listDocumentTemplateTypes(PaginationRequest paginationRequest);

    /**
     * Create a new document template type.
     *
     * @param documentTemplateTypeDTO the document template type data
     * @return the created document template type DTO
     */
    Mono<DocumentTemplateTypeCatalogDTO> createDocumentTemplateType(DocumentTemplateTypeCatalogDTO documentTemplateTypeDTO);

    /**
     * Get a document template type by ID.
     *
     * @param typeId the ID of the document template type
     * @return the document template type DTO
     */
    Mono<DocumentTemplateTypeCatalogDTO> getDocumentTemplateType(UUID typeId);

    /**
     * Get a document template type by code.
     *
     * @param typeCode the code of the document template type
     * @return the document template type DTO
     */
    Mono<DocumentTemplateTypeCatalogDTO> getDocumentTemplateTypeByCode(String typeCode);

    /**
     * Update a document template type.
     *
     * @param typeId the ID of the document template type to update
     * @param documentTemplateTypeDTO the updated document template type data
     * @return the updated document template type DTO
     */
    Mono<DocumentTemplateTypeCatalogDTO> updateDocumentTemplateType(UUID typeId, DocumentTemplateTypeCatalogDTO documentTemplateTypeDTO);

    /**
     * Delete a document template type.
     *
     * @param typeId the ID of the document template type to delete
     * @return a Mono of Void
     */
    Mono<Void> deleteDocumentTemplateType(UUID typeId);
}
