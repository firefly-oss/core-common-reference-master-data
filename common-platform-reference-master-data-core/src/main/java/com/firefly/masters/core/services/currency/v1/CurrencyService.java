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


package com.firefly.masters.core.services.currency.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.currency.v1.CurrencyDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface CurrencyService {

    /**
     * Retrieves a paginated list of currencies based on the provided filter criteria.
     *
     * @param filterRequest the filter and pagination criteria used to fetch the list of currencies
     * @return a Mono emitting a PaginationResponse containing a list of CurrencyDTO objects and pagination details
     */
    Mono<PaginationResponse<CurrencyDTO>> listCurrencies(FilterRequest<CurrencyDTO> filterRequest);

    /**
     * Creates a new currency entry based on the provided CurrencyDTO.
     *
     * @param currencyDto the object containing the details of the currency to be created
     * @return a Mono that emits the created CurrencyDTO
     */
    Mono<CurrencyDTO> createCurrency(CurrencyDTO currencyDto);

    /**
     * Retrieves currency information based on the provided currency ID.
     *
     * @param currencyId the unique identifier of the currency to retrieve
     * @return a Mono containing the retrieved CurrencyDTO object
     */
    Mono<CurrencyDTO> getCurrency(UUID currencyId);

    /**
     * Updates the details of an existing currency identified by the given currency ID.
     *
     * @param currencyId the unique identifier of the currency to be updated
     * @param currencyDto the updated information for the currency
     * @return a Mono emitting the updated CurrencyDTO after the operation is completed
     */
    Mono<CurrencyDTO> updateCurrency(UUID currencyId, CurrencyDTO currencyDto);

    /**
     * Deletes a currency based on the provided currency ID.
     *
     * @param currencyId the unique identifier of the currency to be deleted
     * @return a Mono indicating the completion of the operation
     */
    Mono<Void> deleteCurrency(UUID currencyId);
}