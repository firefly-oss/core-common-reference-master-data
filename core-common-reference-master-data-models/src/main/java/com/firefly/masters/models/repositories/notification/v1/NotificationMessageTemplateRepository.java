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

import com.firefly.masters.models.entities.notification.v1.NotificationMessageTemplate;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing NotificationMessageTemplate entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface NotificationMessageTemplateRepository extends BaseRepository<NotificationMessageTemplate, UUID> {

    /**
     * Find all templates for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Flux of NotificationMessageTemplate entities for the specified message
     */
    Flux<NotificationMessageTemplate> findByMessageId(UUID messageId);

    /**
     * Count templates for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM notification_message_template WHERE message_id = :messageId")
    Mono<Long> countByMessageId(UUID messageId);

    /**
     * Find a specific template by message ID and template name.
     *
     * @param messageId the ID of the notification message
     * @param templateName the name of the template
     * @return a Mono of NotificationMessageTemplate
     */
    Mono<NotificationMessageTemplate> findByMessageIdAndTemplateName(UUID messageId, String templateName);

    /**
     * Find all templates of a specific type.
     *
     * @param templateType the type of template
     * @param pageable pagination information
     * @return a Flux of NotificationMessageTemplate entities of the specified type
     */
    Flux<NotificationMessageTemplate> findByTemplateType(String templateType, Pageable pageable);

    /**
     * Count templates of a specific type.
     *
     * @param templateType the type of template
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM notification_message_template WHERE template_type = :templateType")
    Mono<Long> countByTemplateType(String templateType);

    /**
     * Delete all templates for a specific message.
     *
     * @param messageId the ID of the notification message
     * @return a Mono of Void
     */
    Mono<Void> deleteByMessageId(UUID messageId);
}
