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
import com.firefly.masters.core.mappers.transaction.v1.TransactionCategoryCatalogMapper;
import com.firefly.masters.interfaces.dtos.transaction.v1.TransactionCategoryCatalogDTO;
import com.firefly.masters.models.entities.transaction.v1.TransactionCategoryCatalog;
import com.firefly.masters.models.repositories.transaction.v1.TransactionCategoryCatalogRepository;
import com.firefly.masters.models.repositories.transaction.v1.TransactionCategoryLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the TransactionCategoryCatalogService interface.
 */
@Service
@Transactional
public class TransactionCategoryCatalogServiceImpl implements TransactionCategoryCatalogService {

    @Autowired
    private TransactionCategoryCatalogRepository repository;

    @Autowired
    private TransactionCategoryLocalizationRepository localizationRepository;

    @Autowired
    private TransactionCategoryCatalogMapper mapper;

    @Override
    public Mono<PaginationResponse<TransactionCategoryCatalogDTO>> listTransactionCategories(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<PaginationResponse<TransactionCategoryCatalogDTO>> listRootTransactionCategories(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findRootCategories(pageable),
                () -> repository.countRootCategories()
        );
    }

    @Override
    public Mono<PaginationResponse<TransactionCategoryCatalogDTO>> listChildTransactionCategories(UUID parentCategoryId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findByParentCategoryId(parentCategoryId, pageable),
                () -> repository.countByParentCategoryId(parentCategoryId)
        );
    }

    @Override
    public Mono<TransactionCategoryCatalogDTO> createTransactionCategory(TransactionCategoryCatalogDTO transactionCategoryDTO) {
        // Set audit fields
        LocalDateTime now = LocalDateTime.now();
        transactionCategoryDTO.setDateCreated(now);
        transactionCategoryDTO.setDateUpdated(now);

        TransactionCategoryCatalog entity = mapper.toEntity(transactionCategoryDTO);
        return repository.save(entity)
                .flatMap(this::enrichWithParentCategory);
    }

    @Override
    public Mono<TransactionCategoryCatalogDTO> getTransactionCategory(UUID categoryId) {
        return repository.findById(categoryId)
                .flatMap(this::enrichWithParentCategory);
    }

    @Override
    public Mono<TransactionCategoryCatalogDTO> getTransactionCategoryByCode(String categoryCode) {
        return repository.findByCategoryCode(categoryCode)
                .flatMap(this::enrichWithParentCategory);
    }

    @Override
    public Mono<TransactionCategoryCatalogDTO> updateTransactionCategory(UUID categoryId, TransactionCategoryCatalogDTO transactionCategoryDTO) {
        return repository.findById(categoryId)
                .flatMap(existingCategory -> {
                    TransactionCategoryCatalog updatedCategory = mapper.toEntity(transactionCategoryDTO);
                    updatedCategory.setCategoryId(categoryId);
                    updatedCategory.setDateCreated(existingCategory.getDateCreated());
                    updatedCategory.setDateUpdated(LocalDateTime.now());
                    return repository.save(updatedCategory);
                })
                .flatMap(this::enrichWithParentCategory);
    }

    @Override
    public Mono<Void> deleteTransactionCategory(UUID categoryId) {
        return repository.findById(categoryId)
                .switchIfEmpty(Mono.error(new RuntimeException("Transaction category not found with ID: " + categoryId)))
                .flatMap(entity -> localizationRepository.deleteByCategoryId(categoryId)
                        .then(repository.deleteById(categoryId)));
    }

    /**
     * Enriches a transaction category DTO with its parent category information.
     *
     * @param entity the transaction category entity to enrich
     * @return the enriched transaction category DTO
     */
    private Mono<TransactionCategoryCatalogDTO> enrichWithParentCategory(TransactionCategoryCatalog entity) {
        TransactionCategoryCatalogDTO dto = mapper.toDTO(entity);

        if (entity.getParentCategoryId() != null) {
            return repository.findById(entity.getParentCategoryId())
                    .map(mapper::toDTO)
                    .doOnNext(dto::setParentCategory)
                    .thenReturn(dto);
        }

        return Mono.just(dto);
    }
}
