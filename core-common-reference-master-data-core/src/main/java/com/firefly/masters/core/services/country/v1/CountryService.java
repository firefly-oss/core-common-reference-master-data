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


package com.firefly.masters.core.services.country.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.masters.interfaces.dtos.country.v1.CountryDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface CountryService {
    /**
     * Retrieves a paginated list of countries based on the provided pagination request.
     *
     * @param filterRequest the pagination request containing page number, size, and sorting options
     * @return a Mono emitting a PaginationResponse containing a list of CountryDTO objects
     */
    Mono<PaginationResponse<CountryDTO>> listCountries(FilterRequest<CountryDTO> filterRequest);
    /**
     * Creates a new country based on the provided CountryDTO.
     *
     * @param countryDto the country data transfer object containing details of the country to be created
     * @return a Mono emitting the created CountryDTO object
     */
    Mono<CountryDTO> createCountry(CountryDTO countryDto);
    /**
     * Retrieves the details of a country by its unique identifier.
     *
     * @param countryId the unique identifier of the country to retrieve
     * @return a Mono emitting the CountryDTO containing details about the specified country, or an empty Mono if not found
     */
    Mono<CountryDTO> getCountry(UUID countryId);
    /**
     * Updates the details of an existing country by its unique identifier.
     *
     * @param countryId the unique identifier of the country to be updated
     * @param countryDto the data transfer object containing the updated country details
     * @return a Mono emitting the updated CountryDTO object if the update is successful
     */
    Mono<CountryDTO> updateCountry(UUID countryId, CountryDTO countryDto);
    /**
     * Deletes a country identified by its unique identifier.
     *
     * @param countryId the unique identifier of the country to delete
     * @return a Mono signaling completion of the delete operation
     */
    Mono<Void> deleteCountry(UUID countryId);
}