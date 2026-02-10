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
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCategoryCatalogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing identity document category catalog operations.
 */
public interface IdentityDocumentCategoryCatalogService {

    /**
     * List all identity document categories with pagination.
     *
     * @param paginationRequest pagination parameters
     * @return a paginated list of identity document categories
     */
    Mono<PaginationResponse<IdentityDocumentCategoryCatalogDTO>> listIdentityDocumentCategories(PaginationRequest paginationRequest);

    /**
     * Create a new identity document category.
     *
     * @param identityDocumentCategoryDTO the identity document category to create
     * @return the created identity document category
     */
    Mono<IdentityDocumentCategoryCatalogDTO> createIdentityDocumentCategory(IdentityDocumentCategoryCatalogDTO identityDocumentCategoryDTO);

    /**
     * Get an identity document category by its ID.
     *
     * @param categoryId the ID of the identity document category
     * @return the identity document category with the specified ID
     */
    Mono<IdentityDocumentCategoryCatalogDTO> getIdentityDocumentCategory(UUID categoryId);

    /**
     * Get an identity document category by its code.
     *
     * @param categoryCode the code of the identity document category
     * @return the identity document category with the specified code
     */
    Mono<IdentityDocumentCategoryCatalogDTO> getIdentityDocumentCategoryByCode(String categoryCode);

    /**
     * Update an existing identity document category.
     *
     * @param categoryId the ID of the identity document category to update
     * @param identityDocumentCategoryDTO the updated identity document category data
     * @return the updated identity document category
     */
    Mono<IdentityDocumentCategoryCatalogDTO> updateIdentityDocumentCategory(UUID categoryId, IdentityDocumentCategoryCatalogDTO identityDocumentCategoryDTO);

    /**
     * Delete an identity document category.
     *
     * @param categoryId the ID of the identity document category to delete
     * @return a Mono of Void
     */
    Mono<Void> deleteIdentityDocumentCategory(UUID categoryId);
}
