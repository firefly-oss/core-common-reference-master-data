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


package com.firefly.masters.web.controllers.relationships.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.relationships.v1.RelationshipTypeMasterService;
import com.firefly.masters.interfaces.dtos.relationships.v1.RelationshipTypeMasterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Tag(name = "Relationship Type Master", description = "APIs for managing Relationship Type Master data")
@RestController
@RequestMapping("/api/v1/relationship-types")
public class RelationshipTypeMasterController {

    @Autowired
    private RelationshipTypeMasterService relationshipTypeMasterService;

    @Operation(summary = "List Relationship Types", description = "Retrieve a paginated list of relationship types.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of relationship types",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<RelationshipTypeMasterDTO>>> listRelationshipTypes(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return relationshipTypeMasterService.listRelationshipTypes(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Relationship Type", description = "Create a new relationship type record.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Relationship type created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RelationshipTypeMasterDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RelationshipTypeMasterDTO>> createRelationshipType(
            @RequestBody RelationshipTypeMasterDTO relationshipTypeDto
    ) {
        return relationshipTypeMasterService.createRelationshipType(relationshipTypeDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Relationship Type by ID", description = "Retrieve a specific relationship type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Relationship type retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RelationshipTypeMasterDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relationship type not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{relationshipTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RelationshipTypeMasterDTO>> getRelationshipType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the relationship type", required = true)
            @PathVariable UUID relationshipTypeId
    ) {
        return relationshipTypeMasterService.getRelationshipType(relationshipTypeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Relationship Type", description = "Update an existing relationship type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Relationship type updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RelationshipTypeMasterDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relationship type not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{relationshipTypeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RelationshipTypeMasterDTO>> updateRelationshipType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the relationship type", required = true)
            @PathVariable UUID relationshipTypeId,
            @RequestBody RelationshipTypeMasterDTO relationshipTypeDto
    ) {
        return relationshipTypeMasterService.updateRelationshipType(relationshipTypeId, relationshipTypeDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Relationship Type", description = "Delete a specific relationship type by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Relationship type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Relationship type not found")
    })
    @DeleteMapping(value = "/{relationshipTypeId}")
    public Mono<ResponseEntity<Void>> deleteRelationshipType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the relationship type", required = true)
            @PathVariable UUID relationshipTypeId
    ) {
        return relationshipTypeMasterService.deleteRelationshipType(relationshipTypeId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}