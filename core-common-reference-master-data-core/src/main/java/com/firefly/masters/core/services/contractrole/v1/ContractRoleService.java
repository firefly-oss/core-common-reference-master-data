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
import com.firefly.masters.interfaces.dtos.contractrole.v1.ContractRoleDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing Contract Role data.
 */
public interface ContractRoleService {

    /**
     * Retrieves a paginated list of contract roles based on the provided filter request.
     *
     * @param filterRequest the filter request containing filters, page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of ContractRoleDTO objects
     */
    Mono<PaginationResponse<ContractRoleDTO>> listContractRoles(FilterRequest<ContractRoleDTO> filterRequest);

    /**
     * Creates a new contract role record based on the provided ContractRoleDTO.
     *
     * @param contractRoleDto the DTO containing details of the contract role to be created
     * @return a Mono emitting the created ContractRoleDTO object
     */
    Mono<ContractRoleDTO> createContractRole(ContractRoleDTO contractRoleDto);

    /**
     * Retrieves the details of a contract role by its unique identifier.
     *
     * @param roleId the unique identifier of the contract role to retrieve
     * @return a Mono emitting the ContractRoleDTO containing details about the specified contract role, or an empty Mono if not found
     */
    Mono<ContractRoleDTO> getContractRole(UUID roleId);

    /**
     * Updates the details of an existing contract role by its unique identifier.
     *
     * @param roleId the unique identifier of the contract role to be updated
     * @param contractRoleDto the DTO containing the updated contract role details
     * @return a Mono emitting the updated ContractRoleDTO object if the update is successful
     */
    Mono<ContractRoleDTO> updateContractRole(UUID roleId, ContractRoleDTO contractRoleDto);

    /**
     * Deletes a contract role identified by its unique identifier.
     *
     * @param roleId the unique identifier of the contract role to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteContractRole(UUID roleId);
}
