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


package com.firefly.masters.core.services.title.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.title.v1.TitleMasterDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing Title Master data.
 */
public interface TitleMasterService {

    /**
     * Retrieves a paginated list of titles based on the provided pagination request.
     *
     * @param paginationRequest the pagination request containing page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of TitleMasterDTO objects
     */
    Mono<PaginationResponse<TitleMasterDTO>> listTitles(PaginationRequest paginationRequest);

    /**
     * Creates a new title master record based on the provided TitleMasterDTO.
     *
     * @param titleDto the DTO containing details of the title to be created
     * @return a Mono emitting the created TitleMasterDTO object
     */
    Mono<TitleMasterDTO> createTitle(TitleMasterDTO titleDto);

    /**
     * Retrieves the details of a title by its unique identifier.
     *
     * @param titleId the unique identifier of the title to retrieve
     * @return a Mono emitting the TitleMasterDTO containing details about the specified title, or an empty Mono if not found
     */
    Mono<TitleMasterDTO> getTitle(UUID titleId);

    /**
     * Updates the details of an existing title by its unique identifier.
     *
     * @param titleId  the unique identifier of the title to be updated
     * @param titleDto the DTO containing the updated title details
     * @return a Mono emitting the updated TitleMasterDTO object if the update is successful
     */
    Mono<TitleMasterDTO> updateTitle(UUID titleId, TitleMasterDTO titleDto);

    /**
     * Deletes a title identified by its unique identifier.
     *
     * @param titleId the unique identifier of the title to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteTitle(UUID titleId);
}