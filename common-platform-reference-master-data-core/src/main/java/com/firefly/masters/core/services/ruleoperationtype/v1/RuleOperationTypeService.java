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
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.ruleoperationtype.v1.RuleOperationTypeDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing Rule Operation Type data.
 */
public interface RuleOperationTypeService {

    /**
     * Retrieves a paginated list of rule operation types based on the provided filter request.
     *
     * @param filterRequest the filter request containing filters, page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of RuleOperationTypeDTO objects
     */
    Mono<PaginationResponse<RuleOperationTypeDTO>> listRuleOperationTypes(FilterRequest<RuleOperationTypeDTO> filterRequest);

    /**
     * Creates a new rule operation type record based on the provided RuleOperationTypeDTO.
     *
     * @param dto the DTO containing details of the rule operation type to be created
     * @return a Mono emitting the created RuleOperationTypeDTO object
     */
    Mono<RuleOperationTypeDTO> createRuleOperationType(RuleOperationTypeDTO dto);

    /**
     * Retrieves the details of a rule operation type by its unique identifier.
     *
     * @param operationTypeId the unique identifier of the rule operation type to retrieve
     * @return a Mono emitting the RuleOperationTypeDTO, or an empty Mono if not found
     */
    Mono<RuleOperationTypeDTO> getRuleOperationType(UUID operationTypeId);

    /**
     * Updates the details of an existing rule operation type by its unique identifier.
     *
     * @param operationTypeId the unique identifier of the rule operation type to be updated
     * @param dto the DTO containing the updated rule operation type details
     * @return a Mono emitting the updated RuleOperationTypeDTO object
     */
    Mono<RuleOperationTypeDTO> updateRuleOperationType(UUID operationTypeId, RuleOperationTypeDTO dto);

    /**
     * Deletes a rule operation type identified by its unique identifier.
     *
     * @param operationTypeId the unique identifier of the rule operation type to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteRuleOperationType(UUID operationTypeId);
}
