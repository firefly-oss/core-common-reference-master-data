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


package com.firefly.masters.web.controllers.transaction.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.transaction.v1.TransactionCategoryLocalizationService;
import com.firefly.masters.interfaces.dtos.transaction.v1.TransactionCategoryLocalizationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing transaction category localization operations.
 */
@RestController
@RequestMapping("/api/v1/transaction-category-localizations")
@Tag(name = "Transaction Category Localization", description = "API for managing transaction category localizations")
public class TransactionCategoryLocalizationController {

    @Autowired
    private TransactionCategoryLocalizationService service;

    @Operation(summary = "List Localizations by Category", description = "Retrieve all localizations for a specific transaction category.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved localizations for the specified category",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionCategoryLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No localizations found for the specified category",
                    content = @Content
            )
    })
    @GetMapping(value = "/category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<TransactionCategoryLocalizationDTO>>> getLocalizationsByCategoryId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction category", required = true)
            @PathVariable UUID categoryId
    ) {
        return Mono.just(ResponseEntity.ok(service.getLocalizationsByCategoryId(categoryId)));
    }

    @Operation(summary = "List Localizations by Category with Pagination", description = "Retrieve a paginated list of localizations for a specific transaction category.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved paginated localizations for the specified category",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/category/{categoryId}/paginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<TransactionCategoryLocalizationDTO>>> listLocalizationsByCategoryId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction category", required = true)
            @PathVariable UUID categoryId,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listLocalizationsByCategoryId(categoryId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Localization by ID", description = "Retrieve a transaction category localization by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the transaction category localization",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionCategoryLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction category localization not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{localizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TransactionCategoryLocalizationDTO>> getTransactionCategoryLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction category localization", required = true)
            @PathVariable UUID localizationId
    ) {
        return service.getTransactionCategoryLocalization(localizationId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Localization by Category and Locale", description = "Retrieve a transaction category localization by category ID and locale ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the transaction category localization",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionCategoryLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction category localization not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/category/{categoryId}/locale/{localeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TransactionCategoryLocalizationDTO>> getTransactionCategoryLocalizationByCategoryAndLocale(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction category", required = true)
            @PathVariable UUID categoryId,
            @Parameter(in = ParameterIn.PATH, description = "ID of the locale", required = true)
            @PathVariable UUID localeId
    ) {
        return service.getTransactionCategoryLocalizationByCategoryAndLocale(categoryId, localeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create Transaction Category Localization", description = "Create a new transaction category localization.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction category localization created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionCategoryLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TransactionCategoryLocalizationDTO>> createTransactionCategoryLocalization(
            @RequestBody TransactionCategoryLocalizationDTO dto
    ) {
        return service.createTransactionCategoryLocalization(dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Update Transaction Category Localization", description = "Update an existing transaction category localization.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction category localization updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionCategoryLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction category localization not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PutMapping(value = "/{localizationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TransactionCategoryLocalizationDTO>> updateTransactionCategoryLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction category localization to update", required = true)
            @PathVariable UUID localizationId,
            @RequestBody TransactionCategoryLocalizationDTO dto
    ) {
        return service.updateTransactionCategoryLocalization(localizationId, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Transaction Category Localization", description = "Delete a transaction category localization.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Transaction category localization deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction category localization not found",
                    content = @Content
            )
    })
    @DeleteMapping(value = "/{localizationId}")
    public Mono<ResponseEntity<Void>> deleteTransactionCategoryLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction category localization to delete", required = true)
            @PathVariable UUID localizationId
    ) {
        return service.deleteTransactionCategoryLocalization(localizationId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
