/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.masters.core.services.contractdocumenttype.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import io.fireflysoftware.masters.core.mappers.contractdocumenttype.v1.ContractDocumentTypeMapper;
import io.fireflysoftware.masters.interfaces.dtos.contractdocumenttype.v1.ContractDocumentTypeDTO;
import io.fireflysoftware.masters.models.entities.contractdocumenttype.v1.ContractDocumentType;
import io.fireflysoftware.masters.models.repositories.contractdocumenttype.v1.ContractDocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class ContractDocumentTypeServiceImpl implements ContractDocumentTypeService {

    @Autowired
    private ContractDocumentTypeRepository repository;

    @Autowired
    private ContractDocumentTypeMapper mapper;

    @Override
    public Mono<PaginationResponse<ContractDocumentTypeDTO>> listContractDocumentTypes(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<ContractDocumentTypeDTO> createContractDocumentType(ContractDocumentTypeDTO contractDocumentTypeDto) {
        ContractDocumentType entity = mapper.toEntity(contractDocumentTypeDto);
        entity.setDateCreated(LocalDateTime.now());
        entity.setDateUpdated(LocalDateTime.now());
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractDocumentTypeDTO> getContractDocumentType(UUID documentTypeId) {
        return repository.findById(documentTypeId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractDocumentTypeDTO> getContractDocumentTypeByCode(String documentCode) {
        return repository.findByDocumentCode(documentCode)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractDocumentTypeDTO> updateContractDocumentType(UUID documentTypeId, ContractDocumentTypeDTO contractDocumentTypeDto) {
        return repository.findById(documentTypeId)
                .flatMap(contractDocumentType -> {
                    ContractDocumentType updatedContractDocumentType = mapper.toEntity(contractDocumentTypeDto);
                    updatedContractDocumentType.setDocumentTypeId(documentTypeId); // Preserve actual id
                    updatedContractDocumentType.setDateCreated(contractDocumentType.getDateCreated()); // Preserve creation date
                    updatedContractDocumentType.setDateUpdated(LocalDateTime.now()); // Update the field
                    return repository.save(updatedContractDocumentType);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteContractDocumentType(UUID documentTypeId) {
        return repository.findById(documentTypeId)
                .flatMap(repository::delete);
    }
}
