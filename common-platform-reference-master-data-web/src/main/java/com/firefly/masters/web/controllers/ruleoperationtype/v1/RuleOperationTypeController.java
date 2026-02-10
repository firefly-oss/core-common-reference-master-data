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


package com.firefly.masters.web.controllers.ruleoperationtype.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.ruleoperationtype.v1.RuleOperationTypeService;
import com.firefly.masters.interfaces.dtos.ruleoperationtype.v1.RuleOperationTypeDTO;
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

@Tag(name = "Rule Operation Type", description = "APIs for managing Rule Operation Type data")
@RestController
@RequestMapping("/api/v1/rule-operation-types")
public class RuleOperationTypeController {

    @Autowired
    private RuleOperationTypeService service;

    @Operation(summary = "List Rule Operation Types", description = "Retrieve a paginated list of rule operation types.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of rule operation types"
            )
    })
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<RuleOperationTypeDTO>>> filterRuleOperationTypes(
            @RequestBody FilterRequest<RuleOperationTypeDTO> filterRequest
    ) {
        return service.listRuleOperationTypes(filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create Rule Operation Type", description = "Create a new rule operation type record.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rule operation type created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RuleOperationTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RuleOperationTypeDTO>> createRuleOperationType(
            @RequestBody RuleOperationTypeDTO dto
    ) {
        return service.createRuleOperationType(dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Rule Operation Type by ID", description = "Retrieve a specific rule operation type by its ID.")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Rule operation type retrieved successfully",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = RuleOperationTypeDTO.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Rule operation type not found",
                content = @Content
        )
    })
    @GetMapping(value = "/{operationTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RuleOperationTypeDTO>> getRuleOperationType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the rule operation type", required = true)
            @PathVariable UUID operationTypeId
    ) {
        return service.getRuleOperationType(operationTypeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Rule Operation Type", description = "Update an existing rule operation type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rule operation type updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RuleOperationTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Rule operation type not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{operationTypeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RuleOperationTypeDTO>> updateRuleOperationType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the rule operation type", required = true)
            @PathVariable UUID operationTypeId,
            @RequestBody RuleOperationTypeDTO dto
    ) {
        return service.updateRuleOperationType(operationTypeId, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Rule Operation Type", description = "Delete a specific rule operation type by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rule operation type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Rule operation type not found")
    })
    @DeleteMapping(value = "/{operationTypeId}")
    public Mono<ResponseEntity<Void>> deleteRuleOperationType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the rule operation type", required = true)
            @PathVariable UUID operationTypeId
    ) {
        return service.deleteRuleOperationType(operationTypeId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
