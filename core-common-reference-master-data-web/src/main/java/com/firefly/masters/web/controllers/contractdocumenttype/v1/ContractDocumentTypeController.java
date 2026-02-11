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


package com.firefly.masters.web.controllers.contractdocumenttype.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.contractdocumenttype.v1.ContractDocumentTypeService;
import com.firefly.masters.interfaces.dtos.contractdocumenttype.v1.ContractDocumentTypeDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Tag(name = "Contract Document Type", description = "APIs for managing Contract Document Type data")
@RestController
@RequestMapping("/api/v1/contract-document-types")
public class ContractDocumentTypeController {

    @Autowired
    private ContractDocumentTypeService contractDocumentTypeService;

    @Operation(summary = "List Contract Document Types", description = "Retrieve a paginated list of contract document types.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of contract document types",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ContractDocumentTypeDTO>>> listContractDocumentTypes(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return contractDocumentTypeService.listContractDocumentTypes(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Contract Document Type", description = "Create a new contract document type record.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Contract document type created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractDocumentTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractDocumentTypeDTO>> createContractDocumentType(
            @RequestBody ContractDocumentTypeDTO contractDocumentTypeDto
    ) {
        return contractDocumentTypeService.createContractDocumentType(contractDocumentTypeDto)
                .map(createdDTO -> ResponseEntity.status(HttpStatus.CREATED).body(createdDTO));
    }

    @Operation(summary = "Get Contract Document Type by ID", description = "Retrieve a specific contract document type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract document type retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractDocumentTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contract document type not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{documentTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractDocumentTypeDTO>> getContractDocumentType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract document type", required = true)
            @PathVariable UUID documentTypeId
    ) {
        return contractDocumentTypeService.getContractDocumentType(documentTypeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Contract Document Type by Code", description = "Retrieve a specific contract document type by its code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract document type retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractDocumentTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contract document type not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/by-code/{documentCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractDocumentTypeDTO>> getContractDocumentTypeByCode(
            @Parameter(in = ParameterIn.PATH, description = "Code of the contract document type", required = true)
            @PathVariable String documentCode
    ) {
        return contractDocumentTypeService.getContractDocumentTypeByCode(documentCode)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Contract Document Type", description = "Update an existing contract document type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract document type updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractDocumentTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contract document type not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{documentTypeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractDocumentTypeDTO>> updateContractDocumentType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract document type", required = true)
            @PathVariable UUID documentTypeId,
            @RequestBody ContractDocumentTypeDTO contractDocumentTypeDto
    ) {
        return contractDocumentTypeService.updateContractDocumentType(documentTypeId, contractDocumentTypeDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Contract Document Type", description = "Delete a specific contract document type by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contract document type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Contract document type not found")
    })
    @DeleteMapping(value = "/{documentTypeId}")
    public Mono<ResponseEntity<Void>> deleteContractDocumentType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract document type", required = true)
            @PathVariable UUID documentTypeId
    ) {
        return contractDocumentTypeService.deleteContractDocumentType(documentTypeId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
