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


package com.firefly.masters.core.services.legal.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.legal.v1.LegalFormDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface LegalFormService {

    /**
     * Retrieves a paginated list of legal forms based on the provided filter request.
     *
     * @param filterRequest an object containing filtering and pagination criteria for retrieving legal forms
     * @return a Mono emitting a PaginationResponse containing a list of LegalFormDTOs that match the filter criteria
     */
    Mono<PaginationResponse<LegalFormDTO>> listLegalForms(FilterRequest<LegalFormDTO> filterRequest);
    
    /**
     * Retrieves all legal forms for a specific country.
     *
     * @param countryId the unique identifier of the country to retrieve legal forms for
     * @return a Flux emitting LegalFormDTO objects for the specified country
     */
    Flux<LegalFormDTO> getLegalFormsByCountry(UUID countryId);
    
    /**
     * Creates a new legal form based on the provided LegalFormDTO.
     *
     * @param legalFormDto the legal form data transfer object containing details of the legal form to be created
     * @return a Mono emitting the created LegalFormDTO object
     */
    Mono<LegalFormDTO> createLegalForm(LegalFormDTO legalFormDto);
    
    /**
     * Retrieves the details of a legal form by its unique identifier.
     *
     * @param legalFormId the unique identifier of the legal form to retrieve
     * @return a Mono emitting the LegalFormDTO containing details about the specified legal form, or an empty Mono if not found
     */
    Mono<LegalFormDTO> getLegalForm(UUID legalFormId);
    
    /**
     * Updates the details of an existing legal form by its unique identifier.
     *
     * @param legalFormId the unique identifier of the legal form to be updated
     * @param legalFormDto the data transfer object containing the updated legal form details
     * @return a Mono emitting the updated LegalFormDTO object if the update is successful
     */
    Mono<LegalFormDTO> updateLegalForm(UUID legalFormId, LegalFormDTO legalFormDto);
    
    /**
     * Deletes a legal form identified by its unique identifier.
     *
     * @param legalFormId the unique identifier of the legal form to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteLegalForm(UUID legalFormId);
}