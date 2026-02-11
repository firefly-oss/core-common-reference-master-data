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


package com.firefly.masters.models.repositories.identitydocument.v1;

import com.firefly.masters.models.entities.identitydocument.v1.IdentityDocumentCategoryCatalog;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing IdentityDocumentCategoryCatalog entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface IdentityDocumentCategoryCatalogRepository extends BaseRepository<IdentityDocumentCategoryCatalog, UUID> {
    
    /**
     * Find an identity document category by its code.
     *
     * @param categoryCode the unique code of the identity document category
     * @return a Mono of IdentityDocumentCategoryCatalog
     */
    Mono<IdentityDocumentCategoryCatalog> findByCategoryCode(String categoryCode);
    
    /**
     * Find an identity document category by its name.
     *
     * @param categoryName the name of the identity document category
     * @return a Mono of IdentityDocumentCategoryCatalog
     */
    Mono<IdentityDocumentCategoryCatalog> findByCategoryName(String categoryName);
}
