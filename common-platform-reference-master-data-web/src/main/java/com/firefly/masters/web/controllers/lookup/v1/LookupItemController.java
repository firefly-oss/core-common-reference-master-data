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


package com.firefly.masters.web.controllers.lookup.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.lookup.v1.LookupItemService;
import com.firefly.masters.interfaces.dtos.lookup.v1.LookupItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Tag(name = "Lookup Items", description = "APIs for managing lookup items")
@RestController
@RequestMapping("/api/v1/lookup/items")
public class LookupItemController {

    @Autowired
    private LookupItemService service;

    @Operation(summary = "List Lookup Items", description = "Retrieve a paginated list of lookup items.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of lookup items",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<LookupItemDTO>>> listItems(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listItems(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Lookup Items by Domain", description = "Retrieve all lookup items for a specific domain.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved lookup items for the domain",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LookupItemDTO.class)
                    )
            )
    })
    @GetMapping(value = "/domain/{domainId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LookupItemDTO>>> getItemsByDomain(
            @Parameter(in = ParameterIn.PATH, description = "ID of the domain", required = true)
            @PathVariable UUID domainId
    ) {
        return Mono.just(ResponseEntity.ok(service.getItemsByDomain(domainId)));
    }

    @Operation(summary = "Create Lookup Item", description = "Create a new lookup item.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lookup item created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LookupItemDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LookupItemDTO>> createItem(
            @RequestBody LookupItemDTO itemDto
    ) {
        return service.createItem(itemDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Lookup Item by ID", description = "Retrieve a specific lookup item by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lookup item retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LookupItemDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Lookup item not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LookupItemDTO>> getItem(
            @Parameter(in = ParameterIn.PATH, description = "ID of the lookup item", required = true)
            @PathVariable UUID itemId
    ) {
        return service.getItem(itemId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Lookup Item", description = "Update an existing lookup item by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lookup item updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LookupItemDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Lookup item not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LookupItemDTO>> updateItem(
            @Parameter(in = ParameterIn.PATH, description = "ID of the lookup item", required = true)
            @PathVariable UUID itemId,
            @RequestBody LookupItemDTO itemDto
    ) {
        return service.updateItem(itemId, itemDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Lookup Item", description = "Delete a specific lookup item by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Lookup item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Lookup item not found")
    })
    @DeleteMapping(value = "/{itemId}")
    public Mono<ResponseEntity<Void>> deleteItem(
            @Parameter(in = ParameterIn.PATH, description = "ID of the lookup item", required = true)
            @PathVariable UUID itemId
    ) {
        return service.deleteItem(itemId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}