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


package com.firefly.masters.web.controllers.division.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.division.v1.AdministrativeDivisionService;
import com.firefly.masters.interfaces.dtos.division.v1.AdministrativeDivisionDTO;
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

@Tag(name = "Administrative Divisions", description = "APIs for managing administrative divisions")
@RestController
@RequestMapping("/api/v1/divisions")
public class AdministrativeDivisionController {

    @Autowired
    private AdministrativeDivisionService service;

    @Operation(summary = "List Administrative Divisions", description = "Retrieve a paginated list of administrative divisions.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of administrative divisions",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<AdministrativeDivisionDTO>>> listDivisions(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listDivisions(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Administrative Division", description = "Create a new administrative division.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Administrative division created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdministrativeDivisionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AdministrativeDivisionDTO>> createDivision(
            @RequestBody AdministrativeDivisionDTO divisionDto
    ) {
        return service.createDivision(divisionDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Administrative Division by ID", description = "Retrieve a specific administrative division by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Administrative division retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdministrativeDivisionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Administrative division not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{divisionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AdministrativeDivisionDTO>> getDivision(
            @Parameter(in = ParameterIn.PATH, description = "ID of the administrative division", required = true)
            @PathVariable UUID divisionId
    ) {
        return service.getDivision(divisionId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Administrative Division", description = "Update an existing administrative division by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Administrative division updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdministrativeDivisionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Administrative division not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{divisionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AdministrativeDivisionDTO>> updateDivision(
            @Parameter(in = ParameterIn.PATH, description = "ID of the administrative division", required = true)
            @PathVariable UUID divisionId,
            @RequestBody AdministrativeDivisionDTO divisionDto
    ) {
        return service.updateDivision(divisionId, divisionDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Administrative Division", description = "Delete a specific administrative division by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Administrative division deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Administrative division not found")
    })
    @DeleteMapping(value = "/{divisionId}")
    public Mono<ResponseEntity<Void>> deleteDivision(
            @Parameter(in = ParameterIn.PATH, description = "ID of the administrative division", required = true)
            @PathVariable UUID divisionId
    ) {
        return service.deleteDivision(divisionId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}