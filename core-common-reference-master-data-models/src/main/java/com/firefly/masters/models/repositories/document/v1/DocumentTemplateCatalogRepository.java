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


package com.firefly.masters.models.repositories.document.v1;

import com.firefly.masters.models.entities.document.v1.DocumentTemplateCatalog;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing DocumentTemplateCatalog entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface DocumentTemplateCatalogRepository extends BaseRepository<DocumentTemplateCatalog, UUID> {

    /**
     * Find a document template by its code.
     *
     * @param templateCode the unique code of the template
     * @return a Mono of DocumentTemplateCatalog
     */
    Mono<DocumentTemplateCatalog> findByTemplateCode(String templateCode);

    /**
     * Find all document templates of a specific category.
     *
     * @param category the category of templates
     * @param pageable pagination information
     * @return a Flux of DocumentTemplateCatalog entities of the specified category
     */
    Flux<DocumentTemplateCatalog> findByCategory(String category, Pageable pageable);

    /**
     * Count document templates of a specific category.
     *
     * @param category the category of templates
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM document_template_catalog WHERE category = :category")
    Mono<Long> countByCategory(String category);

    /**
     * Find all document templates of a specific template type.
     *
     * @param typeId the ID of the template type
     * @param pageable pagination information
     * @return a Flux of DocumentTemplateCatalog entities of the specified template type
     */
    Flux<DocumentTemplateCatalog> findByTypeId(UUID typeId, Pageable pageable);

    /**
     * Count document templates of a specific template type.
     *
     * @param typeId the ID of the template type
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM document_template_catalog WHERE type_id = :typeId")
    Mono<Long> countByTypeId(UUID typeId);
}
