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
import com.firefly.masters.interfaces.dtos.document.v1.DocumentTemplateCatalogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing document template catalog operations.
 */
public interface DocumentTemplateCatalogService {

    /**
     * List all document templates with pagination.
     *
     * @param paginationRequest pagination parameters
     * @return a paginated response of document template DTOs
     */
    Mono<PaginationResponse<DocumentTemplateCatalogDTO>> listDocumentTemplates(PaginationRequest paginationRequest);

    /**
     * List document templates by category with pagination.
     *
     * @param category the category of templates to list
     * @param paginationRequest pagination parameters
     * @return a paginated response of document template DTOs
     */
    Mono<PaginationResponse<DocumentTemplateCatalogDTO>> listDocumentTemplatesByCategory(String category, PaginationRequest paginationRequest);

    /**
     * List document templates by type ID with pagination.
     *
     * @param typeId the type ID of templates to list
     * @param paginationRequest pagination parameters
     * @return a paginated response of document template DTOs
     */
    Mono<PaginationResponse<DocumentTemplateCatalogDTO>> listDocumentTemplatesByTypeId(UUID typeId, PaginationRequest paginationRequest);

    /**
     * Create a new document template.
     *
     * @param documentTemplateDTO the document template data
     * @return the created document template DTO
     */
    Mono<DocumentTemplateCatalogDTO> createDocumentTemplate(DocumentTemplateCatalogDTO documentTemplateDTO);

    /**
     * Get a document template by ID.
     *
     * @param templateId the ID of the document template
     * @return the document template DTO
     */
    Mono<DocumentTemplateCatalogDTO> getDocumentTemplate(UUID templateId);

    /**
     * Get a document template by code.
     *
     * @param templateCode the code of the document template
     * @return the document template DTO
     */
    Mono<DocumentTemplateCatalogDTO> getDocumentTemplateByCode(String templateCode);

    /**
     * Update a document template.
     *
     * @param templateId the ID of the document template to update
     * @param documentTemplateDTO the updated document template data
     * @return the updated document template DTO
     */
    Mono<DocumentTemplateCatalogDTO> updateDocumentTemplate(UUID templateId, DocumentTemplateCatalogDTO documentTemplateDTO);

    /**
     * Delete a document template.
     *
     * @param templateId the ID of the document template to delete
     * @return a Mono of Void
     */
    Mono<Void> deleteDocumentTemplate(UUID templateId);
}
