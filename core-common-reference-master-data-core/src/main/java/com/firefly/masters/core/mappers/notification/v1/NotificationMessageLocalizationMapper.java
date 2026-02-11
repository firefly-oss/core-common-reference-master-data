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


package com.firefly.masters.core.mappers.notification.v1;

import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageLocalizationDTO;
import com.firefly.masters.models.entities.notification.v1.NotificationMessageLocalization;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between NotificationMessageLocalization entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface NotificationMessageLocalizationMapper {

    /**
     * Convert a NotificationMessageLocalization entity to a DTO.
     *
     * @param entity the entity to convert
     * @return the converted DTO
     */
    NotificationMessageLocalizationDTO toDTO(NotificationMessageLocalization entity);

    /**
     * Convert a NotificationMessageLocalizationDTO to an entity.
     *
     * @param dto the DTO to convert
     * @return the converted entity
     */
    NotificationMessageLocalization toEntity(NotificationMessageLocalizationDTO dto);
}
