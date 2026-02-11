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


package com.firefly.masters.models.entities.bank.v1;

import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("bank_institution_codes")
public class BankInstitutionCode {

    @Id
    @Column("institution_id")
    private UUID institutionId;

    @Column("bank_name")
    private String bankName;

    @Column("swift_code")
    private String swiftCode;

    @Column("routing_number")
    private String routingNumber;

    @Column("iban_prefix")
    private String ibanPrefix;

    @Column("country_id")
    private UUID countryId;  // References countries(country_id)

    @Column("institution_type_lkp_id")
    private UUID institutionTypeLkpId;

    @Column("status")
    private StatusEnum status;

    @Column("svg_icon")
    private String svgIcon;

    @Column("date_created")
    private LocalDateTime dateCreated;

    @Column("date_updated")
    private LocalDateTime dateUpdated;
}
