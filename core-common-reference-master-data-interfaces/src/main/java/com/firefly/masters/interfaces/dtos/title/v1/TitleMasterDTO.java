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


package com.firefly.masters.interfaces.dtos.title.v1;

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
public class TitleMasterDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID titleId;

    @NotBlank(message = "Prefix is required")
    @Size(max = 10, message = "Prefix must not exceed 10 characters")
    private String prefix;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private Boolean isActive;

    @NotNull(message = "Status is required")
    private StatusEnum status;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
