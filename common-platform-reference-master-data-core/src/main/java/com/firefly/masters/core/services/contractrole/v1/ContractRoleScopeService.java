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
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.contractrole.v1.ContractRoleScopeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing Contract Role Scope data.
 */
public interface ContractRoleScopeService {

    /**
     * Retrieves a paginated list of contract role scopes based on the provided filter request.
     *
     * @param filterRequest the filter request containing filters, page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of ContractRoleScopeDTO objects
     */
    Mono<PaginationResponse<ContractRoleScopeDTO>> listContractRoleScopes(FilterRequest<ContractRoleScopeDTO> filterRequest);

    /**
     * Creates a new contract role scope record based on the provided ContractRoleScopeDTO.
     *
     * @param contractRoleScopeDto the DTO containing details of the contract role scope to be created
     * @return a Mono emitting the created ContractRoleScopeDTO object
     */
    Mono<ContractRoleScopeDTO> createContractRoleScope(ContractRoleScopeDTO contractRoleScopeDto);

    /**
     * Retrieves the details of a contract role scope by its unique identifier.
     *
     * @param scopeId the unique identifier of the contract role scope to retrieve
     * @return a Mono emitting the ContractRoleScopeDTO containing details about the specified contract role scope, or an empty Mono if not found
     */
    Mono<ContractRoleScopeDTO> getContractRoleScope(UUID scopeId);

    /**
     * Updates the details of an existing contract role scope by its unique identifier.
     *
     * @param scopeId the unique identifier of the contract role scope to be updated
     * @param contractRoleScopeDto the DTO containing the updated contract role scope details
     * @return a Mono emitting the updated ContractRoleScopeDTO object if the update is successful
     */
    Mono<ContractRoleScopeDTO> updateContractRoleScope(UUID scopeId, ContractRoleScopeDTO contractRoleScopeDto);

    /**
     * Deletes a contract role scope identified by its unique identifier.
     *
     * @param scopeId the unique identifier of the contract role scope to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteContractRoleScope(UUID scopeId);

    /**
     * Retrieves all scopes for a specific contract role.
     *
     * @param roleId the unique identifier of the contract role
     * @return a Flux emitting ContractRoleScopeDTO objects for the specified role
     */
    Flux<ContractRoleScopeDTO> getScopesByRoleId(UUID roleId);

    /**
     * Retrieves all active scopes for a specific contract role.
     *
     * @param roleId the unique identifier of the contract role
     * @return a Flux emitting active ContractRoleScopeDTO objects for the specified role
     */
    Flux<ContractRoleScopeDTO> getActiveScopesByRoleId(UUID roleId);
}
