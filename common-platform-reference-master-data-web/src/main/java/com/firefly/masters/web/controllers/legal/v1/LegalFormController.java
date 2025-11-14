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


package com.firefly.masters.web.controllers.legal.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.masters.core.services.legal.v1.LegalFormService;
import com.firefly.masters.interfaces.dtos.currency.v1.CurrencyDTO;
import com.firefly.masters.interfaces.dtos.legal.v1.LegalFormDTO;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Tag(name = "Legal Forms", description = "APIs for managing legal forms")
@RestController
@RequestMapping("/api/v1/legal-forms")
public class LegalFormController {

    @Autowired
    private LegalFormService service;

    @Operation(summary = "List Legal Forms", description = "Retrieve a paginated list of legal forms.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of legal forms"
            )
    })
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<LegalFormDTO>>> listLegalForms(
            @RequestBody FilterRequest<LegalFormDTO> filterRequest
    ) {
        return service.listLegalForms(filterRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Legal Forms by Country", description = "Retrieve all legal forms for a specific country.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved legal forms for the country",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LegalFormDTO.class)
                    )
            )
    })
    @GetMapping(value = "/country/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LegalFormDTO>>> getLegalFormsByCountry(
            @Parameter(in = ParameterIn.PATH, description = "ID of the country", required = true)
            @PathVariable UUID countryId
    ) {
        return Mono.just(ResponseEntity.ok(service.getLegalFormsByCountry(countryId)));
    }

    @Operation(summary = "Create Legal Form", description = "Create a new legal form.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Legal form created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LegalFormDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LegalFormDTO>> createLegalForm(
            @RequestBody LegalFormDTO legalFormDto
    ) {
        return service.createLegalForm(legalFormDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Legal Form by ID", description = "Retrieve a specific legal form by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Legal form retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LegalFormDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Legal form not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{legalFormId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LegalFormDTO>> getLegalForm(
            @Parameter(in = ParameterIn.PATH, description = "ID of the legal form", required = true)
            @PathVariable UUID legalFormId
    ) {
        return service.getLegalForm(legalFormId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Legal Form", description = "Update an existing legal form by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Legal form updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LegalFormDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Legal form not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{legalFormId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LegalFormDTO>> updateLegalForm(
            @Parameter(in = ParameterIn.PATH, description = "ID of the legal form", required = true)
            @PathVariable UUID legalFormId,
            @RequestBody LegalFormDTO legalFormDto
    ) {
        return service.updateLegalForm(legalFormId, legalFormDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Legal Form", description = "Delete a specific legal form by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Legal form deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Legal form not found")
    })
    @DeleteMapping(value = "/{legalFormId}")
    public Mono<ResponseEntity<Void>> deleteLegalForm(
            @Parameter(in = ParameterIn.PATH, description = "ID of the legal form", required = true)
            @PathVariable UUID legalFormId
    ) {
        return service.deleteLegalForm(legalFormId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}