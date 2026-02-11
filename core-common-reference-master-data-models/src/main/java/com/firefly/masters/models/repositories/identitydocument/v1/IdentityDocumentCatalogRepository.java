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


package com.firefly.masters.models.repositories.identitydocument.v1;

import com.firefly.masters.models.entities.identitydocument.v1.IdentityDocumentCatalog;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing IdentityDocumentCatalog entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface IdentityDocumentCatalogRepository extends BaseRepository<IdentityDocumentCatalog, UUID> {

    /**
     * Find an identity document by its code.
     *
     * @param documentCode the unique code of the identity document
     * @return a Mono of IdentityDocumentCatalog
     */
    Mono<IdentityDocumentCatalog> findByDocumentCode(String documentCode);

    /**
     * Find all identity documents of a specific category.
     *
     * @param categoryId the ID of the identity document category
     * @param pageable pagination information
     * @return a Flux of IdentityDocumentCatalog entities of the specified category
     */
    Flux<IdentityDocumentCatalog> findByCategoryId(UUID categoryId, Pageable pageable);

    /**
     * Count identity documents of a specific category.
     *
     * @param categoryId the ID of the identity document category
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM identity_document_catalog WHERE category_id = :categoryId")
    Mono<Long> countByCategoryId(UUID categoryId);

    /**
     * Find all identity documents for a specific country.
     *
     * @param countryId the ID of the country
     * @param pageable pagination information
     * @return a Flux of IdentityDocumentCatalog entities for the specified country
     */
    Flux<IdentityDocumentCatalog> findByCountryId(UUID countryId, Pageable pageable);

    /**
     * Count identity documents for a specific country.
     *
     * @param countryId the ID of the country
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM identity_document_catalog WHERE country_id = :countryId")
    Mono<Long> countByCountryId(UUID countryId);
}
