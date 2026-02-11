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


package com.firefly.masters.models.repositories.contractrole.v1;

import com.firefly.masters.models.entities.contractrole.v1.ContractRoleScope;
import com.firefly.masters.models.repositories.BaseRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface ContractRoleScopeRepository extends BaseRepository<ContractRoleScope, UUID> {
    
    /**
     * Find all scopes for a specific contract role.
     *
     * @param roleId the role ID to search for
     * @return a Flux of ContractRoleScope entities
     */
    Flux<ContractRoleScope> findByRoleId(UUID roleId);
    
    /**
     * Find all active scopes for a specific contract role.
     *
     * @param roleId the role ID to search for
     * @param isActive the active status to filter by
     * @return a Flux of ContractRoleScope entities
     */
    Flux<ContractRoleScope> findByRoleIdAndIsActive(UUID roleId, Boolean isActive);
}
