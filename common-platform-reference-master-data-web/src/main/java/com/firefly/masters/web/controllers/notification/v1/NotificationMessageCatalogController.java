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


package com.firefly.masters.web.controllers.notification.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.core.services.notification.v1.NotificationMessageCatalogService;
import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageCatalogDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing notification message catalog operations.
 */
@Tag(name = "Notification Message Catalog", description = "APIs for managing notification message catalog")
@RestController
@RequestMapping("/api/v1/notification-messages")
public class NotificationMessageCatalogController {

    @Autowired
    private NotificationMessageCatalogService service;

    @Operation(summary = "List Notification Messages", description = "Retrieve a paginated list of notification messages.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of notification messages",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<NotificationMessageCatalogDTO>>> listNotificationMessages(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listNotificationMessages(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Notification Messages by Event Type", description = "Retrieve a paginated list of notification messages for a specific event type.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of notification messages",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/event-type/{eventType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<NotificationMessageCatalogDTO>>> listNotificationMessagesByEventType(
            @Parameter(in = ParameterIn.PATH, description = "Event type", required = true)
            @PathVariable String eventType,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listNotificationMessagesByEventType(eventType, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Notification Messages by Message Type", description = "Retrieve a paginated list of notification messages for a specific message type.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of notification messages",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/message-type/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<NotificationMessageCatalogDTO>>> listNotificationMessagesByTypeId(
            @Parameter(in = ParameterIn.PATH, description = "Message type ID", required = true)
            @PathVariable UUID typeId,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listNotificationMessagesByTypeId(typeId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Notification Message", description = "Create a new notification message.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageCatalogDTO>> createNotificationMessage(
            @RequestBody NotificationMessageCatalogDTO notificationMessageDTO
    ) {
        return service.createNotificationMessage(notificationMessageDTO)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Notification Message by ID", description = "Retrieve a specific notification message by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification message not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageCatalogDTO>> getNotificationMessage(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message", required = true)
            @PathVariable UUID messageId
    ) {
        return service.getNotificationMessage(messageId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Notification Message by Code", description = "Retrieve a specific notification message by its code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification message not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/code/{messageCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageCatalogDTO>> getNotificationMessageByCode(
            @Parameter(in = ParameterIn.PATH, description = "Code of the notification message", required = true)
            @PathVariable String messageCode
    ) {
        return service.getNotificationMessageByCode(messageCode)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Notification Message", description = "Update a specific notification message by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification message not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PutMapping(value = "/{messageId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageCatalogDTO>> updateNotificationMessage(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message", required = true)
            @PathVariable UUID messageId,
            @RequestBody NotificationMessageCatalogDTO notificationMessageDTO
    ) {
        return service.updateNotificationMessage(messageId, notificationMessageDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Notification Message", description = "Delete a specific notification message by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notification message deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification message not found")
    })
    @DeleteMapping("/{messageId}")
    public Mono<ResponseEntity<Void>> deleteNotificationMessage(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message", required = true)
            @PathVariable UUID messageId
    ) {
        return service.deleteNotificationMessage(messageId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
