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


package com.firefly.masters.web.controllers.identitydocument.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCategoryCatalogDTO;
import com.firefly.masters.core.services.identitydocument.v1.IdentityDocumentCategoryCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing identity document category catalog operations.
 */
@RestController
@RequestMapping("/api/v1/identity-document-categories")
@Tag(name = "Identity Document Categories", description = "API for managing identity document categories")
public class IdentityDocumentCategoryCatalogController {

    @Autowired
    private IdentityDocumentCategoryCatalogService service;

    @Operation(summary = "List Identity Document Categories", description = "Retrieve a paginated list of identity document categories.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of identity document categories",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<IdentityDocumentCategoryCatalogDTO>>> listIdentityDocumentCategories(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listIdentityDocumentCategories(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Identity Document Category", description = "Create a new identity document category.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Identity document category created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentCategoryCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentCategoryCatalogDTO>> createIdentityDocumentCategory(
            @RequestBody IdentityDocumentCategoryCatalogDTO dto
    ) {
        return service.createIdentityDocumentCategory(dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Identity Document Category", description = "Get a specific identity document category by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the identity document category",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentCategoryCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Identity document category not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentCategoryCatalogDTO>> getIdentityDocumentCategory(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document category", required = true)
            @PathVariable UUID categoryId
    ) {
        return service.getIdentityDocumentCategory(categoryId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Identity Document Category by Code", description = "Get a specific identity document category by its code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the identity document category",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentCategoryCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Identity document category not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/code/{categoryCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentCategoryCatalogDTO>> getIdentityDocumentCategoryByCode(
            @Parameter(in = ParameterIn.PATH, description = "Code of the identity document category", required = true)
            @PathVariable String categoryCode
    ) {
        return service.getIdentityDocumentCategoryByCode(categoryCode)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Update Identity Document Category", description = "Update a specific identity document category by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Identity document category updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentCategoryCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Identity document category not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PutMapping(value = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentCategoryCatalogDTO>> updateIdentityDocumentCategory(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document category", required = true)
            @PathVariable UUID categoryId,
            @RequestBody IdentityDocumentCategoryCatalogDTO dto
    ) {
        return service.updateIdentityDocumentCategory(categoryId, dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Delete Identity Document Category", description = "Delete a specific identity document category by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Identity document category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Identity document category not found")
    })
    @DeleteMapping(value = "/{categoryId}")
    public Mono<ResponseEntity<Void>> deleteIdentityDocumentCategory(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document category", required = true)
            @PathVariable UUID categoryId
    ) {
        return service.deleteIdentityDocumentCategory(categoryId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
