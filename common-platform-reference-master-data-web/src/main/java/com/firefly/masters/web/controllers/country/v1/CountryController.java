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


package com.firefly.masters.web.controllers.country.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.country.v1.CountryServiceImpl;
import com.firefly.masters.interfaces.dtos.country.v1.CountryDTO;
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

@Tag(name = "Countries", description = "APIs for managing countries")
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    @Autowired
    private CountryServiceImpl service;

    @Operation(summary = "List Countries", description = "Retrieve a paginated list of countries.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of countries"
            )
    })
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<CountryDTO>>> filterCountries(
            @RequestBody FilterRequest<CountryDTO> filterRequest
    ) {
        return service.listCountries(filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create Country", description = "Create a new country.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Country created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CountryDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CountryDTO>> createCountry(
            @RequestBody CountryDTO countryDto
    ) {
        return service.createCountry(countryDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Country by ID", description = "Retrieve a specific country by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Country retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CountryDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CountryDTO>> getCountry(
            @Parameter(in = ParameterIn.PATH, description = "ID of the country", required = true)
            @PathVariable UUID countryId
    ) {
        return service.getCountry(countryId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Country", description = "Update an existing country by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Country updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CountryDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{countryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CountryDTO>> updateCountry(
            @Parameter(in = ParameterIn.PATH, description = "ID of the country", required = true)
            @PathVariable UUID countryId,
            @RequestBody CountryDTO countryDto
    ) {
        return service.updateCountry(countryId, countryDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Country", description = "Delete a specific country by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Country deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Country not found")
    })
    @DeleteMapping(value = "/{countryId}")
    public Mono<ResponseEntity<Void>> deleteCountry(
            @Parameter(in = ParameterIn.PATH, description = "ID of the country", required = true)
            @PathVariable UUID countryId
    ) {
        return service.deleteCountry(countryId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}