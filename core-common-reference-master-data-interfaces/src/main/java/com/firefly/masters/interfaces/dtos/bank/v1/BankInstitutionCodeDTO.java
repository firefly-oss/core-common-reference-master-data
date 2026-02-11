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


package com.firefly.masters.interfaces.dtos.bank.v1;

import org.fireflyframework.annotations.ValidBic;
import org.fireflyframework.utils.annotations.FilterableId;
import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankInstitutionCodeDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID institutionId;

    @NotBlank(message = "Bank name is required")
    @Size(max = 200, message = "Bank name must not exceed 200 characters")
    private String bankName;

    @ValidBic
    @NotBlank(message = "SWIFT code is required")
    private String swiftCode;

    @Pattern(regexp = "^[0-9]{9}$|^[0-9]{8}$", message = "Routing number must be 8 or 9 digits")
    private String routingNumber;

    @Size(max = 10, message = "IBAN prefix must not exceed 10 characters")
    private String ibanPrefix;

    @FilterableId
    @NotNull(message = "Country ID is required")
    private UUID countryId;

    private UUID institutionTypeLkpId;

    @NotNull(message = "Status is required")
    private StatusEnum status;

    @Size(max = 10000, message = "SVG icon data must not exceed 10000 characters")
    private String svgIcon;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
