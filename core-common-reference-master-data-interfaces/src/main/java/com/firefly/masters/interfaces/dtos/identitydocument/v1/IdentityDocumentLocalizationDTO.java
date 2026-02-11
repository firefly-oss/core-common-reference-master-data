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


package com.firefly.masters.interfaces.dtos.identitydocument.v1;

import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for identity document localization information.
 * Used for transferring identity document localization data between different layers of the application.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentityDocumentLocalizationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID localizationId;

    @NotNull(message = "Document ID is required")
    private UUID documentId;

    @NotNull(message = "Locale ID is required")
    private UUID localeId;

    @NotBlank(message = "Document name is required")
    @Size(max = 100, message = "Document name must not exceed 100 characters")
    private String documentName;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Size(max = 200, message = "Format description must not exceed 200 characters")
    private String formatDescription;

    @NotNull(message = "Status is required")
    private StatusEnum status;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
