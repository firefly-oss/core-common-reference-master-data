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


package com.firefly.masters.models.repositories.legal.v1;

import com.firefly.masters.models.entities.legal.v1.LegalForm;
import com.firefly.masters.models.repositories.BaseRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface LegalFormRepository extends BaseRepository<LegalForm, UUID> {
    /**
     * Finds all legal forms for a specific country.
     *
     * @param countryId the unique identifier of the country to retrieve legal forms for
     * @return a Flux emitting LegalForm objects for the specified country
     */
    Flux<LegalForm> findByCountryId(UUID countryId);
}
