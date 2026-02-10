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
import com.firefly.masters.core.services.transaction.v1.TransactionCategoryCatalogService;
import com.firefly.masters.interfaces.dtos.transaction.v1.TransactionCategoryCatalogDTO;
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
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing transaction category catalog operations.
 */
@RestController
@RequestMapping("/api/v1/transaction-categories")
@Tag(name = "Transaction Category Catalog", description = "API for managing transaction categories")
public class TransactionCategoryCatalogController {

    @Autowired
    private TransactionCategoryCatalogService service;

    @Operation(summary = "List All Transaction Categories", description = "Retrieve a paginated list of all transaction categories.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of transaction categories",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<TransactionCategoryCatalogDTO>>> listTransactionCategories(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listTransactionCategories(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Root Transaction Categories", description = "Retrieve a paginated list of root transaction categories (categories without a parent).")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of root transaction categories",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/root", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<TransactionCategoryCatalogDTO>>> listRootTransactionCategories(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listRootTransactionCategories(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Child Transaction Categories", description = "Retrieve a paginated list of child transaction categories for a specific parent category.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of child transaction categories",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/parent/{parentCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<TransactionCategoryCatalogDTO>>> listChildTransactionCategories(
            @Parameter(in = ParameterIn.PATH, description = "ID of the parent category", required = true)
            @PathVariable UUID parentCategoryId,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listChildTransactionCategories(parentCategoryId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Transaction Category by ID", description = "Retrieve a transaction category by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the transaction category",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionCategoryCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction category not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TransactionCategoryCatalogDTO>> getTransactionCategory(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction category", required = true)
            @PathVariable UUID categoryId
    ) {
        return service.getTransactionCategory(categoryId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Transaction Category by Code", description = "Retrieve a transaction category by its code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the transaction category",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionCategoryCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction category not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/code/{categoryCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TransactionCategoryCatalogDTO>> getTransactionCategoryByCode(
            @Parameter(in = ParameterIn.PATH, description = "Code of the transaction category", required = true)
            @PathVariable String categoryCode
    ) {
        return service.getTransactionCategoryByCode(categoryCode)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create Transaction Category", description = "Create a new transaction category.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction category created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionCategoryCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TransactionCategoryCatalogDTO>> createTransactionCategory(
            @RequestBody TransactionCategoryCatalogDTO dto
    ) {
        return service.createTransactionCategory(dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Update Transaction Category", description = "Update an existing transaction category.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction category updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionCategoryCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction category not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PutMapping(value = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TransactionCategoryCatalogDTO>> updateTransactionCategory(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction category to update", required = true)
            @PathVariable UUID categoryId,
            @RequestBody TransactionCategoryCatalogDTO dto
    ) {
        return service.updateTransactionCategory(categoryId, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Transaction Category", description = "Delete a transaction category.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Transaction category deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction category not found",
                    content = @Content
            )
    })
    @DeleteMapping(value = "/{categoryId}")
    public Mono<ResponseEntity<Void>> deleteTransactionCategory(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction category to delete", required = true)
            @PathVariable UUID categoryId
    ) {
        return service.deleteTransactionCategory(categoryId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
