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
 * Entity representing a notification message catalog record.
 * This defines the types of notification messages that can be sent to users based on events.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("notification_message_catalog")
public class NotificationMessageCatalog {

    @Id
    @Column("message_id")
    private UUID messageId;

    @Column("message_code")
    private String messageCode;

    @Column("type_id")
    private UUID typeId;

    @Column("event_type")
    private String eventType;

    @Column("description")
    private String description;

    @Column("default_subject")
    private String defaultSubject;

    @Column("default_message")
    private String defaultMessage;

    @Column("parameters")
    private String parameters;  // Stored as JSONB in the database

    @Column("status")
    private StatusEnum status;

    @Column("date_created")
    private LocalDateTime dateCreated;

    @Column("date_updated")
    private LocalDateTime dateUpdated;
}
