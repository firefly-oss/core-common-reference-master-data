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
import com.firefly.masters.core.services.notification.v1.MessageTypeCatalogService;
import com.firefly.masters.interfaces.dtos.notification.v1.MessageTypeCatalogDTO;
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
 * REST controller for managing message type catalog operations.
 */
@Tag(name = "Message Type Catalog", description = "APIs for managing message type catalog")
@RestController
@RequestMapping("/api/v1/message-types")
public class MessageTypeCatalogController {

    @Autowired
    private MessageTypeCatalogService service;

    @Operation(summary = "List Message Types", description = "Retrieve a paginated list of message types.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of message types",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<MessageTypeCatalogDTO>>> listMessageTypes(
            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.listMessageTypes(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Message Type", description = "Create a new message type.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message type created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageTypeCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MessageTypeCatalogDTO>> createMessageType(
            @RequestBody MessageTypeCatalogDTO messageTypeDTO
    ) {
        return service.createMessageType(messageTypeDTO)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Message Type by ID", description = "Retrieve a specific message type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message type retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageTypeCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message type not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MessageTypeCatalogDTO>> getMessageType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the message type", required = true)
            @PathVariable UUID typeId
    ) {
        return service.getMessageType(typeId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Message Type by Code", description = "Retrieve a specific message type by its code.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message type retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageTypeCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message type not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/code/{typeCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MessageTypeCatalogDTO>> getMessageTypeByCode(
            @Parameter(in = ParameterIn.PATH, description = "Code of the message type", required = true)
            @PathVariable String typeCode
    ) {
        return service.getMessageTypeByCode(typeCode)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Message Type", description = "Update a specific message type by its ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message type updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MessageTypeCatalogDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message type not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content
            )
    })
    @PutMapping(value = "/{typeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MessageTypeCatalogDTO>> updateMessageType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the message type", required = true)
            @PathVariable UUID typeId,
            @RequestBody MessageTypeCatalogDTO messageTypeDTO
    ) {
        return service.updateMessageType(typeId, messageTypeDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Message Type", description = "Delete a specific message type by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Message type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Message type not found")
    })
    @DeleteMapping("/{typeId}")
    public Mono<ResponseEntity<Void>> deleteMessageType(
            @Parameter(in = ParameterIn.PATH, description = "ID of the message type", required = true)
            @PathVariable UUID typeId
    ) {
        return service.deleteMessageType(typeId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
