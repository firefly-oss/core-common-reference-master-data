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


package com.firefly.masters.web.controllers.contracttype.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.contracttype.v1.ContractTypeService;
import com.firefly.masters.interfaces.dtos.contracttype.v1.ContractTypeDTO;
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

@Tag(name = "Contract Type", description = "APIs for managing Contract Type data")
@RestController
@RequestMapping("/api/v1/contract-types")
public class ContractTypeController {

    @Autowired
    private ContractTypeService contractTypeService;

    @Operation(summary = "List Contract Types", description = "Retrieve a paginated list of contract types.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of contract types",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ContractTypeDTO>>> listContractTypes(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return contractTypeService.listContractTypes(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Contract Type", description = "Create a new contract type record.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract type created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractTypeDTO>> createContractType(
            @RequestBody ContractTypeDTO contractTypeDto
    ) {
        return contractTypeService.createContractType(contractTypeDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Contract Type by ID", description = "Retrieve a specific contract type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract type retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contract type not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{contractId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractTypeDTO>> getContractType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract type", required = true)
            @PathVariable UUID contractId
    ) {
        return contractTypeService.getContractType(contractId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Contract Type", description = "Update an existing contract type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract type updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contract type not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{contractId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractTypeDTO>> updateContractType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract type", required = true)
            @PathVariable UUID contractId,
            @RequestBody ContractTypeDTO contractTypeDto
    ) {
        return contractTypeService.updateContractType(contractId, contractTypeDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Contract Type", description = "Delete a specific contract type by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contract type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Contract type not found")
    })
    @DeleteMapping(value = "/{contractId}")
    public Mono<ResponseEntity<Void>> deleteContractType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract type", required = true)
            @PathVariable UUID contractId
    ) {
        return contractTypeService.deleteContractType(contractId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}