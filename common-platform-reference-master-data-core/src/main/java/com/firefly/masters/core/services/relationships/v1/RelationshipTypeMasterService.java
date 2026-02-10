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


package com.firefly.masters.core.services.relationships.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.relationships.v1.RelationshipTypeMasterDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing Relationship Type master data.
 */
public interface RelationshipTypeMasterService {

    /**
     * Retrieves a paginated list of relationship types based on the provided pagination request.
     *
     * @param paginationRequest the pagination request containing page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of RelationshipTypeMasterDTO objects
     */
    Mono<PaginationResponse<RelationshipTypeMasterDTO>> listRelationshipTypes(PaginationRequest paginationRequest);


    /**
     * Creates a new relationship type record based on the provided RelationshipTypeMasterDTO.
     *
     * @param relationshipTypeDto the DTO containing details of the relationship type to be created
     * @return a Mono emitting the created RelationshipTypeMasterDTO object
     */
    Mono<RelationshipTypeMasterDTO> createRelationshipType(RelationshipTypeMasterDTO relationshipTypeDto);

    /**
     * Retrieves the details of a relationship type by its unique identifier.
     *
     * @param relationshipTypeId the unique identifier of the relationship type to retrieve
     * @return a Mono emitting the RelationshipTypeMasterDTO containing details about the specified item, or an empty Mono if not found
     */
    Mono<RelationshipTypeMasterDTO> getRelationshipType(UUID relationshipTypeId);

    /**
     * Updates the details of an existing relationship type by its unique identifier.
     *
     * @param relationshipTypeId  the unique identifier of the relationship type to be updated
     * @param relationshipTypeDto the data transfer object containing the updated details
     * @return a Mono emitting the updated RelationshipTypeMasterDTO object if the update is successful
     */
    Mono<RelationshipTypeMasterDTO> updateRelationshipType(UUID relationshipTypeId, RelationshipTypeMasterDTO relationshipTypeDto);

    /**
     * Deletes a relationship type identified by its unique identifier.
     *
     * @param relationshipTypeId the unique identifier of the relationship type to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteRelationshipType(UUID relationshipTypeId);
}