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


package com.firefly.masters.core.services.consent.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.consent.v1.ConsentCatalogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing consent catalog.
 */
public interface ConsentCatalogService {

    /**
     * Retrieves a paginated list of consent catalog entries based on the provided pagination request.
     *
     * @param paginationRequest the request object containing pagination details,
     *                          such as page number and size, for fetching the paginated list.
     * @return a {@link Mono} emitting a {@link PaginationResponse} that contains a list of
     *         {@link ConsentCatalogDTO} objects matching the criteria specified
     *         in the pagination request.
     */
    Mono<PaginationResponse<ConsentCatalogDTO>> listConsentCatalog(PaginationRequest paginationRequest);

    /**
     * Retrieves a paginated list of consent catalog entries of a specific type.
     *
     * @param consentType the type of consent
     * @param paginationRequest the request object containing pagination details
     * @return a {@link Mono} emitting a {@link PaginationResponse} that contains a list of
     *         {@link ConsentCatalogDTO} objects of the specified type
     */
    Mono<PaginationResponse<ConsentCatalogDTO>> listConsentCatalogByType(String consentType, PaginationRequest paginationRequest);

    /**
     * Creates a new consent catalog entry with the provided details.
     *
     * @param dto the details of the consent catalog entry to be created
     * @return a {@link Mono} that emits the created {@link ConsentCatalogDTO} upon success.
     */
    Mono<ConsentCatalogDTO> createConsentCatalog(ConsentCatalogDTO dto);

    /**
     * Retrieves a consent catalog entry by its unique identifier.
     *
     * @param id the unique identifier of the consent catalog entry to retrieve
     * @return a {@code Mono} containing the {@code ConsentCatalogDTO} if found, or an empty {@code Mono} if not found
     */
    Mono<ConsentCatalogDTO> getConsentCatalog(UUID id);

    /**
     * Updates the consent catalog entry corresponding to the specified identifier.
     *
     * @param id the unique identifier of the consent catalog entry to be updated
     * @param dto the data transfer object containing the updated details for the consent catalog entry
     * @return a Mono containing the updated ConsentCatalogDTO
     */
    Mono<ConsentCatalogDTO> updateConsentCatalog(UUID id, ConsentCatalogDTO dto);

    /**
     * Deletes the consent catalog entry associated with the specified ID.
     *
     * @param id the unique identifier of the consent catalog entry to be deleted
     * @return a Mono that completes when the deletion operation is finished
     */
    Mono<Void> deleteConsentCatalog(UUID id);
}