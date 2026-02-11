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


package com.firefly.masters.core.services.contractrole.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.mappers.contractrole.v1.ContractRoleScopeMapper;
import com.firefly.masters.interfaces.dtos.contractrole.v1.ContractRoleScopeDTO;
import com.firefly.masters.models.entities.contractrole.v1.ContractRoleScope;
import com.firefly.masters.models.repositories.contractrole.v1.ContractRoleScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class ContractRoleScopeServiceImpl implements ContractRoleScopeService {

    @Autowired
    private ContractRoleScopeRepository repository;

    @Autowired
    private ContractRoleScopeMapper mapper;

    @Override
    public Mono<PaginationResponse<ContractRoleScopeDTO>> listContractRoleScopes(FilterRequest<ContractRoleScopeDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        ContractRoleScope.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ContractRoleScopeDTO> createContractRoleScope(ContractRoleScopeDTO contractRoleScopeDto) {
        ContractRoleScope entity = mapper.toEntity(contractRoleScopeDto);
        entity.setDateCreated(LocalDateTime.now());
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractRoleScopeDTO> getContractRoleScope(UUID scopeId) {
        return repository.findById(scopeId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractRoleScopeDTO> updateContractRoleScope(UUID scopeId, ContractRoleScopeDTO contractRoleScopeDto) {
        return repository.findById(scopeId)
                .flatMap(existingEntity -> {
                    // Update fields from DTO
                    existingEntity.setRoleId(contractRoleScopeDto.getRoleId());
                    existingEntity.setScopeCode(contractRoleScopeDto.getScopeCode());
                    existingEntity.setScopeName(contractRoleScopeDto.getScopeName());
                    existingEntity.setDescription(contractRoleScopeDto.getDescription());
                    existingEntity.setActionType(contractRoleScopeDto.getActionType());
                    existingEntity.setResourceType(contractRoleScopeDto.getResourceType());
                    existingEntity.setIsActive(contractRoleScopeDto.getIsActive());
                    existingEntity.setDateUpdated(LocalDateTime.now());
                    
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteContractRoleScope(UUID scopeId) {
        return repository.deleteById(scopeId);
    }

    @Override
    public Flux<ContractRoleScopeDTO> getScopesByRoleId(UUID roleId) {
        return repository.findByRoleId(roleId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<ContractRoleScopeDTO> getActiveScopesByRoleId(UUID roleId) {
        return repository.findByRoleIdAndIsActive(roleId, true)
                .map(mapper::toDTO);
    }
}
