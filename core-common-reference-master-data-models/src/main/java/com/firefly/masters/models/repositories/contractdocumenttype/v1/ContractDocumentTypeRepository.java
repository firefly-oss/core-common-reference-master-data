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


package com.firefly.masters.models.repositories.contractdocumenttype.v1;

import com.firefly.masters.models.entities.contractdocumenttype.v1.ContractDocumentType;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing ContractDocumentType entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface ContractDocumentTypeRepository extends BaseRepository<ContractDocumentType, UUID> {

    /**
     * Find a contract document type by its code.
     *
     * @param documentCode the unique code of the contract document type
     * @return a Mono of ContractDocumentType
     */
    Mono<ContractDocumentType> findByDocumentCode(String documentCode);

    /**
     * Find a contract document type by its name.
     *
     * @param name the name of the contract document type
     * @return a Mono of ContractDocumentType
     */
    Mono<ContractDocumentType> findByName(String name);

    /**
     * Check if a contract document type exists by its code.
     *
     * @param documentCode the unique code of the contract document type
     * @return a Mono of Boolean indicating existence
     */
    @Query("SELECT COUNT(*) > 0 FROM contract_document_type WHERE document_code = :documentCode")
    Mono<Boolean> existsByDocumentCode(String documentCode);
}
