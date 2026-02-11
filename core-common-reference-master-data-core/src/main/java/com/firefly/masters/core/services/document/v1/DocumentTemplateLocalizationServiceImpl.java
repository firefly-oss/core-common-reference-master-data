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

import com.firefly.masters.core.mappers.document.v1.DocumentTemplateLocalizationMapper;
import com.firefly.masters.interfaces.dtos.document.v1.DocumentTemplateLocalizationDTO;
import com.firefly.masters.models.entities.document.v1.DocumentTemplateLocalization;
import com.firefly.masters.models.repositories.document.v1.DocumentTemplateLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the DocumentTemplateLocalizationService interface.
 */
@Service
@Transactional
public class DocumentTemplateLocalizationServiceImpl implements DocumentTemplateLocalizationService {

    @Autowired
    private DocumentTemplateLocalizationRepository repository;

    @Autowired
    private DocumentTemplateLocalizationMapper mapper;

    @Override
    public Flux<DocumentTemplateLocalizationDTO> getLocalizationsByTemplateId(UUID templateId) {
        return repository.findByTemplateId(templateId)
                .map(mapper::toDTO)
                .switchIfEmpty(Flux.error(new RuntimeException("No localizations found for template ID: " + templateId)));
    }

    @Override
    public Flux<DocumentTemplateLocalizationDTO> getLocalizationsByLocaleId(UUID localeId) {
        return repository.findByLocaleId(localeId)
                .map(mapper::toDTO)
                .switchIfEmpty(Flux.error(new RuntimeException("No localizations found for locale ID: " + localeId)));
    }

    @Override
    public Mono<DocumentTemplateLocalizationDTO> createDocumentTemplateLocalization(DocumentTemplateLocalizationDTO localizationDTO) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        localizationDTO.setDateCreated(now);
        localizationDTO.setDateUpdated(now);

        return Mono.just(localizationDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating document template localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<DocumentTemplateLocalizationDTO> getDocumentTemplateLocalization(UUID localizationId) {
        return repository.findById(localizationId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Document template localization not found with ID: " + localizationId)));
    }

    @Override
    public Mono<DocumentTemplateLocalizationDTO> getDocumentTemplateLocalizationByTemplateAndLocale(UUID templateId, UUID localeId) {
        return repository.findByTemplateIdAndLocaleId(templateId, localeId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Document template localization not found with template ID: " + templateId + " and locale ID: " + localeId)));
    }

    @Override
    public Mono<DocumentTemplateLocalizationDTO> updateDocumentTemplateLocalization(UUID localizationId, DocumentTemplateLocalizationDTO localizationDTO) {
        return repository.findById(localizationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Document template localization not found with ID: " + localizationId)))
                .flatMap(existingEntity -> {
                    DocumentTemplateLocalization updatedEntity = mapper.toEntity(localizationDTO);
                    updatedEntity.setLocalizationId(localizationId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    updatedEntity.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error updating document template localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteDocumentTemplateLocalization(UUID localizationId) {
        return repository.findById(localizationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Document template localization not found with ID: " + localizationId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting document template localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteLocalizationsByTemplateId(UUID templateId) {
        return repository.deleteByTemplateId(templateId)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting localizations for template ID: " + templateId, e)));
    }
}
