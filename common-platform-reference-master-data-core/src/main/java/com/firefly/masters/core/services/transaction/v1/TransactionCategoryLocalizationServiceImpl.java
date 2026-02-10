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


package com.firefly.masters.core.services.transaction.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.transaction.v1.TransactionCategoryLocalizationMapper;
import com.firefly.masters.interfaces.dtos.transaction.v1.TransactionCategoryLocalizationDTO;
import com.firefly.masters.models.entities.transaction.v1.TransactionCategoryLocalization;
import com.firefly.masters.models.repositories.transaction.v1.TransactionCategoryLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the TransactionCategoryLocalizationService interface.
 */
@Service
@Transactional
public class TransactionCategoryLocalizationServiceImpl implements TransactionCategoryLocalizationService {

    @Autowired
    private TransactionCategoryLocalizationRepository repository;

    @Autowired
    private TransactionCategoryLocalizationMapper mapper;

    @Override
    public Flux<TransactionCategoryLocalizationDTO> getLocalizationsByCategoryId(UUID categoryId) {
        return repository.findByCategoryId(categoryId)
                .map(mapper::toDTO)
                .switchIfEmpty(Flux.error(new RuntimeException("No localizations found for category ID: " + categoryId)));
    }

    @Override
    public Mono<PaginationResponse<TransactionCategoryLocalizationDTO>> listLocalizationsByCategoryId(UUID categoryId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findByCategoryId(categoryId, pageable),
                () -> repository.countByCategoryId(categoryId)
        );
    }

    @Override
    public Mono<TransactionCategoryLocalizationDTO> createTransactionCategoryLocalization(TransactionCategoryLocalizationDTO localizationDTO) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        localizationDTO.setDateCreated(now);
        localizationDTO.setDateUpdated(now);

        return Mono.just(localizationDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating transaction category localization: " + e.getMessage(), e)));
    }

    @Override
    public Mono<TransactionCategoryLocalizationDTO> getTransactionCategoryLocalization(UUID localizationId) {
        return repository.findById(localizationId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Transaction category localization not found with ID: " + localizationId)));
    }

    @Override
    public Mono<TransactionCategoryLocalizationDTO> getTransactionCategoryLocalizationByCategoryAndLocale(UUID categoryId, UUID localeId) {
        return repository.findByCategoryIdAndLocaleId(categoryId, localeId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Transaction category localization not found with category ID: " + categoryId + " and locale ID: " + localeId)));
    }

    @Override
    public Mono<TransactionCategoryLocalizationDTO> updateTransactionCategoryLocalization(UUID localizationId, TransactionCategoryLocalizationDTO localizationDTO) {
        return repository.findById(localizationId)
                .flatMap(existingLocalization -> {
                    TransactionCategoryLocalization updatedLocalization = mapper.toEntity(localizationDTO);
                    updatedLocalization.setLocalizationId(localizationId);
                    updatedLocalization.setDateCreated(existingLocalization.getDateCreated());
                    updatedLocalization.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedLocalization);
                })
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Transaction category localization not found with ID: " + localizationId)));
    }

    @Override
    public Mono<Void> deleteTransactionCategoryLocalization(UUID localizationId) {
        return repository.findById(localizationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Transaction category localization not found with ID: " + localizationId)))
                .flatMap(repository::delete);
    }
}
