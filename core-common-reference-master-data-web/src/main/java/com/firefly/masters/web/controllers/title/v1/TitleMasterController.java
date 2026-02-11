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


package com.firefly.masters.web.controllers.title.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.title.v1.TitleMasterService;
import com.firefly.masters.interfaces.dtos.title.v1.TitleMasterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Tag(name = "Title Master", description = "APIs for managing Title Master data")
@RestController
@RequestMapping("/api/v1/titles")
public class TitleMasterController {

    @Autowired
    private TitleMasterService titleMasterService;

    @Operation(summary = "List Titles", description = "Retrieve a paginated list of titles.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of titles",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<TitleMasterDTO>>> listTitles(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return titleMasterService.listTitles(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Title", description = "Create a new title master record.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Title created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TitleMasterDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TitleMasterDTO>> createTitle(
            @RequestBody TitleMasterDTO titleDto
    ) {
        return titleMasterService.createTitle(titleDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Title by ID", description = "Retrieve a specific title by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Title retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TitleMasterDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Title not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{titleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TitleMasterDTO>> getTitle(
            @Parameter(in = ParameterIn.PATH, description = "ID of the title", required = true)
            @PathVariable UUID titleId
    ) {
        return titleMasterService.getTitle(titleId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Title", description = "Update an existing title by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Title updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TitleMasterDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Title not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{titleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TitleMasterDTO>> updateTitle(
            @Parameter(in = ParameterIn.PATH, description = "ID of the title", required = true)
            @PathVariable UUID titleId,
            @RequestBody TitleMasterDTO titleDto
    ) {
        return titleMasterService.updateTitle(titleId, titleDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Title", description = "Delete a specific title by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Title deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Title not found")
    })
    @DeleteMapping(value = "/{titleId}")
    public Mono<ResponseEntity<Void>> deleteTitle(
            @Parameter(in = ParameterIn.PATH, description = "ID of the title", required = true)
            @PathVariable UUID titleId
    ) {
        return titleMasterService.deleteTitle(titleId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}