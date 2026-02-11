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

import com.firefly.masters.core.mappers.notification.v1.NotificationMessageLocalizationMapper;
import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageLocalizationDTO;
import com.firefly.masters.models.entities.notification.v1.NotificationMessageLocalization;
import com.firefly.masters.models.repositories.notification.v1.NotificationMessageLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the NotificationMessageLocalizationService interface.
 */
@Service
@Transactional
public class NotificationMessageLocalizationServiceImpl implements NotificationMessageLocalizationService {

    @Autowired
    private NotificationMessageLocalizationRepository repository;

    @Autowired
    private NotificationMessageLocalizationMapper mapper;

    @Override
    public Flux<NotificationMessageLocalizationDTO> getLocalizationsByMessageId(UUID messageId) {
        return repository.findByMessageId(messageId)
                .map(mapper::toDTO)
                .switchIfEmpty(Flux.error(new RuntimeException("No localizations found for message ID: " + messageId)));
    }

    @Override
    public Flux<NotificationMessageLocalizationDTO> getLocalizationsByLocaleId(UUID localeId) {
        return repository.findByLocaleId(localeId)
                .map(mapper::toDTO)
                .switchIfEmpty(Flux.error(new RuntimeException("No localizations found for locale ID: " + localeId)));
    }

    @Override
    public Mono<NotificationMessageLocalizationDTO> createNotificationMessageLocalization(NotificationMessageLocalizationDTO localizationDTO) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        localizationDTO.setDateCreated(now);
        localizationDTO.setDateUpdated(now);

        return Mono.just(localizationDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating notification message localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<NotificationMessageLocalizationDTO> getNotificationMessageLocalization(UUID localizationId) {
        return repository.findById(localizationId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Notification message localization not found with ID: " + localizationId)));
    }

    @Override
    public Mono<NotificationMessageLocalizationDTO> getNotificationMessageLocalizationByMessageAndLocale(UUID messageId, UUID localeId) {
        return repository.findByMessageIdAndLocaleId(messageId, localeId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Notification message localization not found with message ID: " + messageId + " and locale ID: " + localeId)));
    }

    @Override
    public Mono<NotificationMessageLocalizationDTO> updateNotificationMessageLocalization(UUID localizationId, NotificationMessageLocalizationDTO localizationDTO) {
        return repository.findById(localizationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Notification message localization not found with ID: " + localizationId)))
                .flatMap(existingEntity -> {
                    NotificationMessageLocalization updatedEntity = mapper.toEntity(localizationDTO);
                    updatedEntity.setLocalizationId(localizationId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    updatedEntity.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error updating notification message localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteNotificationMessageLocalization(UUID localizationId) {
        return repository.findById(localizationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Notification message localization not found with ID: " + localizationId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting notification message localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteLocalizationsByMessageId(UUID messageId) {
        return repository.deleteByMessageId(messageId)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting localizations for message ID: " + messageId, e)));
    }
}
