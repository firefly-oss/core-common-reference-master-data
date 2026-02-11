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

import com.firefly.masters.models.entities.notification.v1.NotificationMessageCatalog;
import com.firefly.masters.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing NotificationMessageCatalog entities.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface NotificationMessageCatalogRepository extends BaseRepository<NotificationMessageCatalog, UUID> {

    /**
     * Find a notification message by its code.
     *
     * @param messageCode the unique code of the message
     * @return a Mono of NotificationMessageCatalog
     */
    Mono<NotificationMessageCatalog> findByMessageCode(String messageCode);

    /**
     * Find all notification messages of a specific event type.
     *
     * @param eventType the type of event
     * @param pageable pagination information
     * @return a Flux of NotificationMessageCatalog entities of the specified event type
     */
    Flux<NotificationMessageCatalog> findByEventType(String eventType, Pageable pageable);

    /**
     * Count notification messages of a specific event type.
     *
     * @param eventType the type of event
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM notification_message_catalog WHERE event_type = :eventType")
    Mono<Long> countByEventType(String eventType);

    /**
     * Find all notification messages of a specific message type.
     *
     * @param typeId the ID of the message type
     * @param pageable pagination information
     * @return a Flux of NotificationMessageCatalog entities of the specified message type
     */
    Flux<NotificationMessageCatalog> findByTypeId(UUID typeId, Pageable pageable);

    /**
     * Count notification messages of a specific message type.
     *
     * @param typeId the ID of the message type
     * @return a Mono of Long representing the count
     */
    @Query("SELECT COUNT(*) FROM notification_message_catalog WHERE type_id = :typeId")
    Mono<Long> countByTypeId(UUID typeId);
}
