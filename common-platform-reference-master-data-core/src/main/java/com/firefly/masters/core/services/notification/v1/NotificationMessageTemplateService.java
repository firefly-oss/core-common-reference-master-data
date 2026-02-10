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
import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageTemplateDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing notification message template operations.
 */
public interface NotificationMessageTemplateService {

    /**
     * List all notification message templates with pagination.
     *
     * @param paginationRequest pagination parameters
     * @return a paginated response of notification message template DTOs
     */
    Mono<PaginationResponse<NotificationMessageTemplateDTO>> listNotificationMessageTemplates(PaginationRequest paginationRequest);

    /**
     * List notification message templates by template type with pagination.
     *
     * @param templateType the type of template
     * @param paginationRequest pagination parameters
     * @return a paginated response of notification message template DTOs
     */
    Mono<PaginationResponse<NotificationMessageTemplateDTO>> listNotificationMessageTemplatesByType(String templateType, PaginationRequest paginationRequest);

    /**
     * Get all templates for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Flux of notification message template DTOs
     */
    Flux<NotificationMessageTemplateDTO> getTemplatesByMessageId(UUID messageId);

    /**
     * Create a new notification message template.
     *
     * @param templateDTO the notification message template data
     * @return the created notification message template DTO
     */
    Mono<NotificationMessageTemplateDTO> createNotificationMessageTemplate(NotificationMessageTemplateDTO templateDTO);

    /**
     * Get a notification message template by ID.
     *
     * @param templateId the ID of the notification message template
     * @return the notification message template DTO
     */
    Mono<NotificationMessageTemplateDTO> getNotificationMessageTemplate(UUID templateId);

    /**
     * Get a notification message template by message ID and template name.
     *
     * @param messageId the ID of the notification message
     * @param templateName the name of the template
     * @return the notification message template DTO
     */
    Mono<NotificationMessageTemplateDTO> getNotificationMessageTemplateByNameAndMessageId(UUID messageId, String templateName);

    /**
     * Update a notification message template.
     *
     * @param templateId the ID of the notification message template to update
     * @param templateDTO the updated notification message template data
     * @return the updated notification message template DTO
     */
    Mono<NotificationMessageTemplateDTO> updateNotificationMessageTemplate(UUID templateId, NotificationMessageTemplateDTO templateDTO);

    /**
     * Delete a notification message template.
     *
     * @param templateId the ID of the notification message template to delete
     * @return a Mono of Void
     */
    Mono<Void> deleteNotificationMessageTemplate(UUID templateId);

    /**
     * Delete all templates for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Mono of Void
     */
    Mono<Void> deleteTemplatesByMessageId(UUID messageId);
}
