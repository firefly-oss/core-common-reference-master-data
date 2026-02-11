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
import com.firefly.masters.core.services.notification.v1.NotificationMessageTemplateService;
import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageTemplateDTO;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing notification message template operations.
 */
@Tag(name = "Notification Message Templates", description = "APIs for managing notification message templates")
@RestController
@RequestMapping("/api/v1/notification-templates")
public class NotificationMessageTemplateController {

    @Autowired
    private NotificationMessageTemplateService service;

    @Operation(summary = "List Notification Message Templates", description = "Retrieve a paginated list of notification message templates.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of notification message templates",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<NotificationMessageTemplateDTO>>> listNotificationMessageTemplates(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listNotificationMessageTemplates(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "List Notification Message Templates by Type", description = "Retrieve a paginated list of notification message templates for a specific template type.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of notification message templates",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(value = "/type/{templateType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<NotificationMessageTemplateDTO>>> listNotificationMessageTemplatesByType(
            @Parameter(in = ParameterIn.PATH, description = "Template type", required = true)
            @PathVariable String templateType,
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listNotificationMessageTemplatesByType(templateType, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Templates by Message ID", description = "Retrieve all templates for a specific notification message.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved templates",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageTemplateDTO.class)
                    )
            )
    })
    @GetMapping(value = "/message/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<NotificationMessageTemplateDTO>>> getTemplatesByMessageId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message", required = true)
            @PathVariable UUID messageId
    ) {
        return Mono.just(ResponseEntity.ok(service.getTemplatesByMessageId(messageId)));
    }

    @Operation(summary = "Create Notification Message Template", description = "Create a new notification message template.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message template created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageTemplateDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageTemplateDTO>> createNotificationMessageTemplate(
            @RequestBody NotificationMessageTemplateDTO templateDTO
    ) {
        return service.createNotificationMessageTemplate(templateDTO)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Notification Message Template by ID", description = "Retrieve a specific notification message template by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message template retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageTemplateDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification message template not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{templateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageTemplateDTO>> getNotificationMessageTemplate(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message template", required = true)
            @PathVariable UUID templateId
    ) {
        return service.getNotificationMessageTemplate(templateId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Notification Message Template by Name and Message ID", description = "Retrieve a specific notification message template by its name and message ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message template retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageTemplateDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification message template not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/message/{messageId}/name/{templateName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageTemplateDTO>> getNotificationMessageTemplateByNameAndMessageId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message", required = true)
            @PathVariable UUID messageId,
            @Parameter(in = ParameterIn.PATH, description = "Name of the template", required = true)
            @PathVariable String templateName
    ) {
        return service.getNotificationMessageTemplateByNameAndMessageId(messageId, templateName)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Notification Message Template", description = "Update a specific notification message template by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message template updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageTemplateDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification message template not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PutMapping(value = "/{templateId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageTemplateDTO>> updateNotificationMessageTemplate(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message template", required = true)
            @PathVariable UUID templateId,
            @RequestBody NotificationMessageTemplateDTO templateDTO
    ) {
        return service.updateNotificationMessageTemplate(templateId, templateDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Notification Message Template", description = "Delete a specific notification message template by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notification message template deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification message template not found")
    })
    @DeleteMapping("/{templateId}")
    public Mono<ResponseEntity<Void>> deleteNotificationMessageTemplate(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message template", required = true)
            @PathVariable UUID templateId
    ) {
        return service.deleteNotificationMessageTemplate(templateId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Templates by Message ID", description = "Delete all templates for a specific notification message.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Templates deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification message not found")
    })
    @DeleteMapping("/message/{messageId}")
    public Mono<ResponseEntity<Void>> deleteTemplatesByMessageId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message", required = true)
            @PathVariable UUID messageId
    ) {
        return service.deleteTemplatesByMessageId(messageId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
