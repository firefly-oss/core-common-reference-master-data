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
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.assettype.v1.AssetTypeMapper;
import com.firefly.masters.interfaces.dtos.assettype.v1.AssetTypeDTO;
import com.firefly.masters.models.entities.assettype.v1.AssetType;
import com.firefly.masters.models.repositories.assettype.v1.AssetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class AssetTypeServiceImpl implements AssetTypeService {

    @Autowired
    private AssetTypeRepository repository;

    @Autowired
    private AssetTypeMapper mapper;

    @Override
    public Mono<PaginationResponse<AssetTypeDTO>> listAssetTypes(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<AssetTypeDTO> createAssetType(AssetTypeDTO assetTypeDto) {
        AssetType entity = mapper.toEntity(assetTypeDto);
        entity.setDateCreated(LocalDateTime.now());
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AssetTypeDTO> getAssetType(UUID assetId) {
        return repository.findById(assetId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AssetTypeDTO> updateAssetType(UUID assetId, AssetTypeDTO assetTypeDto) {
        return repository.findById(assetId)
                .flatMap(assetType -> {
                    AssetType updatedAssetType = mapper.toEntity(assetTypeDto);
                    updatedAssetType.setAssetId(assetId); // Preserve actual id
                    updatedAssetType.setDateUpdated(LocalDateTime.now()); // Update the field
                    return repository.save(updatedAssetType);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteAssetType(UUID assetId) {
        return repository.findById(assetId)
                .flatMap(repository::delete);
    }
}