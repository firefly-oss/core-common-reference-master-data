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


package com.firefly.masters.models.entities.lookup.v1;

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
@Table("lookup_domain")
public class LookupDomain {

    @Id
    @Column("domain_id")
    private UUID domainId;

    @Column("domain_code")
    private String domainCode;

    @Column("domain_name")
    private String domainName;

    @Column("domain_desc")
    private String domainDesc;

    @Column("parent_domain_id")
    private UUID parentDomainId;  // Self-referencing to lookup_domain(domain_id)

    @Column("multiselect_allowed")
    private Boolean multiselectAllowed;

    @Column("hierarchy_allowed")
    private Boolean hierarchyAllowed;

    @Column("tenant_overridable")
    private Boolean tenantOverridable;

    @Column("extra_json")
    private String extraJson;  // Stored as JSONB in the database

    @Column("tenant_id")
    private UUID tenantId;

    @Column("status")
    private StatusEnum status;

    @Column("date_created")
    private LocalDateTime dateCreated;

    @Column("date_updated")
    private LocalDateTime dateUpdated;
}