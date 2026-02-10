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
import com.firefly.masters.core.mappers.notification.v1.MessageTypeCatalogMapper;
import com.firefly.masters.interfaces.dtos.notification.v1.MessageTypeCatalogDTO;
import com.firefly.masters.models.entities.notification.v1.MessageTypeCatalog;
import com.firefly.masters.models.repositories.notification.v1.MessageTypeCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the MessageTypeCatalogService interface.
 */
@Service
@Transactional
public class MessageTypeCatalogServiceImpl implements MessageTypeCatalogService {

    @Autowired
    private MessageTypeCatalogRepository repository;

    @Autowired
    private MessageTypeCatalogMapper mapper;

    @Override
    public Mono<PaginationResponse<MessageTypeCatalogDTO>> listMessageTypes(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<MessageTypeCatalogDTO> createMessageType(MessageTypeCatalogDTO messageTypeDTO) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        messageTypeDTO.setDateCreated(now);
        messageTypeDTO.setDateUpdated(now);

        return Mono.just(messageTypeDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating message type: " + e.getMessage(), e)));
    }

    @Override
    public Mono<MessageTypeCatalogDTO> getMessageType(UUID typeId) {
        return repository.findById(typeId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Message type not found with ID: " + typeId)));
    }

    @Override
    public Mono<MessageTypeCatalogDTO> getMessageTypeByCode(String typeCode) {
        return repository.findByTypeCode(typeCode)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Message type not found with code: " + typeCode)));
    }

    @Override
    public Mono<MessageTypeCatalogDTO> updateMessageType(UUID typeId, MessageTypeCatalogDTO messageTypeDTO) {
        return repository.findById(typeId)
                .switchIfEmpty(Mono.error(new RuntimeException("Message type not found with ID: " + typeId)))
                .flatMap(existingEntity -> {
                    MessageTypeCatalog updatedEntity = mapper.toEntity(messageTypeDTO);
                    updatedEntity.setTypeId(typeId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    updatedEntity.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error updating message type: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteMessageType(UUID typeId) {
        return repository.findById(typeId)
                .switchIfEmpty(Mono.error(new RuntimeException("Message type not found with ID: " + typeId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting message type: " + e.getMessage(), e)));
    }
}
