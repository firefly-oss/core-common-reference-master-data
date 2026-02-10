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
import com.firefly.masters.interfaces.dtos.lookup.v1.LookupItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface LookupItemService {
    /**
     * Retrieves a paginated list of lookup items based on the provided pagination request.
     *
     * @param paginationRequest the pagination request containing page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of LookupItemDTO objects
     */
    Mono<PaginationResponse<LookupItemDTO>> listItems(PaginationRequest paginationRequest);
    
    /**
     * Retrieves all lookup items for a specific domain.
     *
     * @param domainId the unique identifier of the domain to retrieve items for
     * @return a Flux emitting LookupItemDTO objects for the specified domain
     */
    Flux<LookupItemDTO> getItemsByDomain(UUID domainId);
    
    /**
     * Creates a new lookup item based on the provided LookupItemDTO.
     *
     * @param itemDto the lookup item data transfer object containing details of the item to be created
     * @return a Mono emitting the created LookupItemDTO object
     */
    Mono<LookupItemDTO> createItem(LookupItemDTO itemDto);
    
    /**
     * Retrieves the details of a lookup item by its unique identifier.
     *
     * @param itemId the unique identifier of the lookup item to retrieve
     * @return a Mono emitting the LookupItemDTO containing details about the specified item, or an empty Mono if not found
     */
    Mono<LookupItemDTO> getItem(UUID itemId);
    
    /**
     * Updates the details of an existing lookup item by its unique identifier.
     *
     * @param itemId the unique identifier of the lookup item to be updated
     * @param itemDto the data transfer object containing the updated lookup item details
     * @return a Mono emitting the updated LookupItemDTO object if the update is successful
     */
    Mono<LookupItemDTO> updateItem(UUID itemId, LookupItemDTO itemDto);
    
    /**
     * Deletes a lookup item identified by its unique identifier.
     *
     * @param itemId the unique identifier of the lookup item to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteItem(UUID itemId);
}