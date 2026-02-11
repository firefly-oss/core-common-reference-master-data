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


package com.firefly.masters.web.controllers.document.v1;

import com.firefly.masters.interfaces.dtos.document.v1.DocumentTemplateLocalizationDTO;
import com.firefly.masters.core.services.document.v1.DocumentTemplateLocalizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing document template localization operations.
 */
@Tag(name = "Document Template Localizations", description = "APIs for managing document template localizations")
@RestController
@RequestMapping("/api/v1/document-template-localizations")
public class DocumentTemplateLocalizationController {

    @Autowired
    private DocumentTemplateLocalizationService service;

    @Operation(summary = "Get Localizations by Template ID", description = "Retrieve all localizations for a specific document template.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved localizations",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No localizations found for the specified template"
            )
    })
    @GetMapping(value = "/template/{templateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<DocumentTemplateLocalizationDTO>>> getLocalizationsByTemplateId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template", required = true)
            @PathVariable UUID templateId
    ) {
        return Mono.just(ResponseEntity.ok(service.getLocalizationsByTemplateId(templateId)));
    }

    @Operation(summary = "Get Localizations by Locale ID", description = "Retrieve all localizations for a specific locale.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved localizations",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No localizations found for the specified locale"
            )
    })
    @GetMapping(value = "/locale/{localeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<DocumentTemplateLocalizationDTO>>> getLocalizationsByLocaleId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the language locale", required = true)
            @PathVariable UUID localeId
    ) {
        return Mono.just(ResponseEntity.ok(service.getLocalizationsByLocaleId(localeId)));
    }

    @Operation(summary = "Create Document Template Localization", description = "Create a new document template localization.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Document template localization created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateLocalizationDTO.class)
                    )
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateLocalizationDTO>> createDocumentTemplateLocalization(
            @RequestBody DocumentTemplateLocalizationDTO localizationDTO
    ) {
        return service.createDocumentTemplateLocalization(localizationDTO)
                .map(createdDTO -> ResponseEntity.status(HttpStatus.CREATED).body(createdDTO));
    }

    @Operation(summary = "Get Document Template Localization by ID", description = "Retrieve a document template localization by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved document template localization",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template localization not found"
            )
    })
    @GetMapping(value = "/{localizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateLocalizationDTO>> getDocumentTemplateLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template localization", required = true)
            @PathVariable UUID localizationId
    ) {
        return service.getDocumentTemplateLocalization(localizationId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Document Template Localization by Template and Locale", description = "Retrieve a document template localization by template ID and locale ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved document template localization",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template localization not found"
            )
    })
    @GetMapping(value = "/template/{templateId}/locale/{localeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateLocalizationDTO>> getDocumentTemplateLocalizationByTemplateAndLocale(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template", required = true)
            @PathVariable UUID templateId,
            @Parameter(in = ParameterIn.PATH, description = "ID of the language locale", required = true)
            @PathVariable UUID localeId
    ) {
        return service.getDocumentTemplateLocalizationByTemplateAndLocale(templateId, localeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Document Template Localization", description = "Update an existing document template localization.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Document template localization updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template localization not found"
            )
    })
    @PutMapping(value = "/{localizationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateLocalizationDTO>> updateDocumentTemplateLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template localization", required = true)
            @PathVariable UUID localizationId,
            @RequestBody DocumentTemplateLocalizationDTO localizationDTO
    ) {
        return service.updateDocumentTemplateLocalization(localizationId, localizationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Document Template Localization", description = "Delete a document template localization by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Document template localization deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template localization not found"
            )
    })
    @DeleteMapping(value = "/{localizationId}")
    public Mono<ResponseEntity<Void>> deleteDocumentTemplateLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template localization", required = true)
            @PathVariable UUID localizationId
    ) {
        return service.deleteDocumentTemplateLocalization(localizationId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Localizations by Template ID", description = "Delete all localizations for a specific document template.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Document template localizations deleted successfully"
            )
    })
    @DeleteMapping(value = "/template/{templateId}")
    public Mono<ResponseEntity<Void>> deleteLocalizationsByTemplateId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template", required = true)
            @PathVariable UUID templateId
    ) {
        return service.deleteLocalizationsByTemplateId(templateId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
