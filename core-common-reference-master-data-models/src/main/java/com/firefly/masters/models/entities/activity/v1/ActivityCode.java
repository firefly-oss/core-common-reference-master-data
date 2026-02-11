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


package com.firefly.masters.models.entities.activity.v1;

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
@Table("activity_code")
public class ActivityCode {

    @Id
    @Column("activity_code_id")
    private UUID activityCodeId;

    @Column("country_id")
    private UUID countryId;  // References countries(country_id)

    @Column("code")
    private String code;

    @Column("classification_sys")
    private String classificationSys;

    @Column("description")
    private String description;

    @Column("parent_code_id")
    private UUID parentCodeId;  // Self-referencing to activity_code(activity_code_id)

    @Column("high_risk")
    private Boolean highRisk;

    @Column("risk_factors")
    private String riskFactors;

    @Column("status")
    private StatusEnum status;

    @Column("date_created")
    private LocalDateTime dateCreated;

    @Column("date_updated")
    private LocalDateTime dateUpdated;
}