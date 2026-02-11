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

import com.firefly.masters.core.services.notification.v1.NotificationMessageLocalizationService;
import com.firefly.masters.interfaces.dtos.notification.v1.NotificationMessageLocalizationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing notification message localization operations.
 */
@Tag(name = "Notification Message Localizations", description = "APIs for managing notification message localizations")
@RestController
@RequestMapping("/api/v1/notification-localizations")
public class NotificationMessageLocalizationController {

    @Autowired
    private NotificationMessageLocalizationService service;

    @Operation(summary = "Get Localizations by Message ID", description = "Retrieve all localizations for a specific notification message.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved localizations",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageLocalizationDTO.class)
                    )
            )
    })
    @GetMapping(value = "/message/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<NotificationMessageLocalizationDTO>>> getLocalizationsByMessageId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message", required = true)
            @PathVariable UUID messageId
    ) {
        return Mono.just(ResponseEntity.ok(service.getLocalizationsByMessageId(messageId)));
    }

    @Operation(summary = "Get Localizations by Locale ID", description = "Retrieve all localizations for a specific locale.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved localizations",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageLocalizationDTO.class)
                    )
            )
    })
    @GetMapping(value = "/locale/{localeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<NotificationMessageLocalizationDTO>>> getLocalizationsByLocaleId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the language locale", required = true)
            @PathVariable UUID localeId
    ) {
        return Mono.just(ResponseEntity.ok(service.getLocalizationsByLocaleId(localeId)));
    }

    @Operation(summary = "Create Notification Message Localization", description = "Create a new notification message localization.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message localization created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageLocalizationDTO>> createNotificationMessageLocalization(
            @RequestBody NotificationMessageLocalizationDTO localizationDTO
    ) {
        return service.createNotificationMessageLocalization(localizationDTO)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Notification Message Localization by ID", description = "Retrieve a specific notification message localization by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message localization retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification message localization not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{localizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageLocalizationDTO>> getNotificationMessageLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message localization", required = true)
            @PathVariable UUID localizationId
    ) {
        return service.getNotificationMessageLocalization(localizationId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Notification Message Localization by Message and Locale", description = "Retrieve a specific notification message localization by message ID and locale ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message localization retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification message localization not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/message/{messageId}/locale/{localeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageLocalizationDTO>> getNotificationMessageLocalizationByMessageAndLocale(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message", required = true)
            @PathVariable UUID messageId,
            @Parameter(in = ParameterIn.PATH, description = "ID of the language locale", required = true)
            @PathVariable UUID localeId
    ) {
        return service.getNotificationMessageLocalizationByMessageAndLocale(messageId, localeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Notification Message Localization", description = "Update a specific notification message localization by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification message localization updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotificationMessageLocalizationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification message localization not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PutMapping(value = "/{localizationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<NotificationMessageLocalizationDTO>> updateNotificationMessageLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message localization", required = true)
            @PathVariable UUID localizationId,
            @RequestBody NotificationMessageLocalizationDTO localizationDTO
    ) {
        return service.updateNotificationMessageLocalization(localizationId, localizationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Notification Message Localization", description = "Delete a specific notification message localization by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notification message localization deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification message localization not found")
    })
    @DeleteMapping("/{localizationId}")
    public Mono<ResponseEntity<Void>> deleteNotificationMessageLocalization(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message localization", required = true)
            @PathVariable UUID localizationId
    ) {
        return service.deleteNotificationMessageLocalization(localizationId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Localizations by Message ID", description = "Delete all localizations for a specific notification message.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Localizations deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification message not found")
    })
    @DeleteMapping("/message/{messageId}")
    public Mono<ResponseEntity<Void>> deleteLocalizationsByMessageId(
            @Parameter(in = ParameterIn.PATH, description = "ID of the notification message", required = true)
            @PathVariable UUID messageId
    ) {
        return service.deleteLocalizationsByMessageId(messageId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
