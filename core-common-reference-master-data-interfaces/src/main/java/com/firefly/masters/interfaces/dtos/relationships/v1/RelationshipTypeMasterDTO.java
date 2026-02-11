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


package com.firefly.masters.interfaces.dtos.relationships.v1;

import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipTypeMasterDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID relationshipTypeId;

    @NotBlank(message = "Relationship type code is required")
    @Size(max = 100, message = "Relationship type code must not exceed 100 characters")
    private String code;

    @NotBlank(message = "Description is required")
    @Size(max = 200, message = "Description must not exceed 200 characters")
    private String description;

    @NotNull(message = "Status is required")
    private StatusEnum status;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}