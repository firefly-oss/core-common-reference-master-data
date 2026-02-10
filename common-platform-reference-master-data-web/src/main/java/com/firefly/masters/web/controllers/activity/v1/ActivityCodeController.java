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


package com.firefly.masters.web.controllers.activity.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.activity.v1.ActivityCodeService;
import com.firefly.masters.interfaces.dtos.activity.v1.ActivityCodeDTO;
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

@Tag(name = "Activity Codes", description = "APIs for managing activity codes")
@RestController
@RequestMapping("/api/v1/activity-codes")
public class ActivityCodeController {

    @Autowired
    private ActivityCodeService service;

    @Operation(summary = "List Activity Codes", description = "Retrieve a paginated list of activity codes.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of activity codes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ActivityCodeDTO>>> listActivityCodes(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listActivityCodes(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Activity Codes by Country", description = "Retrieve all activity codes for a specific country.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved activity codes for the country",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ActivityCodeDTO.class)
                    )
            )
    })
    @GetMapping(value = "/country/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<ActivityCodeDTO>>> getActivityCodesByCountry(
            @Parameter(in = ParameterIn.PATH, description = "ID of the country", required = true)
            @PathVariable UUID countryId
    ) {
        return Mono.just(ResponseEntity.ok(service.getActivityCodesByCountry(countryId)));
    }

    @Operation(summary = "Get Child Activity Codes", description = "Retrieve all child activity codes for a specific parent activity code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved child activity codes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ActivityCodeDTO.class)
                    )
            )
    })
    @GetMapping(value = "/parent/{parentCodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<ActivityCodeDTO>>> getChildActivityCodes(
            @Parameter(in = ParameterIn.PATH, description = "ID of the parent activity code", required = true)
            @PathVariable UUID parentCodeId
    ) {
        return Mono.just(ResponseEntity.ok(service.getChildActivityCodes(parentCodeId)));
    }

    @Operation(summary = "Create Activity Code", description = "Create a new activity code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Activity code created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ActivityCodeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ActivityCodeDTO>> createActivityCode(
            @RequestBody ActivityCodeDTO activityCodeDto
    ) {
        return service.createActivityCode(activityCodeDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Activity Code by ID", description = "Retrieve a specific activity code by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Activity code retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ActivityCodeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Activity code not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{activityCodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ActivityCodeDTO>> getActivityCode(
            @Parameter(in = ParameterIn.PATH, description = "ID of the activity code", required = true)
            @PathVariable UUID activityCodeId
    ) {
        return service.getActivityCode(activityCodeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Activity Code", description = "Update an existing activity code by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Activity code updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ActivityCodeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Activity code not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{activityCodeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ActivityCodeDTO>> updateActivityCode(
            @Parameter(in = ParameterIn.PATH, description = "ID of the activity code", required = true)
            @PathVariable UUID activityCodeId,
            @RequestBody ActivityCodeDTO activityCodeDto
    ) {
        return service.updateActivityCode(activityCodeId, activityCodeDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Activity Code", description = "Delete a specific activity code by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Activity code deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Activity code not found")
    })
    @DeleteMapping(value = "/{activityCodeId}")
    public Mono<ResponseEntity<Void>> deleteActivityCode(
            @Parameter(in = ParameterIn.PATH, description = "ID of the activity code", required = true)
            @PathVariable UUID activityCodeId
    ) {
        return service.deleteActivityCode(activityCodeId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}