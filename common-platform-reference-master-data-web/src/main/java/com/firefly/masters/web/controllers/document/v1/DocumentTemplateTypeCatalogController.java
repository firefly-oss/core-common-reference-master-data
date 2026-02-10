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

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.document.v1.DocumentTemplateTypeCatalogDTO;
import com.firefly.masters.core.services.document.v1.DocumentTemplateTypeCatalogService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing document template type catalog operations.
 */
@Tag(name = "Document Template Types", description = "APIs for managing document template types")
@RestController
@RequestMapping("/api/v1/document-template-types")
public class DocumentTemplateTypeCatalogController {

    @Autowired
    private DocumentTemplateTypeCatalogService service;

    @Operation(summary = "List Document Template Types", description = "Retrieve a paginated list of document template types.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of document template types",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<DocumentTemplateTypeCatalogDTO>>> listDocumentTemplateTypes(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listDocumentTemplateTypes(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Document Template Type", description = "Create a new document template type.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Document template type created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateTypeCatalogDTO.class)
                    )
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateTypeCatalogDTO>> createDocumentTemplateType(
            @RequestBody DocumentTemplateTypeCatalogDTO documentTemplateTypeDTO
    ) {
        return service.createDocumentTemplateType(documentTemplateTypeDTO)
                .map(createdDTO -> ResponseEntity.status(HttpStatus.CREATED).body(createdDTO));
    }

    @Operation(summary = "Get Document Template Type by ID", description = "Retrieve a document template type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved document template type",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateTypeCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template type not found"
            )
    })
    @GetMapping(value = "/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateTypeCatalogDTO>> getDocumentTemplateType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template type", required = true)
            @PathVariable UUID typeId
    ) {
        return service.getDocumentTemplateType(typeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Document Template Type by Code", description = "Retrieve a document template type by its code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved document template type",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateTypeCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template type not found"
            )
    })
    @GetMapping(value = "/code/{typeCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateTypeCatalogDTO>> getDocumentTemplateTypeByCode(
            @Parameter(in = ParameterIn.PATH, description = "Code of the document template type", required = true)
            @PathVariable String typeCode
    ) {
        return service.getDocumentTemplateTypeByCode(typeCode)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Document Template Type", description = "Update an existing document template type.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Document template type updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateTypeCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template type not found"
            )
    })
    @PutMapping(value = "/{typeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateTypeCatalogDTO>> updateDocumentTemplateType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template type", required = true)
            @PathVariable UUID typeId,
            @RequestBody DocumentTemplateTypeCatalogDTO documentTemplateTypeDTO
    ) {
        return service.updateDocumentTemplateType(typeId, documentTemplateTypeDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Document Template Type", description = "Delete a document template type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Document template type deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template type not found"
            )
    })
    @DeleteMapping(value = "/{typeId}")
    public Mono<ResponseEntity<Void>> deleteDocumentTemplateType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template type", required = true)
            @PathVariable UUID typeId
    ) {
        return service.deleteDocumentTemplateType(typeId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
