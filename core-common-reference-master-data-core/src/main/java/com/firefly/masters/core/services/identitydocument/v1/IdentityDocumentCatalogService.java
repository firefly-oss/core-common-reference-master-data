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
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCatalogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing identity document catalog operations.
 */
public interface IdentityDocumentCatalogService {

    /**
     * List all identity documents with pagination.
     *
     * @param paginationRequest pagination parameters
     * @return a paginated list of identity documents
     */
    Mono<PaginationResponse<IdentityDocumentCatalogDTO>> listIdentityDocuments(PaginationRequest paginationRequest);

    /**
     * List identity documents by category with pagination.
     *
     * @param categoryId the ID of the identity document category
     * @param paginationRequest pagination parameters
     * @return a paginated list of identity documents of the specified category
     */
    Mono<PaginationResponse<IdentityDocumentCatalogDTO>> listIdentityDocumentsByCategory(UUID categoryId, PaginationRequest paginationRequest);

    /**
     * List identity documents by country with pagination.
     *
     * @param countryId the ID of the country
     * @param paginationRequest pagination parameters
     * @return a paginated list of identity documents for the specified country
     */
    Mono<PaginationResponse<IdentityDocumentCatalogDTO>> listIdentityDocumentsByCountry(UUID countryId, PaginationRequest paginationRequest);

    /**
     * Create a new identity document.
     *
     * @param identityDocumentDTO the identity document to create
     * @return the created identity document
     */
    Mono<IdentityDocumentCatalogDTO> createIdentityDocument(IdentityDocumentCatalogDTO identityDocumentDTO);

    /**
     * Get an identity document by its ID.
     *
     * @param documentId the ID of the identity document
     * @return the identity document with the specified ID
     */
    Mono<IdentityDocumentCatalogDTO> getIdentityDocument(UUID documentId);

    /**
     * Get an identity document by its code.
     *
     * @param documentCode the code of the identity document
     * @return the identity document with the specified code
     */
    Mono<IdentityDocumentCatalogDTO> getIdentityDocumentByCode(String documentCode);

    /**
     * Update an existing identity document.
     *
     * @param documentId the ID of the identity document to update
     * @param identityDocumentDTO the updated identity document data
     * @return the updated identity document
     */
    Mono<IdentityDocumentCatalogDTO> updateIdentityDocument(UUID documentId, IdentityDocumentCatalogDTO identityDocumentDTO);

    /**
     * Delete an identity document.
     *
     * @param documentId the ID of the identity document to delete
     * @return a Mono of Void
     */
    Mono<Void> deleteIdentityDocument(UUID documentId);
}
