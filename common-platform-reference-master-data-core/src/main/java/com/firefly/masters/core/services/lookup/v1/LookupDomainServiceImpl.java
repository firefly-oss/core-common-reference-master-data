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
import com.firefly.masters.core.mappers.lookup.v1.LookupDomainMapper;
import com.firefly.masters.interfaces.dtos.lookup.v1.LookupDomainDTO;
import com.firefly.masters.models.entities.lookup.v1.LookupDomain;
import com.firefly.masters.models.repositories.lookup.v1.LookupDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class LookupDomainServiceImpl implements LookupDomainService {

    @Autowired
    private LookupDomainRepository repository;

    @Autowired
    private LookupDomainMapper mapper;

    @Override
    public Mono<PaginationResponse<LookupDomainDTO>> listDomains(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<LookupDomainDTO> createDomain(LookupDomainDTO domainDto) {
        LookupDomain domain = mapper.toEntity(domainDto);
        return repository.save(domain)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LookupDomainDTO> getDomain(UUID domainId) {
        return repository.findById(domainId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LookupDomainDTO> updateDomain(UUID domainId, LookupDomainDTO domainDto) {
        return repository.findById(domainId)
                .flatMap(foundDomain -> {
                    LookupDomain updatedDomain = mapper.toEntity(domainDto);
                    updatedDomain.setDomainId(foundDomain.getDomainId());
                    return repository.save(updatedDomain);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDomain(UUID domainId) {
        return repository.findById(domainId)
                .flatMap(repository::delete);
    }
}