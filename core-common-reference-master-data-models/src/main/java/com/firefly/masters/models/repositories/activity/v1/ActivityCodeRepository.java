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


package com.firefly.masters.models.repositories.activity.v1;

import com.firefly.masters.models.entities.activity.v1.ActivityCode;
import com.firefly.masters.models.repositories.BaseRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface ActivityCodeRepository extends BaseRepository<ActivityCode, UUID> {
    /**
     * Finds all activity codes for a specific country.
     *
     * @param countryId the unique identifier of the country to retrieve activity codes for
     * @return a Flux emitting ActivityCode objects for the specified country
     */
    Flux<ActivityCode> findByCountryId(UUID countryId);

    /**
     * Finds all child activity codes for a specific parent activity code.
     *
     * @param parentCodeId the unique identifier of the parent activity code
     * @return a Flux emitting ActivityCode objects that are children of the specified parent
     */
    Flux<ActivityCode> findByParentCodeId(UUID parentCodeId);
}
