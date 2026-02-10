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


package com.firefly.masters.core.services.notification.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageCatalogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing notification message catalog operations.
 */
public interface NotificationMessageCatalogService {

    /**
     * List all notification messages with pagination.
     *
     * @param paginationRequest pagination parameters
     * @return a paginated response of notification message DTOs
     */
    Mono<PaginationResponse<NotificationMessageCatalogDTO>> listNotificationMessages(PaginationRequest paginationRequest);

    /**
     * List notification messages by event type with pagination.
     *
     * @param eventType the type of event
     * @param paginationRequest pagination parameters
     * @return a paginated response of notification message DTOs
     */
    Mono<PaginationResponse<NotificationMessageCatalogDTO>> listNotificationMessagesByEventType(String eventType, PaginationRequest paginationRequest);

    /**
     * List notification messages by message type with pagination.
     *
     * @param typeId the ID of the message type
     * @param paginationRequest pagination parameters
     * @return a paginated response of notification message DTOs
     */
    Mono<PaginationResponse<NotificationMessageCatalogDTO>> listNotificationMessagesByTypeId(UUID typeId, PaginationRequest paginationRequest);

    /**
     * Create a new notification message.
     *
     * @param notificationMessageDTO the notification message data
     * @return the created notification message DTO
     */
    Mono<NotificationMessageCatalogDTO> createNotificationMessage(NotificationMessageCatalogDTO notificationMessageDTO);

    /**
     * Get a notification message by ID.
     *
     * @param messageId the ID of the notification message
     * @return the notification message DTO
     */
    Mono<NotificationMessageCatalogDTO> getNotificationMessage(UUID messageId);

    /**
     * Get a notification message by code.
     *
     * @param messageCode the code of the notification message
     * @return the notification message DTO
     */
    Mono<NotificationMessageCatalogDTO> getNotificationMessageByCode(String messageCode);

    /**
     * Update a notification message.
     *
     * @param messageId the ID of the notification message to update
     * @param notificationMessageDTO the updated notification message data
     * @return the updated notification message DTO
     */
    Mono<NotificationMessageCatalogDTO> updateNotificationMessage(UUID messageId, NotificationMessageCatalogDTO notificationMessageDTO);

    /**
     * Delete a notification message.
     *
     * @param messageId the ID of the notification message to delete
     * @return a Mono of Void
     */
    Mono<Void> deleteNotificationMessage(UUID messageId);
}
