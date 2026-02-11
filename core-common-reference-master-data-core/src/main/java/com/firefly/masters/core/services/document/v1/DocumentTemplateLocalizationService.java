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


package com.firefly.masters.core.services.document.v1;

import com.firefly.masters.interfaces.dtos.document.v1.DocumentTemplateLocalizationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing document template localization operations.
 */
public interface DocumentTemplateLocalizationService {

    /**
     * Get all localizations for a specific template.
     *
     * @param templateId the ID of the document template
     * @return a Flux of document template localization DTOs
     */
    Flux<DocumentTemplateLocalizationDTO> getLocalizationsByTemplateId(UUID templateId);

    /**
     * Get all localizations for a specific locale.
     *
     * @param localeId the ID of the language locale
     * @return a Flux of document template localization DTOs
     */
    Flux<DocumentTemplateLocalizationDTO> getLocalizationsByLocaleId(UUID localeId);

    /**
     * Create a new document template localization.
     *
     * @param localizationDTO the document template localization data
     * @return the created document template localization DTO
     */
    Mono<DocumentTemplateLocalizationDTO> createDocumentTemplateLocalization(DocumentTemplateLocalizationDTO localizationDTO);

    /**
     * Get a document template localization by ID.
     *
     * @param localizationId the ID of the document template localization
     * @return the document template localization DTO
     */
    Mono<DocumentTemplateLocalizationDTO> getDocumentTemplateLocalization(UUID localizationId);

    /**
     * Get a document template localization by template ID and locale ID.
     *
     * @param templateId the ID of the document template
     * @param localeId the ID of the language locale
     * @return the document template localization DTO
     */
    Mono<DocumentTemplateLocalizationDTO> getDocumentTemplateLocalizationByTemplateAndLocale(UUID templateId, UUID localeId);

    /**
     * Update a document template localization.
     *
     * @param localizationId the ID of the document template localization to update
     * @param localizationDTO the updated document template localization data
     * @return the updated document template localization DTO
     */
    Mono<DocumentTemplateLocalizationDTO> updateDocumentTemplateLocalization(UUID localizationId, DocumentTemplateLocalizationDTO localizationDTO);

    /**
     * Delete a document template localization.
     *
     * @param localizationId the ID of the document template localization to delete
     * @return a Mono of Void
     */
    Mono<Void> deleteDocumentTemplateLocalization(UUID localizationId);

    /**
     * Delete all localizations for a specific template.
     *
     * @param templateId the ID of the document template
     * @return a Mono of Void
     */
    Mono<Void> deleteLocalizationsByTemplateId(UUID templateId);
}
