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


package com.firefly.masters.web.controllers.bank.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.bank.v1.BankInstitutionCodeServiceImpl;
import com.firefly.masters.interfaces.dtos.bank.v1.BankInstitutionCodeDTO;
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

@Tag(name = "BankInstitutionCodes", description = "APIs for managing bank or institution codes")
@RestController
@RequestMapping("/api/v1/bank-institution-codes")
public class BankInstitutionCodeController {

    @Autowired
    private BankInstitutionCodeServiceImpl service;

    @Operation(summary = "List Bank Institution Codes", description = "Retrieve a paginated list of bank or institution codes.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of bank/institution codes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<BankInstitutionCodeDTO>>> listBankInstitutionCodes(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listBankInstitutionCodes(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Bank Institution Code", description = "Create a new bank or institution code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Bank institution code created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BankInstitutionCodeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<BankInstitutionCodeDTO>> createBankInstitutionCode(
            @RequestBody BankInstitutionCodeDTO dto
    ) {
        return service.createBankInstitutionCode(dto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Bank Institution Code by ID", description = "Retrieve a specific bank or institution code by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Bank institution code retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BankInstitutionCodeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bank institution code not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<BankInstitutionCodeDTO>> getBankInstitutionCode(
            @Parameter(in = ParameterIn.PATH, description = "ID of the bank institution code", required = true)
            @PathVariable UUID id
    ) {
        return service.getBankInstitutionCode(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Bank Institution Code", description = "Update an existing bank or institution code by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Bank institution code updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BankInstitutionCodeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bank institution code not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<BankInstitutionCodeDTO>> updateBankInstitutionCode(
            @Parameter(in = ParameterIn.PATH, description = "ID of the bank institution code", required = true)
            @PathVariable UUID id,
            @RequestBody BankInstitutionCodeDTO dto
    ) {
        return service.updateBankInstitutionCode(id, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Bank Institution Code", description = "Delete a specific bank or institution code by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Bank institution code deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Bank institution code not found")
    })
    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> deleteBankInstitutionCode(
            @Parameter(in = ParameterIn.PATH, description = "ID of the bank institution code", required = true)
            @PathVariable UUID id
    ) {
        return service.deleteBankInstitutionCode(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

