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


package com.firefly.masters.core.services.lookup.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.lookup.v1.LookupDomainDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface LookupDomainService {
    /**
     * Retrieves a paginated list of lookup domains based on the provided pagination request.
     *
     * @param paginationRequest the pagination request containing page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of LookupDomainDTO objects
     */
    Mono<PaginationResponse<LookupDomainDTO>> listDomains(PaginationRequest paginationRequest);
    
    /**
     * Creates a new lookup domain based on the provided LookupDomainDTO.
     *
     * @param domainDto the lookup domain data transfer object containing details of the domain to be created
     * @return a Mono emitting the created LookupDomainDTO object
     */
    Mono<LookupDomainDTO> createDomain(LookupDomainDTO domainDto);
    
    /**
     * Retrieves the details of a lookup domain by its unique identifier.
     *
     * @param domainId the unique identifier of the lookup domain to retrieve
     * @return a Mono emitting the LookupDomainDTO containing details about the specified domain, or an empty Mono if not found
     */
    Mono<LookupDomainDTO> getDomain(UUID domainId);
    
    /**
     * Updates the details of an existing lookup domain by its unique identifier.
     *
     * @param domainId the unique identifier of the lookup domain to be updated
     * @param domainDto the data transfer object containing the updated lookup domain details
     * @return a Mono emitting the updated LookupDomainDTO object if the update is successful
     */
    Mono<LookupDomainDTO> updateDomain(UUID domainId, LookupDomainDTO domainDto);
    
    /**
     * Deletes a lookup domain identified by its unique identifier.
     *
     * @param domainId the unique identifier of the lookup domain to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteDomain(UUID domainId);
}