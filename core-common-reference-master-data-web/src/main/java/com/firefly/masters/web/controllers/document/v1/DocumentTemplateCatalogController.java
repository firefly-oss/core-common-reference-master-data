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
import com.firefly.masters.interfaces.dtos.document.v1.DocumentTemplateCatalogDTO;
import com.firefly.masters.core.services.document.v1.DocumentTemplateCatalogService;
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
 * REST controller for managing document template catalog operations.
 */
@Tag(name = "Document Templates", description = "APIs for managing document templates")
@RestController
@RequestMapping("/api/v1/document-templates")
public class DocumentTemplateCatalogController {

    @Autowired
    private DocumentTemplateCatalogService service;

    @Operation(summary = "List Document Templates", description = "Retrieve a paginated list of document templates.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of document templates",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<DocumentTemplateCatalogDTO>>> listDocumentTemplates(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listDocumentTemplates(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Document Templates by Category", description = "Retrieve a paginated list of document templates for a specific category.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of document templates",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<DocumentTemplateCatalogDTO>>> listDocumentTemplatesByCategory(
            @Parameter(in = ParameterIn.PATH, description = "Category of templates", required = true)
            @PathVariable String category,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listDocumentTemplatesByCategory(category, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Document Templates by Type", description = "Retrieve a paginated list of document templates for a specific template type.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of document templates",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/type/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<DocumentTemplateCatalogDTO>>> listDocumentTemplatesByTypeId(
            @Parameter(in = ParameterIn.PATH, description = "Template type ID", required = true)
            @PathVariable UUID typeId,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listDocumentTemplatesByTypeId(typeId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Document Template", description = "Create a new document template.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Document template created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateCatalogDTO.class)
                    )
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateCatalogDTO>> createDocumentTemplate(
            @RequestBody DocumentTemplateCatalogDTO documentTemplateDTO
    ) {
        return service.createDocumentTemplate(documentTemplateDTO)
                .map(createdDTO -> ResponseEntity.status(HttpStatus.CREATED).body(createdDTO));
    }

    @Operation(summary = "Get Document Template by ID", description = "Retrieve a document template by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved document template",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template not found"
            )
    })
    @GetMapping(value = "/{templateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateCatalogDTO>> getDocumentTemplate(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template", required = true)
            @PathVariable UUID templateId
    ) {
        return service.getDocumentTemplate(templateId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Document Template by Code", description = "Retrieve a document template by its code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved document template",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template not found"
            )
    })
    @GetMapping(value = "/code/{templateCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateCatalogDTO>> getDocumentTemplateByCode(
            @Parameter(in = ParameterIn.PATH, description = "Code of the document template", required = true)
            @PathVariable String templateCode
    ) {
        return service.getDocumentTemplateByCode(templateCode)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Document Template", description = "Update an existing document template.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Document template updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DocumentTemplateCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template not found"
            )
    })
    @PutMapping(value = "/{templateId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DocumentTemplateCatalogDTO>> updateDocumentTemplate(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template", required = true)
            @PathVariable UUID templateId,
            @RequestBody DocumentTemplateCatalogDTO documentTemplateDTO
    ) {
        return service.updateDocumentTemplate(templateId, documentTemplateDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Document Template", description = "Delete a document template by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Document template deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document template not found"
            )
    })
    @DeleteMapping(value = "/{templateId}")
    public Mono<ResponseEntity<Void>> deleteDocumentTemplate(
            @Parameter(in = ParameterIn.PATH, description = "ID of the document template", required = true)
            @PathVariable UUID templateId
    ) {
        return service.deleteDocumentTemplate(templateId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
