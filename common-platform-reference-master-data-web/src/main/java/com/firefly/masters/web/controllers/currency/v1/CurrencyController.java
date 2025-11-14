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


package com.firefly.masters.web.controllers.currency.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.masters.core.services.currency.v1.CurrencyServiceImpl;
import com.firefly.masters.interfaces.dtos.country.v1.CountryDTO;
import com.firefly.masters.interfaces.dtos.currency.v1.CurrencyDTO;
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

@Tag(name = "Currencies", description = "APIs for managing currencies")
@RestController
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyServiceImpl service;

    @Operation(summary = "List Currencies", description = "Retrieve a paginated list of currencies.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of currencies"
            )
    })
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<CurrencyDTO>>> filterCurrencies(
            @RequestBody FilterRequest<CurrencyDTO> filterRequest
    ) {
        return service.listCurrencies(filterRequest)
                .map(ResponseEntity::ok);
    }



    @Operation(summary = "Create Currency", description = "Create a new currency.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Currency created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CurrencyDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CurrencyDTO>> createCurrency(
            @RequestBody CurrencyDTO currencyDto
    ) {
        return service.createCurrency(currencyDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Currency by ID", description = "Retrieve a specific currency by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Currency retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CurrencyDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Currency not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{currencyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CurrencyDTO>> getCurrency(
            @Parameter(in = ParameterIn.PATH, description = "ID of the currency", required = true)
            @PathVariable UUID currencyId
    ) {
        return service.getCurrency(currencyId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Currency", description = "Update an existing currency by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Currency updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CurrencyDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Currency not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{currencyId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CurrencyDTO>> updateCurrency(
            @Parameter(in = ParameterIn.PATH, description = "ID of the currency", required = true)
            @PathVariable UUID currencyId,
            @RequestBody CurrencyDTO currencyDto
    ) {
        return service.updateCurrency(currencyId, currencyDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Currency", description = "Delete a specific currency by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Currency deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Currency not found")
    })
    @DeleteMapping(value = "/{currencyId}")
    public Mono<ResponseEntity<Void>> deleteCurrency(
            @Parameter(in = ParameterIn.PATH, description = "ID of the currency", required = true)
            @PathVariable UUID currencyId
    ) {
        return service.deleteCurrency(currencyId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

