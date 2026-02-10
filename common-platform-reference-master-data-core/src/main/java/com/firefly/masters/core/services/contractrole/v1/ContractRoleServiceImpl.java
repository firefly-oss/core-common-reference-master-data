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
import com.firefly.masters.core.mappers.contractrole.v1.ContractRoleMapper;
import com.firefly.masters.interfaces.dtos.contractrole.v1.ContractRoleDTO;
import com.firefly.masters.models.entities.contractrole.v1.ContractRole;
import com.firefly.masters.models.repositories.contractrole.v1.ContractRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class ContractRoleServiceImpl implements ContractRoleService {

    @Autowired
    private ContractRoleRepository repository;

    @Autowired
    private ContractRoleMapper mapper;

    @Override
    public Mono<PaginationResponse<ContractRoleDTO>> listContractRoles(FilterRequest<ContractRoleDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        ContractRole.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ContractRoleDTO> createContractRole(ContractRoleDTO contractRoleDto) {
        ContractRole entity = mapper.toEntity(contractRoleDto);
        entity.setDateCreated(LocalDateTime.now());
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractRoleDTO> getContractRole(UUID roleId) {
        return repository.findById(roleId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractRoleDTO> updateContractRole(UUID roleId, ContractRoleDTO contractRoleDto) {
        return repository.findById(roleId)
                .flatMap(role -> {
                    ContractRole updated = mapper.toEntity(contractRoleDto);
                    updated.setRoleId(roleId);
                    updated.setDateUpdated(LocalDateTime.now());
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteContractRole(UUID roleId) {
        return repository.findById(roleId)
                .flatMap(repository::delete);
    }
}
