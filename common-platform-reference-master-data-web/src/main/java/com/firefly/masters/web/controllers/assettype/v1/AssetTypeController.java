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


package com.firefly.masters.web.controllers.assettype.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.assettype.v1.AssetTypeService;
import com.firefly.masters.interfaces.dtos.assettype.v1.AssetTypeDTO;
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

@Tag(name = "Asset Type", description = "APIs for managing Asset Type data")
@RestController
@RequestMapping("/api/v1/asset-types")
public class AssetTypeController {

    @Autowired
    private AssetTypeService assetTypeService;

    @Operation(summary = "List Asset Types", description = "Retrieve a paginated list of asset types.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of asset types",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<AssetTypeDTO>>> listAssetTypes(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return assetTypeService.listAssetTypes(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Asset Type", description = "Create a new asset type record.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Asset type created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AssetTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AssetTypeDTO>> createAssetType(
            @RequestBody AssetTypeDTO assetTypeDto
    ) {
        return assetTypeService.createAssetType(assetTypeDto)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Asset Type by ID", description = "Retrieve a specific asset type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Asset type retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AssetTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Asset type not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{assetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AssetTypeDTO>> getAssetType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the asset type", required = true)
            @PathVariable UUID assetId
    ) {
        return assetTypeService.getAssetType(assetId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Asset Type", description = "Update an existing asset type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Asset type updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AssetTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Asset type not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{assetId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AssetTypeDTO>> updateAssetType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the asset type", required = true)
            @PathVariable UUID assetId,
            @RequestBody AssetTypeDTO assetTypeDto
    ) {
        return assetTypeService.updateAssetType(assetId, assetTypeDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Asset Type", description = "Delete a specific asset type by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Asset type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Asset type not found")
    })
    @DeleteMapping(value = "/{assetId}")
    public Mono<ResponseEntity<Void>> deleteAssetType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the asset type", required = true)
            @PathVariable UUID assetId
    ) {
        return assetTypeService.deleteAssetType(assetId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}