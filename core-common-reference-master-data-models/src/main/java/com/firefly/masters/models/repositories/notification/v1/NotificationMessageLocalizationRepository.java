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


package com.firefly.masters.models.repositories.notification.v1;

import com.firefly.masters.models.entities.notification.v1.NotificationMessageLocalization;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing NotificationMessageLocalization entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface NotificationMessageLocalizationRepository extends BaseRepository<NotificationMessageLocalization, UUID> {

    /**
     * Find all localizations for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Flux of NotificationMessageLocalization entities for the specified message
     */
    Flux<NotificationMessageLocalization> findByMessageId(UUID messageId);

    /**
     * Count localizations for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM notification_message_localization WHERE message_id = :messageId")
    Mono<Long> countByMessageId(UUID messageId);

    /**
     * Find all localizations for a specific locale.
     *
     * @param localeId the ID of the language locale
     * @return a Flux of NotificationMessageLocalization entities for the specified locale
     */
    Flux<NotificationMessageLocalization> findByLocaleId(UUID localeId);

    /**
     * Count localizations for a specific locale.
     *
     * @param localeId the ID of the language locale
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM notification_message_localization WHERE locale_id = :localeId")
    Mono<Long> countByLocaleId(UUID localeId);

    /**
     * Find a specific localization by message ID and locale ID.
     *
     * @param messageId the ID of the notification message
     * @param localeId the ID of the language locale
     * @return a Mono of NotificationMessageLocalization
     */
    Mono<NotificationMessageLocalization> findByMessageIdAndLocaleId(UUID messageId, UUID localeId);

    /**
     * Delete all localizations for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Mono of Void
     */
    Mono<Void> deleteByMessageId(UUID messageId);
}
