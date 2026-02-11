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


package com.firefly.masters.web.controllers.lookup.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.lookup.v1.LookupDomainService;
import com.firefly.masters.interfaces.dtos.lookup.v1.LookupDomainDTO;
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

@Tag(name = "Lookup Domains", description = "APIs for managing lookup domains")
@RestController
@RequestMapping("/api/v1/lookup/domains")
public class LookupDomainController {

    @Autowired
    private LookupDomainService service;

    @Operation(summary = "List Lookup Domains", description = "Retrieve a paginated list of lookup domains.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of lookup domains",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<LookupDomainDTO>>> listDomains(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listDomains(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Lookup Domain", description = "Create a new lookup domain.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lookup domain created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LookupDomainDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LookupDomainDTO>> createDomain(
            @RequestBody LookupDomainDTO domainDto
    ) {
        return service.createDomain(domainDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Lookup Domain by ID", description = "Retrieve a specific lookup domain by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lookup domain retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LookupDomainDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Lookup domain not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{domainId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LookupDomainDTO>> getDomain(
            @Parameter(in = ParameterIn.PATH, description = "ID of the lookup domain", required = true)
            @PathVariable UUID domainId
    ) {
        return service.getDomain(domainId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Lookup Domain", description = "Update an existing lookup domain by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lookup domain updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LookupDomainDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Lookup domain not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{domainId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LookupDomainDTO>> updateDomain(
            @Parameter(in = ParameterIn.PATH, description = "ID of the lookup domain", required = true)
            @PathVariable UUID domainId,
            @RequestBody LookupDomainDTO domainDto
    ) {
        return service.updateDomain(domainId, domainDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Lookup Domain", description = "Delete a specific lookup domain by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Lookup domain deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Lookup domain not found")
    })
    @DeleteMapping(value = "/{domainId}")
    public Mono<ResponseEntity<Void>> deleteDomain(
            @Parameter(in = ParameterIn.PATH, description = "ID of the lookup domain", required = true)
            @PathVariable UUID domainId
    ) {
        return service.deleteDomain(domainId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}