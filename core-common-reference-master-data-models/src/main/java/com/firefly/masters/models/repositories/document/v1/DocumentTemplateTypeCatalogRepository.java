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

import com.firefly.masters.models.entities.document.v1.DocumentTemplateTypeCatalog;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing DocumentTemplateTypeCatalog entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface DocumentTemplateTypeCatalogRepository extends BaseRepository<DocumentTemplateTypeCatalog, UUID> {
    
    /**
     * Find a document template type by its code.
     *
     * @param typeCode the unique code of the document template type
     * @return a Mono of DocumentTemplateTypeCatalog
     */
    Mono<DocumentTemplateTypeCatalog> findByTypeCode(String typeCode);
    
    /**
     * Find a document template type by its name.
     *
     * @param typeName the name of the document template type
     * @return a Mono of DocumentTemplateTypeCatalog
     */
    Mono<DocumentTemplateTypeCatalog> findByTypeName(String typeName);
    
    /**
     * Count document template types by status.
     *
     * @param status the status to count
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM document_template_type_catalog WHERE status = :status")
    Mono<Long> countByStatus(String status);
}
