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
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCatalogDTO;
import com.firefly.masters.core.services.identitydocument.v1.IdentityDocumentCatalogService;
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
 * REST controller for managing identity document catalog operations.
 */
@RestController
@RequestMapping("/api/v1/identity-documents")
@Tag(name = "Identity Documents", description = "API for managing identity documents")
public class IdentityDocumentCatalogController {

    @Autowired
    private IdentityDocumentCatalogService service;

    @Operation(summary = "List Identity Documents", description = "Retrieve a paginated list of identity documents.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of identity documents",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<IdentityDocumentCatalogDTO>>> listIdentityDocuments(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listIdentityDocuments(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Identity Documents by Category", description = "Retrieve a paginated list of identity documents of a specific category.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of identity documents of the specified category",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<IdentityDocumentCatalogDTO>>> listIdentityDocumentsByCategory(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document category", required = true)
            @PathVariable UUID categoryId,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listIdentityDocumentsByCategory(categoryId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Identity Documents by Country", description = "Retrieve a paginated list of identity documents for a specific country.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of identity documents for the specified country",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/country/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<IdentityDocumentCatalogDTO>>> listIdentityDocumentsByCountry(
            @Parameter(in = ParameterIn.PATH, description = "ID of the country", required = true)
            @PathVariable UUID countryId,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listIdentityDocumentsByCountry(countryId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Identity Document", description = "Create a new identity document.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Identity document created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentCatalogDTO>> createIdentityDocument(
            @RequestBody IdentityDocumentCatalogDTO dto
    ) {
        return service.createIdentityDocument(dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Identity Document", description = "Get a specific identity document by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the identity document",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Identity document not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentCatalogDTO>> getIdentityDocument(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document", required = true)
            @PathVariable UUID documentId
    ) {
        return service.getIdentityDocument(documentId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Identity Document by Code", description = "Get a specific identity document by its code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the identity document",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Identity document not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/code/{documentCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentCatalogDTO>> getIdentityDocumentByCode(
            @Parameter(in = ParameterIn.PATH, description = "Code of the identity document", required = true)
            @PathVariable String documentCode
    ) {
        return service.getIdentityDocumentByCode(documentCode)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Update Identity Document", description = "Update a specific identity document by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Identity document updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Identity document not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PutMapping(value = "/{documentId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentCatalogDTO>> updateIdentityDocument(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document", required = true)
            @PathVariable UUID documentId,
            @RequestBody IdentityDocumentCatalogDTO dto
    ) {
        return service.updateIdentityDocument(documentId, dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Delete Identity Document", description = "Delete a specific identity document by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Identity document deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Identity document not found")
    })
    @DeleteMapping(value = "/{documentId}")
    public Mono<ResponseEntity<Void>> deleteIdentityDocument(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document", required = true)
            @PathVariable UUID documentId
    ) {
        return service.deleteIdentityDocument(documentId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
