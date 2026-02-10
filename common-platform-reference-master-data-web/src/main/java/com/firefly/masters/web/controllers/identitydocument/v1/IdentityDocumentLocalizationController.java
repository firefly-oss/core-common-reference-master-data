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
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentLocalizationDTO;
import com.firefly.masters.core.services.identitydocument.v1.IdentityDocumentLocalizationService;
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
 * REST controller for managing identity document localization operations.
 */
@Tag(name = "Identity Document Localizations", description = "API for managing identity document localizations")
@RestController
@RequestMapping("/api/v1/identity-document-localizations")
public class IdentityDocumentLocalizationController {

    @Autowired
    private IdentityDocumentLocalizationService service;

    @Operation(summary = "List Identity Document Localizations", description = "Retrieve a paginated list of identity document localizations.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of identity document localizations",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<IdentityDocumentLocalizationDTO>>> listIdentityDocumentLocalizations(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listIdentityDocumentLocalizations(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Localizations by Document ID", description = "Retrieve a paginated list of localizations for a specific identity document.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of localizations for the specified document",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/document/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<IdentityDocumentLocalizationDTO>>> getLocalizationsByDocumentId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document", required = true)
            @PathVariable UUID documentId,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.getLocalizationsByDocumentId(documentId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Identity Document Localization", description = "Create a new identity document localization.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Identity document localization created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentLocalizationDTO>> createIdentityDocumentLocalization(
            @RequestBody IdentityDocumentLocalizationDTO localizationDTO
    ) {
        return service.createIdentityDocumentLocalization(localizationDTO)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Identity Document Localization by Document and Locale", description = "Get a specific identity document localization by document ID and locale ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the identity document localization",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Identity document localization not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/document/{documentId}/locale/{localeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentLocalizationDTO>> getIdentityDocumentLocalizationByDocumentAndLocale(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document", required = true)
            @PathVariable UUID documentId,
            @Parameter(in = ParameterIn.PATH, description = "ID of the language locale", required = true)
            @PathVariable UUID localeId
    ) {
        return service.getIdentityDocumentLocalizationByDocumentAndLocale(documentId, localeId)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "Update Identity Document Localization", description = "Update a specific identity document localization by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Identity document localization updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdentityDocumentLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Identity document localization not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PutMapping(value = "/{localizationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<IdentityDocumentLocalizationDTO>> updateIdentityDocumentLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document localization", required = true)
            @PathVariable UUID localizationId,
            @RequestBody IdentityDocumentLocalizationDTO localizationDTO
    ) {
        return service.updateIdentityDocumentLocalization(localizationId, localizationDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "Delete Identity Document Localization", description = "Delete a specific identity document localization by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Identity document localization deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Identity document localization not found")
    })
    @DeleteMapping(value = "/{localizationId}")
    public Mono<ResponseEntity<Void>> deleteIdentityDocumentLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document localization", required = true)
            @PathVariable UUID localizationId
    ) {
        return service.deleteIdentityDocumentLocalization(localizationId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "Delete Localizations by Document ID", description = "Delete all localizations for a specific identity document.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Identity document localizations deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Identity document not found")
    })
    @DeleteMapping(value = "/document/{documentId}")
    public Mono<ResponseEntity<Void>> deleteLocalizationsByDocumentId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the identity document", required = true)
            @PathVariable UUID documentId
    ) {
        return service.deleteLocalizationsByDocumentId(documentId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
