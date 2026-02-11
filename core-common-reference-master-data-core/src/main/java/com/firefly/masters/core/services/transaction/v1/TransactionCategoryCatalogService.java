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


package com.firefly.masters.core.services.transaction.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.transaction.v1.TransactionCategoryCatalogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing transaction category catalog operations.
 */
public interface TransactionCategoryCatalogService {

    /**
     * List all transaction categories with pagination.
     *
     * @param paginationRequest pagination parameters
     * @return a paginated list of transaction categories
     */
    Mono<PaginationResponse<TransactionCategoryCatalogDTO>> listTransactionCategories(PaginationRequest paginationRequest);

    /**
     * List all root transaction categories (categories without a parent) with pagination.
     *
     * @param paginationRequest pagination parameters
     * @return a paginated list of root transaction categories
     */
    Mono<PaginationResponse<TransactionCategoryCatalogDTO>> listRootTransactionCategories(PaginationRequest paginationRequest);

    /**
     * List all child transaction categories for a specific parent category with pagination.
     *
     * @param parentCategoryId the ID of the parent category
     * @param paginationRequest pagination parameters
     * @return a paginated list of child transaction categories
     */
    Mono<PaginationResponse<TransactionCategoryCatalogDTO>> listChildTransactionCategories(UUID parentCategoryId, PaginationRequest paginationRequest);

    /**
     * Create a new transaction category.
     *
     * @param transactionCategoryDTO the transaction category to create
     * @return the created transaction category
     */
    Mono<TransactionCategoryCatalogDTO> createTransactionCategory(TransactionCategoryCatalogDTO transactionCategoryDTO);

    /**
     * Get a transaction category by its ID.
     *
     * @param categoryId the ID of the transaction category
     * @return the transaction category with the specified ID
     */
    Mono<TransactionCategoryCatalogDTO> getTransactionCategory(UUID categoryId);

    /**
     * Get a transaction category by its code.
     *
     * @param categoryCode the code of the transaction category
     * @return the transaction category with the specified code
     */
    Mono<TransactionCategoryCatalogDTO> getTransactionCategoryByCode(String categoryCode);

    /**
     * Update a transaction category.
     *
     * @param categoryId the ID of the transaction category to update
     * @param transactionCategoryDTO the updated transaction category data
     * @return the updated transaction category
     */
    Mono<TransactionCategoryCatalogDTO> updateTransactionCategory(UUID categoryId, TransactionCategoryCatalogDTO transactionCategoryDTO);

    /**
     * Delete a transaction category.
     *
     * @param categoryId the ID of the transaction category to delete
     * @return a Mono that completes when the transaction category is deleted
     */
    Mono<Void> deleteTransactionCategory(UUID categoryId);
}
