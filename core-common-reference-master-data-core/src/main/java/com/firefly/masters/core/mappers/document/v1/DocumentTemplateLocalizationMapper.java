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


package com.firefly.masters.core.mappers.document.v1;

import com.firefly.masters.interfaces.dtos.document.v1.DocumentTemplateLocalizationDTO;
import com.firefly.masters.models.entities.document.v1.DocumentTemplateLocalization;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between DocumentTemplateLocalization entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface DocumentTemplateLocalizationMapper {

    /**
     * Convert a DocumentTemplateLocalization entity to a DTO.
     *
     * @param entity the entity to convert
     * @return the converted DTO
     */
    DocumentTemplateLocalizationDTO toDTO(DocumentTemplateLocalization entity);

    /**
     * Convert a DocumentTemplateLocalizationDTO to an entity.
     *
     * @param dto the DTO to convert
     * @return the converted entity
     */
    DocumentTemplateLocalization toEntity(DocumentTemplateLocalizationDTO dto);
}
