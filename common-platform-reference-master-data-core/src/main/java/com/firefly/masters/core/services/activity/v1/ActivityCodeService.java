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


package com.firefly.masters.core.services.activity.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.activity.v1.ActivityCodeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ActivityCodeService {
    /**
     * Retrieves a paginated list of activity codes based on the provided pagination request.
     *
     * @param paginationRequest the pagination request containing page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of ActivityCodeDTO objects
     */
    Mono<PaginationResponse<ActivityCodeDTO>> listActivityCodes(PaginationRequest paginationRequest);
    
    /**
     * Retrieves all activity codes for a specific country.
     *
     * @param countryId the unique identifier of the country to retrieve activity codes for
     * @return a Flux emitting ActivityCodeDTO objects for the specified country
     */
    Flux<ActivityCodeDTO> getActivityCodesByCountry(UUID countryId);
    
    /**
     * Retrieves all child activity codes for a specific parent activity code.
     *
     * @param parentCodeId the unique identifier of the parent activity code
     * @return a Flux emitting ActivityCodeDTO objects that are children of the specified parent
     */
    Flux<ActivityCodeDTO> getChildActivityCodes(UUID parentCodeId);
    
    /**
     * Creates a new activity code based on the provided ActivityCodeDTO.
     *
     * @param activityCodeDto the activity code data transfer object containing details of the activity code to be created
     * @return a Mono emitting the created ActivityCodeDTO object
     */
    Mono<ActivityCodeDTO> createActivityCode(ActivityCodeDTO activityCodeDto);
    
    /**
     * Retrieves the details of an activity code by its unique identifier.
     *
     * @param activityCodeId the unique identifier of the activity code to retrieve
     * @return a Mono emitting the ActivityCodeDTO containing details about the specified activity code, or an empty Mono if not found
     */
    Mono<ActivityCodeDTO> getActivityCode(UUID activityCodeId);
    
    /**
     * Updates the details of an existing activity code by its unique identifier.
     *
     * @param activityCodeId the unique identifier of the activity code to be updated
     * @param activityCodeDto the data transfer object containing the updated activity code details
     * @return a Mono emitting the updated ActivityCodeDTO object if the update is successful
     */
    Mono<ActivityCodeDTO> updateActivityCode(UUID activityCodeId, ActivityCodeDTO activityCodeDto);
    
    /**
     * Deletes an activity code identified by its unique identifier.
     *
     * @param activityCodeId the unique identifier of the activity code to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteActivityCode(UUID activityCodeId);
}