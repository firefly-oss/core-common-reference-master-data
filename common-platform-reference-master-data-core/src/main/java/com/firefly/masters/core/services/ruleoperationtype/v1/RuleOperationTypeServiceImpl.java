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


package com.firefly.masters.core.services.ruleoperationtype.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.ruleoperationtype.v1.RuleOperationTypeMapper;
import com.firefly.masters.interfaces.dtos.ruleoperationtype.v1.RuleOperationTypeDTO;
import com.firefly.masters.models.entities.ruleoperationtype.v1.RuleOperationType;
import com.firefly.masters.models.repositories.ruleoperationtype.v1.RuleOperationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class RuleOperationTypeServiceImpl implements RuleOperationTypeService {

    @Autowired
    private RuleOperationTypeRepository repository;

    @Autowired
    private RuleOperationTypeMapper mapper;

    @Override
    public Mono<PaginationResponse<RuleOperationTypeDTO>> listRuleOperationTypes(FilterRequest<RuleOperationTypeDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        RuleOperationType.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<RuleOperationTypeDTO> createRuleOperationType(RuleOperationTypeDTO dto) {
        RuleOperationType entity = mapper.toEntity(dto);
        entity.setDateCreated(LocalDateTime.now());
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RuleOperationTypeDTO> getRuleOperationType(UUID operationTypeId) {
        return repository.findById(operationTypeId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RuleOperationTypeDTO> updateRuleOperationType(UUID operationTypeId, RuleOperationTypeDTO dto) {
        return repository.findById(operationTypeId)
                .flatMap(found -> {
                    RuleOperationType updated = mapper.toEntity(dto);
                    updated.setOperationTypeId(operationTypeId);
                    updated.setDateUpdated(LocalDateTime.now());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteRuleOperationType(UUID operationTypeId) {
        return repository.findById(operationTypeId)
                .flatMap(repository::delete);
    }
}
