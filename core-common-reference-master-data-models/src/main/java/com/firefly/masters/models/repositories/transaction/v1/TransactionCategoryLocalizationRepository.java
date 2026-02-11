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


package com.firefly.masters.models.repositories.transaction.v1;

import com.firefly.masters.models.entities.transaction.v1.TransactionCategoryLocalization;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing TransactionCategoryLocalization entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface TransactionCategoryLocalizationRepository extends BaseRepository<TransactionCategoryLocalization, UUID> {

    /**
     * Find all localizations for a specific transaction category.
     *
     * @param categoryId the ID of the transaction category
     * @return a Flux of TransactionCategoryLocalization entities for the specified category
     */
    Flux<TransactionCategoryLocalization> findByCategoryId(UUID categoryId);

    /**
     * Find all localizations for a specific transaction category with pagination.
     *
     * @param categoryId the ID of the transaction category
     * @param pageable pagination information
     * @return a Flux of TransactionCategoryLocalization entities for the specified category
     */
    Flux<TransactionCategoryLocalization> findByCategoryId(UUID categoryId, Pageable pageable);

    /**
     * Count all localizations for a specific transaction category.
     *
     * @param categoryId the ID of the transaction category
     * @return a Mono with the count of localizations for the specified category
     */
    Mono<Long> countByCategoryId(UUID categoryId);

    /**
     * Find a localization for a specific transaction category and locale.
     *
     * @param categoryId the ID of the transaction category
     * @param localeId the ID of the locale
     * @return a Mono of TransactionCategoryLocalization for the specified category and locale
     */
    Mono<TransactionCategoryLocalization> findByCategoryIdAndLocaleId(UUID categoryId, UUID localeId);

    /**
     * Delete all localizations for a specific transaction category.
     *
     * @param categoryId the ID of the transaction category
     * @return a Mono that completes when the localizations are deleted
     */
    Mono<Void> deleteByCategoryId(UUID categoryId);
}
