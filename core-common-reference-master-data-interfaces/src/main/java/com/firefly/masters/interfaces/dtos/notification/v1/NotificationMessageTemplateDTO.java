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


package com.firefly.masters.interfaces.dtos.notification.v1;

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
import java.util.Map;
import java.util.UUID;

/**
 * Data Transfer Object for notification message template information.
 * Used for transferring notification message template data between different layers of the application.
 * Includes support for template variables that can be used for dynamic content generation.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageTemplateDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID templateId;

    @NotNull(message = "Message ID is required")
    private UUID messageId;

    @NotBlank(message = "Template name is required")
    @Size(max = 100, message = "Template name must not exceed 100 characters")
    private String templateName;

    @NotBlank(message = "Template content is required")
    @Size(max = 5000, message = "Template content must not exceed 5000 characters")
    private String templateContent;

    @NotBlank(message = "Template type is required")
    @Size(max = 50, message = "Template type must not exceed 50 characters")
    private String templateType;

    @Size(max = 20, message = "Version must not exceed 20 characters")
    private String version;

    private Map<String, Object> templateVariables;

    @NotNull(message = "Status is required")
    private StatusEnum status;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
