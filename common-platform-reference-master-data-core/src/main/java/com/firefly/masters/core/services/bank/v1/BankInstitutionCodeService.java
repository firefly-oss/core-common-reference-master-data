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
import com.firefly.masters.interfaces.dtos.bank.v1.BankInstitutionCodeDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface BankInstitutionCodeService {

    /**
     * Retrieves a paginated list of bank institution codes based on the provided pagination request.
     *
     * @param paginationRequest the request object containing pagination details,
     *                          such as page number and size, for fetching the paginated list.
     * @return a {@link Mono} emitting a {@link PaginationResponse} that contains a list of
     *         {@link BankInstitutionCodeDTO} objects matching the criteria specified
     *         in the pagination request.
     */
    Mono<PaginationResponse<BankInstitutionCodeDTO>> listBankInstitutionCodes(PaginationRequest paginationRequest);

    /**
     * Creates a new bank institution code with the provided details.
     *
     * @param dto the details of the bank institution code to be created, including fields like bank name,
     *            SWIFT code, routing number, country ID, status, and SVG icon.
     * @return a {@link Mono} that emits the created {@link BankInstitutionCodeDTO} upon success.
     */
    Mono<BankInstitutionCodeDTO> createBankInstitutionCode(BankInstitutionCodeDTO dto);

    /**
     * Retrieves a bank institution code by its unique identifier.
     *
     * @param id the unique identifier of the bank institution code to retrieve
     * @return a {@code Mono} containing the {@code BankInstitutionCodeDTO} if found, or an empty {@code Mono} if not found
     */
    Mono<BankInstitutionCodeDTO> getBankInstitutionCode(UUID id);

    /**
     * Updates the bank institution code corresponding to the specified identifier.
     *
     * @param id the unique identifier of the bank institution code to be updated
     * @param dto the data transfer object containing the updated details for the bank institution code
     * @return a Mono containing the updated BankInstitutionCodeDTO
     */
    Mono<BankInstitutionCodeDTO> updateBankInstitutionCode(UUID id, BankInstitutionCodeDTO dto);

    /**
     * Deletes the bank institution code associated with the specified ID.
     *
     * @param id the unique identifier of the bank institution code to be deleted
     * @return a Mono that completes when the deletion operation is finished
     */
    Mono<Void> deleteBankInstitutionCode(UUID id);
}
