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


package com.firefly.masters.core.services.relationships.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.relationships.v1.RelationshipTypeMasterMapper;
import com.firefly.masters.interfaces.dtos.relationships.v1.RelationshipTypeMasterDTO;
import com.firefly.masters.models.entities.relationships.v1.RelationshipTypeMaster;
import com.firefly.masters.models.repositories.relationships.v1.RelationshipTypeMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class RelationshipTypeMasterServiceImpl implements RelationshipTypeMasterService {

    @Autowired
    private RelationshipTypeMasterRepository repository;

    @Autowired
    private RelationshipTypeMasterMapper mapper;

    @Override
    public Mono<PaginationResponse<RelationshipTypeMasterDTO>> listRelationshipTypes(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<RelationshipTypeMasterDTO> createRelationshipType(RelationshipTypeMasterDTO relationshipTypeDto) {
        RelationshipTypeMaster domain = mapper.toEntity(relationshipTypeDto);
        return repository.save(domain)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RelationshipTypeMasterDTO> getRelationshipType(UUID relationshipTypeId) {
        return repository.findById(relationshipTypeId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RelationshipTypeMasterDTO> updateRelationshipType(UUID relationshipTypeId, RelationshipTypeMasterDTO relationshipTypeDto) {
        return repository.findById(relationshipTypeId)
                .flatMap(foundRelationshipTypeMaster -> {
                    RelationshipTypeMaster updatedRelationshipType = mapper.toEntity(relationshipTypeDto);
                    updatedRelationshipType.setRelationshipTypeId(relationshipTypeId); // Preserving original id
                    updatedRelationshipType.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedRelationshipType);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteRelationshipType(UUID relationshipTypeId) {
        return repository.findById(relationshipTypeId)
                .flatMap(repository::delete);
    }
}
