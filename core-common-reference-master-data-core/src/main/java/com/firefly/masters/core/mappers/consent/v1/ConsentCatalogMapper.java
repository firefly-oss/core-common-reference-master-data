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


package com.firefly.masters.core.mappers.consent.v1;

import com.firefly.masters.interfaces.dtos.consent.v1.ConsentCatalogDTO;
import com.firefly.masters.models.entities.consent.v1.ConsentCatalog;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between ConsentCatalog entities and DTOs.
 * Uses MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring")
public interface ConsentCatalogMapper {
    /**
     * Converts a ConsentCatalog entity to a ConsentCatalogDTO.
     *
     * @param entity the ConsentCatalog entity to convert
     * @return the corresponding ConsentCatalogDTO
     */
    ConsentCatalogDTO toDTO(ConsentCatalog entity);
    
    /**
     * Converts a ConsentCatalogDTO to a ConsentCatalog entity.
     *
     * @param dto the ConsentCatalogDTO to convert
     * @return the corresponding ConsentCatalog entity
     */
    ConsentCatalog toEntity(ConsentCatalogDTO dto);
}