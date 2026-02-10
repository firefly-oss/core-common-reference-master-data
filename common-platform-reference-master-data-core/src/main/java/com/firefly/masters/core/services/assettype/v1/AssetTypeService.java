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


package com.firefly.masters.core.services.assettype.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.assettype.v1.AssetTypeDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing Asset Type data.
 */
public interface AssetTypeService {

    /**
     * Retrieves a paginated list of asset types based on the provided pagination request.
     *
     * @param paginationRequest the pagination request containing page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of AssetTypeDTO objects
     */
    Mono<PaginationResponse<AssetTypeDTO>> listAssetTypes(PaginationRequest paginationRequest);

    /**
     * Creates a new asset type record based on the provided AssetTypeDTO.
     *
     * @param assetTypeDto the DTO containing details of the asset type to be created
     * @return a Mono emitting the created AssetTypeDTO object
     */
    Mono<AssetTypeDTO> createAssetType(AssetTypeDTO assetTypeDto);

    /**
     * Retrieves the details of an asset type by its unique identifier.
     *
     * @param assetId the unique identifier of the asset type to retrieve
     * @return a Mono emitting the AssetTypeDTO containing details about the specified asset type, or an empty Mono if not found
     */
    Mono<AssetTypeDTO> getAssetType(UUID assetId);

    /**
     * Updates the details of an existing asset type by its unique identifier.
     *
     * @param assetId the unique identifier of the asset type to be updated
     * @param assetTypeDto the DTO containing the updated asset type details
     * @return a Mono emitting the updated AssetTypeDTO object if the update is successful
     */
    Mono<AssetTypeDTO> updateAssetType(UUID assetId, AssetTypeDTO assetTypeDto);

    /**
     * Deletes an asset type identified by its unique identifier.
     *
     * @param assetId the unique identifier of the asset type to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteAssetType(UUID assetId);
}