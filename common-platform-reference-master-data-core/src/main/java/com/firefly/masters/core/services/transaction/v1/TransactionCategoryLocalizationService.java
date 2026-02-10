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
import com.firefly.masters.interfaces.dtos.transaction.v1.TransactionCategoryLocalizationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing transaction category localization operations.
 */
public interface TransactionCategoryLocalizationService {

    /**
     * List all localizations for a specific transaction category.
     *
     * @param categoryId the ID of the transaction category
     * @return a Flux of transaction category localizations
     */
    Flux<TransactionCategoryLocalizationDTO> getLocalizationsByCategoryId(UUID categoryId);

    /**
     * List all localizations for a specific transaction category with pagination.
     *
     * @param categoryId the ID of the transaction category
     * @param paginationRequest pagination parameters
     * @return a paginated list of transaction category localizations
     */
    Mono<PaginationResponse<TransactionCategoryLocalizationDTO>> listLocalizationsByCategoryId(UUID categoryId, PaginationRequest paginationRequest);

    /**
     * Create a new transaction category localization.
     *
     * @param localizationDTO the transaction category localization to create
     * @return the created transaction category localization
     */
    Mono<TransactionCategoryLocalizationDTO> createTransactionCategoryLocalization(TransactionCategoryLocalizationDTO localizationDTO);

    /**
     * Get a transaction category localization by its ID.
     *
     * @param localizationId the ID of the transaction category localization
     * @return the transaction category localization with the specified ID
     */
    Mono<TransactionCategoryLocalizationDTO> getTransactionCategoryLocalization(UUID localizationId);

    /**
     * Get a transaction category localization by category ID and locale ID.
     *
     * @param categoryId the ID of the transaction category
     * @param localeId the ID of the locale
     * @return the transaction category localization for the specified category and locale
     */
    Mono<TransactionCategoryLocalizationDTO> getTransactionCategoryLocalizationByCategoryAndLocale(UUID categoryId, UUID localeId);

    /**
     * Update a transaction category localization.
     *
     * @param localizationId the ID of the transaction category localization to update
     * @param localizationDTO the updated transaction category localization data
     * @return the updated transaction category localization
     */
    Mono<TransactionCategoryLocalizationDTO> updateTransactionCategoryLocalization(UUID localizationId, TransactionCategoryLocalizationDTO localizationDTO);

    /**
     * Delete a transaction category localization.
     *
     * @param localizationId the ID of the transaction category localization to delete
     * @return a Mono that completes when the transaction category localization is deleted
     */
    Mono<Void> deleteTransactionCategoryLocalization(UUID localizationId);
}
