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
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.notification.v1.NotificationMessageCatalogMapper;
import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageCatalogDTO;
import com.firefly.masters.models.entities.notification.v1.NotificationMessageCatalog;
import com.firefly.masters.models.repositories.notification.v1.NotificationMessageCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the NotificationMessageCatalogService interface.
 */
@Service
@Transactional
public class NotificationMessageCatalogServiceImpl implements NotificationMessageCatalogService {

    @Autowired
    private NotificationMessageCatalogRepository repository;

    @Autowired
    private NotificationMessageCatalogMapper mapper;

    @Override
    public Mono<PaginationResponse<NotificationMessageCatalogDTO>> listNotificationMessages(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<PaginationResponse<NotificationMessageCatalogDTO>> listNotificationMessagesByEventType(String eventType, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findByEventType(eventType, pageable),
                () -> repository.countByEventType(eventType)
        );
    }

    @Override
    public Mono<PaginationResponse<NotificationMessageCatalogDTO>> listNotificationMessagesByTypeId(UUID typeId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findByTypeId(typeId, pageable),
                () -> repository.countByTypeId(typeId)
        );
    }

    @Override
    public Mono<NotificationMessageCatalogDTO> createNotificationMessage(NotificationMessageCatalogDTO notificationMessageDTO) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        notificationMessageDTO.setDateCreated(now);
        notificationMessageDTO.setDateUpdated(now);

        return Mono.just(notificationMessageDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating notification message: " + e.getMessage(), e)));
    }

    @Override
    public Mono<NotificationMessageCatalogDTO> getNotificationMessage(UUID messageId) {
        return repository.findById(messageId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Notification message not found with ID: " + messageId)));
    }

    @Override
    public Mono<NotificationMessageCatalogDTO> getNotificationMessageByCode(String messageCode) {
        return repository.findByMessageCode(messageCode)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Notification message not found with code: " + messageCode)));
    }

    @Override
    public Mono<NotificationMessageCatalogDTO> updateNotificationMessage(UUID messageId, NotificationMessageCatalogDTO notificationMessageDTO) {
        return repository.findById(messageId)
                .switchIfEmpty(Mono.error(new RuntimeException("Notification message not found with ID: " + messageId)))
                .flatMap(existingEntity -> {
                    NotificationMessageCatalog updatedEntity = mapper.toEntity(notificationMessageDTO);
                    updatedEntity.setMessageId(messageId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    updatedEntity.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error updating notification message: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteNotificationMessage(UUID messageId) {
        return repository.findById(messageId)
                .switchIfEmpty(Mono.error(new RuntimeException("Notification message not found with ID: " + messageId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting notification message: " + e.getMessage(), e)));
    }
}
