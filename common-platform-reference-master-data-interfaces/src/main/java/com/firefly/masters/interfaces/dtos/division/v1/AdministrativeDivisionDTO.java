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


package com.firefly.masters.interfaces.dtos.division.v1;

import org.fireflyframework.utils.annotations.FilterableId;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministrativeDivisionDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID divisionId;

    @FilterableId
    @NotNull(message = "Country ID is required")
    private UUID countryId;

    @NotBlank(message = "Division code is required")
    @Size(max = 20, message = "Division code must not exceed 20 characters")
    private String code;

    @NotBlank(message = "Division name is required")
    @Size(max = 100, message = "Division name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Division level is required")
    @Size(max = 50, message = "Division level must not exceed 50 characters")
    private String level;

    private UUID parentDivisionId;

    @NotNull(message = "Status is required")
    private StatusEnum status;

    @Size(max = 50, message = "Postal code pattern must not exceed 50 characters")
    private String postalCodePattern;

    @Size(max = 50, message = "Time zone must not exceed 50 characters")
    private String timeZone;
}
