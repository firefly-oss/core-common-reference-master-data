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

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Data Transfer Object for notification message catalog information.
 * Used for transferring notification message catalog data between different layers of the application.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageCatalogDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID messageId;

    @NotBlank(message = "Message code is required")
    @Size(max = 50, message = "Message code must not exceed 50 characters")
    private String messageCode;

    @NotNull(message = "Type ID is required")
    private UUID typeId;

    @Valid
    private MessageTypeCatalogDTO messageType;

    @NotBlank(message = "Event type is required")
    @Size(max = 50, message = "Event type must not exceed 50 characters")
    private String eventType;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Size(max = 200, message = "Default subject must not exceed 200 characters")
    private String defaultSubject;

    @Size(max = 2000, message = "Default message must not exceed 2000 characters")
    private String defaultMessage;

    private Map<String, Object> parameters;

    @NotNull(message = "Status is required")
    private StatusEnum status;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
