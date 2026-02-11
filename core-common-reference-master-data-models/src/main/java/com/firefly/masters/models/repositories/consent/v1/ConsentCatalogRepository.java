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


package com.firefly.masters.models.repositories.consent.v1;

import com.firefly.masters.models.entities.consent.v1.ConsentCatalog;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import java.util.UUID;

/**
 * Repository for managing ConsentCatalog entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
public interface ConsentCatalogRepository extends BaseRepository<ConsentCatalog, UUID> {
    
    /**
     * Find all consent catalog entries of a specific type.
     *
     * @param consentType the type of consent
     * @param pageable pagination information
     * @return a Flux of ConsentCatalog entities of the specified type
     */
    Flux<ConsentCatalog> findByConsentType(String consentType, Pageable pageable);
}