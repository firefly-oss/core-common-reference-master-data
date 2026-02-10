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


package com.firefly.masters.web.controllers.contractrole.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.contractrole.v1.ContractRoleScopeService;
import com.firefly.masters.interfaces.dtos.contractrole.v1.ContractRoleScopeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contract-role-scopes")
@Tag(name = "Contract Role Scope", description = "API for managing contract role scopes and permissions")
public class ContractRoleScopeController {

    @Autowired
    private ContractRoleScopeService contractRoleScopeService;

    @Operation(summary = "Filter Contract Role Scopes", description = "Retrieve a paginated list of contract role scopes based on filter criteria.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract role scopes retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid filter request",
                    content = @Content
            )
    })
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ContractRoleScopeDTO>>> filterContractRoleScopes(
            @RequestBody FilterRequest<ContractRoleScopeDTO> filterRequest
    ) {
        return contractRoleScopeService.listContractRoleScopes(filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create Contract Role Scope", description = "Create a new contract role scope record.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract role scope created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractRoleScopeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractRoleScopeDTO>> createContractRoleScope(
            @RequestBody ContractRoleScopeDTO contractRoleScopeDto
    ) {
        return contractRoleScopeService.createContractRoleScope(contractRoleScopeDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Contract Role Scope", description = "Retrieve a specific contract role scope by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract role scope retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractRoleScopeDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Contract role scope not found")
    })
    @GetMapping(value = "/{scopeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractRoleScopeDTO>> getContractRoleScope(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract role scope", required = true)
            @PathVariable UUID scopeId
    ) {
        return contractRoleScopeService.getContractRoleScope(scopeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Contract Role Scope", description = "Update an existing contract role scope by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract role scope updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractRoleScopeDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Contract role scope not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping(value = "/{scopeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractRoleScopeDTO>> updateContractRoleScope(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract role scope", required = true)
            @PathVariable UUID scopeId,
            @RequestBody ContractRoleScopeDTO contractRoleScopeDto
    ) {
        return contractRoleScopeService.updateContractRoleScope(scopeId, contractRoleScopeDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Contract Role Scope", description = "Delete a specific contract role scope by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contract role scope deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Contract role scope not found")
    })
    @DeleteMapping(value = "/{scopeId}")
    public Mono<ResponseEntity<Void>> deleteContractRoleScope(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract role scope", required = true)
            @PathVariable UUID scopeId
    ) {
        return contractRoleScopeService.deleteContractRoleScope(scopeId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @Operation(summary = "Get Scopes by Role ID", description = "Retrieve all scopes for a specific contract role.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract role scopes retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractRoleScopeDTO.class)
                    )
            )
    })
    @GetMapping(value = "/role/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ContractRoleScopeDTO> getScopesByRoleId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract role", required = true)
            @PathVariable UUID roleId
    ) {
        return contractRoleScopeService.getScopesByRoleId(roleId);
    }

    @Operation(summary = "Get Active Scopes by Role ID", description = "Retrieve all active scopes for a specific contract role.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Active contract role scopes retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ContractRoleScopeDTO.class))
                    )
            )
    })
    @GetMapping(value = "/role/{roleId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ContractRoleScopeDTO> getActiveScopesByRoleId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract role", required = true)
            @PathVariable UUID roleId
    ) {
        return contractRoleScopeService.getActiveScopesByRoleId(roleId);
    }
}
