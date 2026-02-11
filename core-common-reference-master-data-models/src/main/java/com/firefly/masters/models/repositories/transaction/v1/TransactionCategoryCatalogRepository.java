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

import com.firefly.masters.models.entities.transaction.v1.TransactionCategoryCatalog;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing TransactionCategoryCatalog entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface TransactionCategoryCatalogRepository extends BaseRepository<TransactionCategoryCatalog, UUID> {

    /**
     * Find a transaction category by its code.
     *
     * @param categoryCode the unique code of the transaction category
     * @return a Mono of TransactionCategoryCatalog
     */
    Mono<TransactionCategoryCatalog> findByCategoryCode(String categoryCode);

    /**
     * Find all transaction categories with a specific parent category.
     *
     * @param parentCategoryId the ID of the parent category
     * @param pageable pagination information
     * @return a Flux of TransactionCategoryCatalog entities with the specified parent
     */
    Flux<TransactionCategoryCatalog> findByParentCategoryId(UUID parentCategoryId, Pageable pageable);

    /**
     * Count all transaction categories with a specific parent category.
     *
     * @param parentCategoryId the ID of the parent category
     * @return a Mono with the count of categories with the specified parent
     */
    Mono<Long> countByParentCategoryId(UUID parentCategoryId);

    /**
     * Find all root transaction categories (categories without a parent).
     *
     * @param pageable pagination information
     * @return a Flux of TransactionCategoryCatalog entities without a parent
     */
    @Query("SELECT * FROM transaction_category_catalog WHERE parent_category_id IS NULL")
    Flux<TransactionCategoryCatalog> findRootCategories(Pageable pageable);

    /**
     * Count all root transaction categories (categories without a parent).
     *
     * @return a Mono with the count of categories without a parent
     */
    @Query("SELECT COUNT(*) FROM transaction_category_catalog WHERE parent_category_id IS NULL")
    Mono<Long> countRootCategories();
}
