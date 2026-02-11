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


package com.firefly.masters.interfaces.dtos.contractrole.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class ContractRoleScopeDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID scopeId;

    @NotNull(message = "Role ID is required")
    private UUID roleId;

    @NotBlank(message = "Scope code is required")
    @Size(max = 100, message = "Scope code must not exceed 100 characters")
    private String scopeCode;

    @NotBlank(message = "Scope name is required")
    @Size(max = 200, message = "Scope name must not exceed 200 characters")
    private String scopeName;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Action type is required")
    @Size(max = 50, message = "Action type must not exceed 50 characters")
    private String actionType;

    @Size(max = 100, message = "Resource type must not exceed 100 characters")
    private String resourceType;

    private Boolean isActive;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
