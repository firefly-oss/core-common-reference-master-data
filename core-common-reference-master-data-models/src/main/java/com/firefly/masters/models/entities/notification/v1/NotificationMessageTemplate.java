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


package com.firefly.masters.models.entities.notification.v1;

import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a notification message template.
 * This stores HTML templates for notification messages with support for template variables.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("notification_message_template")
public class NotificationMessageTemplate {

    @Id
    @Column("template_id")
    private UUID templateId;

    @Column("message_id")
    private UUID messageId;  // References notification_message_catalog(message_id)

    @Column("template_name")
    private String templateName;

    @Column("template_content")
    private String templateContent;

    @Column("template_type")
    private String templateType;

    @Column("version")
    private String version;

    @Column("template_variables")
    private String templateVariables;  // Stored as JSONB in the database

    @Column("status")
    private StatusEnum status;

    @Column("date_created")
    private LocalDateTime dateCreated;

    @Column("date_updated")
    private LocalDateTime dateUpdated;
}
