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
import com.firefly.masters.core.services.contractrole.v1.ContractRoleService;
import com.firefly.masters.interfaces.dtos.contractrole.v1.ContractRoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Tag(name = "Contract Role", description = "APIs for managing Contract Role data")
@RestController
@RequestMapping("/api/v1/contract-roles")
public class ContractRoleController {

    @Autowired
    private ContractRoleService contractRoleService;

    @Operation(summary = "List Contract Roles", description = "Retrieve a paginated list of contract roles.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of contract roles"
            )
    })
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ContractRoleDTO>>> filterContractRoles(
            @RequestBody FilterRequest<ContractRoleDTO> filterRequest
    ) {
        return contractRoleService.listContractRoles(filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create Contract Role", description = "Create a new contract role record.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract role created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractRoleDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractRoleDTO>> createContractRole(
            @RequestBody ContractRoleDTO contractRoleDto
    ) {
        return contractRoleService.createContractRole(contractRoleDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Contract Role by ID", description = "Retrieve a specific contract role by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract role retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractRoleDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contract role not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractRoleDTO>> getContractRole(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract role", required = true)
            @PathVariable UUID roleId
    ) {
        return contractRoleService.getContractRole(roleId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Contract Role", description = "Update an existing contract role by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract role updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContractRoleDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contract role not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{roleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ContractRoleDTO>> updateContractRole(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract role", required = true)
            @PathVariable UUID roleId,
            @RequestBody ContractRoleDTO contractRoleDto
    ) {
        return contractRoleService.updateContractRole(roleId, contractRoleDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Contract Role", description = "Delete a specific contract role by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contract role deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Contract role not found")
    })
    @DeleteMapping(value = "/{roleId}")
    public Mono<ResponseEntity<Void>> deleteContractRole(
            @Parameter(in = ParameterIn.PATH, description = "ID of the contract role", required = true)
            @PathVariable UUID roleId
    ) {
        return contractRoleService.deleteContractRole(roleId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
