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


package com.firefly.masters.core.services.division.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.division.v1.AdministrativeDivisionDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface AdministrativeDivisionService {
    /**
     * Retrieves a paginated list of administrative divisions based on the provided pagination request.
     *
     * @param paginationRequest the pagination request containing page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of AdministrativeDivisionDTO objects
     */
    Mono<PaginationResponse<AdministrativeDivisionDTO>> listDivisions(PaginationRequest paginationRequest);
    
    /**
     * Creates a new administrative division based on the provided AdministrativeDivisionDTO.
     *
     * @param divisionDto the administrative division data transfer object containing details of the division to be created
     * @return a Mono emitting the created AdministrativeDivisionDTO object
     */
    Mono<AdministrativeDivisionDTO> createDivision(AdministrativeDivisionDTO divisionDto);
    
    /**
     * Retrieves the details of an administrative division by its unique identifier.
     *
     * @param divisionId the unique identifier of the administrative division to retrieve
     * @return a Mono emitting the AdministrativeDivisionDTO containing details about the specified division, or an empty Mono if not found
     */
    Mono<AdministrativeDivisionDTO> getDivision(UUID divisionId);
    
    /**
     * Updates the details of an existing administrative division by its unique identifier.
     *
     * @param divisionId the unique identifier of the administrative division to be updated
     * @param divisionDto the data transfer object containing the updated administrative division details
     * @return a Mono emitting the updated AdministrativeDivisionDTO object if the update is successful
     */
    Mono<AdministrativeDivisionDTO> updateDivision(UUID divisionId, AdministrativeDivisionDTO divisionDto);
    
    /**
     * Deletes an administrative division identified by its unique identifier.
     *
     * @param divisionId the unique identifier of the administrative division to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteDivision(UUID divisionId);
}