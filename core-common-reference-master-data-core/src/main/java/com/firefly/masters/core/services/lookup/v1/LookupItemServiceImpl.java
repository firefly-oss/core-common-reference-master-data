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


package com.firefly.masters.core.services.lookup.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.lookup.v1.LookupItemMapper;
import com.firefly.masters.interfaces.dtos.lookup.v1.LookupItemDTO;
import com.firefly.masters.models.entities.lookup.v1.LookupItem;
import com.firefly.masters.models.repositories.lookup.v1.LookupItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class LookupItemServiceImpl implements LookupItemService {

    @Autowired
    private LookupItemRepository repository;

    @Autowired
    private LookupItemMapper mapper;

    @Override
    public Mono<PaginationResponse<LookupItemDTO>> listItems(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Flux<LookupItemDTO> getItemsByDomain(UUID domainId) {
        return repository.findByDomainId(domainId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LookupItemDTO> createItem(LookupItemDTO itemDto) {
        LookupItem item = mapper.toEntity(itemDto);
        return repository.save(item)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LookupItemDTO> getItem(UUID itemId) {
        return repository.findById(itemId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LookupItemDTO> updateItem(UUID itemId, LookupItemDTO itemDto) {
        return repository.findById(itemId)
                .flatMap(foundItem -> {
                    LookupItem updatedItem = mapper.toEntity(itemDto);
                    updatedItem.setItemId(foundItem.getItemId());
                    return repository.save(updatedItem);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteItem(UUID itemId) {
        return repository.findById(itemId)
                .flatMap(repository::delete);
    }
}