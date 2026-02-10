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


package com.firefly.masters.core.services.bank.v1;

import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.core.queries.PaginationUtils;
import com.firefly.masters.core.mappers.bank.v1.BankInstitutionCodeMapper;
import com.firefly.masters.interfaces.dtos.bank.v1.BankInstitutionCodeDTO;
import com.firefly.masters.models.entities.bank.v1.BankInstitutionCode;
import com.firefly.masters.models.repositories.bank.v1.BankInstitutionCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class BankInstitutionCodeServiceImpl implements BankInstitutionCodeService {

    @Autowired
    private BankInstitutionCodeRepository repository;

    @Autowired
    private BankInstitutionCodeMapper mapper;

    @Override
    public Mono<PaginationResponse<BankInstitutionCodeDTO>> listBankInstitutionCodes(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDTO,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        );
    }

    @Override
    public Mono<BankInstitutionCodeDTO> createBankInstitutionCode(BankInstitutionCodeDTO dto) {
        BankInstitutionCode entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankInstitutionCodeDTO> getBankInstitutionCode(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<BankInstitutionCodeDTO> updateBankInstitutionCode(UUID id, BankInstitutionCodeDTO dto) {
        return repository.findById(id)
                .flatMap(existingEntity -> {
                    BankInstitutionCode updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setInstitutionId(existingEntity.getInstitutionId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBankInstitutionCode(UUID id) {
        return repository.deleteById(id);
    }
}
