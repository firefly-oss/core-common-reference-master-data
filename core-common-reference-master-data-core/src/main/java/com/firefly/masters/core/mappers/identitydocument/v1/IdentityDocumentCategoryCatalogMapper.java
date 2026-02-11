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


package com.firefly.masters.core.mappers.identitydocument.v1;

import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCategoryCatalogDTO;
import com.firefly.masters.models.entities.identitydocument.v1.IdentityDocumentCategoryCatalog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between IdentityDocumentCategoryCatalog entities and DTOs.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IdentityDocumentCategoryCatalogMapper {

    /**
     * Convert an IdentityDocumentCategoryCatalog entity to a DTO.
     *
     * @param entity the entity to convert
     * @return the converted DTO
     */
    IdentityDocumentCategoryCatalogDTO toDTO(IdentityDocumentCategoryCatalog entity);

    /**
     * Convert an IdentityDocumentCategoryCatalogDTO to an entity.
     *
     * @param dto the DTO to convert
     * @return the converted entity
     */
    IdentityDocumentCategoryCatalog toEntity(IdentityDocumentCategoryCatalogDTO dto);
}
