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
import com.firefly.masters.interfaces.dtos.notification.v1.MessageTypeCatalogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing message type catalog operations.
 */
public interface MessageTypeCatalogService {

    /**
     * List all message types with pagination.
     *
     * @param paginationRequest pagination parameters
     * @return a paginated response of message type DTOs
     */
    Mono<PaginationResponse<MessageTypeCatalogDTO>> listMessageTypes(PaginationRequest paginationRequest);

    /**
     * Create a new message type.
     *
     * @param messageTypeDTO the message type data
     * @return the created message type DTO
     */
    Mono<MessageTypeCatalogDTO> createMessageType(MessageTypeCatalogDTO messageTypeDTO);

    /**
     * Get a message type by ID.
     *
     * @param typeId the ID of the message type
     * @return the message type DTO
     */
    Mono<MessageTypeCatalogDTO> getMessageType(UUID typeId);

    /**
     * Get a message type by code.
     *
     * @param typeCode the code of the message type
     * @return the message type DTO
     */
    Mono<MessageTypeCatalogDTO> getMessageTypeByCode(String typeCode);

    /**
     * Update a message type.
     *
     * @param typeId the ID of the message type to update
     * @param messageTypeDTO the updated message type data
     * @return the updated message type DTO
     */
    Mono<MessageTypeCatalogDTO> updateMessageType(UUID typeId, MessageTypeCatalogDTO messageTypeDTO);

    /**
     * Delete a message type.
     *
     * @param typeId the ID of the message type to delete
     * @return a Mono of Void
     */
    Mono<Void> deleteMessageType(UUID typeId);
}
