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


package com.firefly.masters.core.services.title.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.title.v1.TitleMasterMapper;
import com.firefly.masters.interfaces.dtos.title.v1.TitleMasterDTO;
import com.firefly.masters.models.entities.relationships.v1.RelationshipTypeMaster;
import com.firefly.masters.models.entities.title.v1.TitleMaster;
import com.firefly.masters.models.repositories.title.v1.TitleMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class TitleMasterServiceImpl implements TitleMasterService {

    @Autowired
    private TitleMasterRepository repository;

    @Autowired
    private TitleMasterMapper mapper;

    @Override
    public Mono<PaginationResponse<TitleMasterDTO>> listTitles(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<TitleMasterDTO> createTitle(TitleMasterDTO titleDto) {
        TitleMaster entity = mapper.toEntity(titleDto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TitleMasterDTO> getTitle(UUID titleId) {
        return repository.findById(titleId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TitleMasterDTO> updateTitle(UUID titleId, TitleMasterDTO titleDto) {
        return repository.findById(titleId)
                .flatMap(title -> {
                    TitleMaster titleMaster = mapper.toEntity(titleDto);
                    titleMaster.setTitleId(titleId); // Preserve actual id
                    titleMaster.setDateUpdated(LocalDateTime.now()); // Update the field
                    return repository.save(titleMaster);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteTitle(UUID titleId) {
        return repository.findById(titleId)
                .flatMap(repository::delete);
    }
}
