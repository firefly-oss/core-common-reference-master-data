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


package com.firefly.masters.interfaces.dtos.lookup.v1;

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
public class LookupDomainDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID domainId;

    @NotBlank(message = "Domain code is required")
    @Size(max = 50, message = "Domain code must not exceed 50 characters")
    private String domainCode;

    @NotBlank(message = "Domain name is required")
    @Size(max = 100, message = "Domain name must not exceed 100 characters")
    private String domainName;

    @Size(max = 500, message = "Domain description must not exceed 500 characters")
    private String domainDesc;

    private UUID parentDomainId;
    private Boolean multiselectAllowed;
    private Boolean hierarchyAllowed;
    private Boolean tenantOverridable;

    @Size(max = 2000, message = "Extra JSON must not exceed 2000 characters")
    private String extraJson;

    private UUID tenantId;

    @NotNull(message = "Status is required")
    private StatusEnum status;
}
