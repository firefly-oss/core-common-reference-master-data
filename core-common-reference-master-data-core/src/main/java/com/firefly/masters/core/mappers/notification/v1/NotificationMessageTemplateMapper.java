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

import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageTemplateDTO;
import com.firefly.masters.models.entities.notification.v1.NotificationMessageTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Mapper for converting between NotificationMessageTemplate entities and DTOs.
 * Includes support for handling template variables stored as JSON.
 */
@Mapper(componentModel = "spring")
public abstract class NotificationMessageTemplateMapper {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Convert a NotificationMessageTemplate entity to a DTO.
     *
     * @param entity the entity to convert
     * @return the converted DTO
     */
    @Mapping(source = "templateVariables", target = "templateVariables", qualifiedByName = "templateVariablesToMap")
    public abstract NotificationMessageTemplateDTO toDTO(NotificationMessageTemplate entity);

    /**
     * Convert a NotificationMessageTemplateDTO to an entity.
     *
     * @param dto the DTO to convert
     * @return the converted entity
     */
    @Mapping(source = "templateVariables", target = "templateVariables", qualifiedByName = "mapToTemplateVariables")
    public abstract NotificationMessageTemplate toEntity(NotificationMessageTemplateDTO dto);

    /**
     * Convert a JSON string to a Map.
     *
     * @param templateVariables the JSON string to convert
     * @return the converted Map
     */
    @Named("templateVariablesToMap")
    protected Map<String, Object> templateVariablesToMap(String templateVariables) {
        if (templateVariables == null) {
            return null;
        }
        try {
            return objectMapper.readValue(templateVariables, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting template variables JSON to Map", e);
        }
    }

    /**
     * Convert a Map to a JSON string.
     *
     * @param templateVariables the Map to convert
     * @return the converted JSON string
     */
    @Named("mapToTemplateVariables")
    protected String mapToTemplateVariables(Map<String, Object> templateVariables) {
        if (templateVariables == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(templateVariables);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting template variables Map to JSON", e);
        }
    }

}