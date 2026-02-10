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


package com.firefly.masters.web.controllers.consent.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.consent.v1.ConsentCatalogServiceImpl;
import com.firefly.masters.interfaces.dtos.consent.v1.ConsentCatalogDTO;
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
import reactor.core.publisher.Mono;
import java.util.UUID;

@Tag(name = "ConsentCatalog", description = "APIs for managing consent catalog")
@RestController
@RequestMapping("/api/v1/consent-catalog")
public class ConsentCatalogController {

    @Autowired
    private ConsentCatalogServiceImpl service;

    @Operation(summary = "List Consent Catalog", description = "Retrieve a paginated list of consent catalog entries.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of consent catalog entries",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ConsentCatalogDTO>>> listConsentCatalog(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listConsentCatalog(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Consent Catalog by Type", description = "Retrieve a paginated list of consent catalog entries of a specific type.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of consent catalog entries of the specified type",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/type/{consentType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ConsentCatalogDTO>>> listConsentCatalogByType(
            @Parameter(in = ParameterIn.PATH, description = "Type of consent", required = true)
            @PathVariable String consentType,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listConsentCatalogByType(consentType, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Consent Catalog Entry", description = "Create a new consent catalog entry.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consent catalog entry created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsentCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ConsentCatalogDTO>> createConsentCatalog(
            @RequestBody ConsentCatalogDTO dto
    ) {
        return service.createConsentCatalog(dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Consent Catalog Entry by ID", description = "Retrieve a specific consent catalog entry by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consent catalog entry retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsentCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Consent catalog entry not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ConsentCatalogDTO>> getConsentCatalog(
            @Parameter(in = ParameterIn.PATH, description = "ID of the consent catalog entry", required = true)
            @PathVariable UUID id
    ) {
        return service.getConsentCatalog(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Consent Catalog Entry", description = "Update an existing consent catalog entry by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consent catalog entry updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConsentCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Consent catalog entry not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ConsentCatalogDTO>> updateConsentCatalog(
            @Parameter(in = ParameterIn.PATH, description = "ID of the consent catalog entry", required = true)
            @PathVariable UUID id,
            @RequestBody ConsentCatalogDTO dto
    ) {
        return service.updateConsentCatalog(id, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Consent Catalog Entry", description = "Delete a specific consent catalog entry by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Consent catalog entry deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Consent catalog entry not found")
    })
    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> deleteConsentCatalog(
            @Parameter(in = ParameterIn.PATH, description = "ID of the consent catalog entry", required = true)
            @PathVariable UUID id
    ) {
        return service.deleteConsentCatalog(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}