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


package com.firefly.masters.interfaces.dtos.consent.v1;

import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for consent catalog information.
 * Used for transferring consent catalog data between different layers of the application.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsentCatalogDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID consentId;

    @NotBlank(message = "Consent type is required")
    @Size(max = 50, message = "Consent type must not exceed 50 characters")
    private String consentType;

    @NotBlank(message = "Consent description is required")
    @Size(max = 500, message = "Consent description must not exceed 500 characters")
    private String consentDescription;

    private Integer expiryPeriodDays;

    @Size(max = 20, message = "Consent version must not exceed 20 characters")
    private String consentVersion;

    @Size(max = 100, message = "Consent source must not exceed 100 characters")
    private String consentSource;

    @NotNull(message = "Status is required")
    private StatusEnum status;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}