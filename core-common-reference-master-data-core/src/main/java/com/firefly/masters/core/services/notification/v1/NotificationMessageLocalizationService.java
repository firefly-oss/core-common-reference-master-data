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

import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageLocalizationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing notification message localization operations.
 */
public interface NotificationMessageLocalizationService {

    /**
     * Get all localizations for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Flux of notification message localization DTOs
     */
    Flux<NotificationMessageLocalizationDTO> getLocalizationsByMessageId(UUID messageId);

    /**
     * Get all localizations for a specific locale.
     *
     * @param localeId the ID of the language locale
     * @return a Flux of notification message localization DTOs
     */
    Flux<NotificationMessageLocalizationDTO> getLocalizationsByLocaleId(UUID localeId);

    /**
     * Create a new notification message localization.
     *
     * @param localizationDTO the notification message localization data
     * @return the created notification message localization DTO
     */
    Mono<NotificationMessageLocalizationDTO> createNotificationMessageLocalization(NotificationMessageLocalizationDTO localizationDTO);

    /**
     * Get a notification message localization by ID.
     *
     * @param localizationId the ID of the notification message localization
     * @return the notification message localization DTO
     */
    Mono<NotificationMessageLocalizationDTO> getNotificationMessageLocalization(UUID localizationId);

    /**
     * Get a notification message localization by message ID and locale ID.
     *
     * @param messageId the ID of the notification message
     * @param localeId the ID of the language locale
     * @return the notification message localization DTO
     */
    Mono<NotificationMessageLocalizationDTO> getNotificationMessageLocalizationByMessageAndLocale(UUID messageId, UUID localeId);

    /**
     * Update a notification message localization.
     *
     * @param localizationId the ID of the notification message localization to update
     * @param localizationDTO the updated notification message localization data
     * @return the updated notification message localization DTO
     */
    Mono<NotificationMessageLocalizationDTO> updateNotificationMessageLocalization(UUID localizationId, NotificationMessageLocalizationDTO localizationDTO);

    /**
     * Delete a notification message localization.
     *
     * @param localizationId the ID of the notification message localization to delete
     * @return a Mono of Void
     */
    Mono<Void> deleteNotificationMessageLocalization(UUID localizationId);

    /**
     * Delete all localizations for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Mono of Void
     */
    Mono<Void> deleteLocalizationsByMessageId(UUID messageId);
}
