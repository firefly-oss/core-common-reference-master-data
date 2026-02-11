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

import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCatalogDTO;
import com.firefly.masters.interfaces.dtos.identitydocument.v1.IdentityDocumentCategoryCatalogDTO;
import com.firefly.masters.models.entities.identitydocument.v1.IdentityDocumentCatalog;
import com.firefly.masters.models.repositories.identitydocument.v1.IdentityDocumentCategoryCatalogRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

/**
 * Mapper for converting between IdentityDocumentCatalog entities and DTOs.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {IdentityDocumentCategoryCatalogMapper.class})
public abstract class IdentityDocumentCatalogMapper {

    @Autowired
    private IdentityDocumentCategoryCatalogRepository identityDocumentCategoryCatalogRepository;

    @Autowired
    private IdentityDocumentCategoryCatalogMapper identityDocumentCategoryCatalogMapper;

    /**
     * Convert an IdentityDocumentCatalog entity to a DTO.
     *
     * @param entity the entity to convert
     * @return the converted DTO
     */
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "categoryIdToCategory")
    public abstract IdentityDocumentCatalogDTO toDTO(IdentityDocumentCatalog entity);

    /**
     * Convert an IdentityDocumentCatalogDTO to an entity.
     *
     * @param dto the DTO to convert
     * @return the converted entity
     */
    public abstract IdentityDocumentCatalog toEntity(IdentityDocumentCatalogDTO dto);

    /**
     * Convert a category ID to an IdentityDocumentCategoryCatalogDTO.
     *
     * @param categoryId the category ID to convert
     * @return the converted IdentityDocumentCategoryCatalogDTO
     */
    @Named("categoryIdToCategory")
    protected IdentityDocumentCategoryCatalogDTO categoryIdToCategory(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }
        return identityDocumentCategoryCatalogRepository.findById(categoryId)
                .map(identityDocumentCategoryCatalogMapper::toDTO)
                .block();
    }
}
