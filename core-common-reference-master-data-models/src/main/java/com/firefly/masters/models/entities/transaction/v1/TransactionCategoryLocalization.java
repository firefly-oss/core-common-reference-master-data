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


package com.firefly.masters.models.entities.transaction.v1;

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

/**
 * Entity representing a localized version of a transaction category.
 * This stores translations of transaction category information for different languages/locales.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("transaction_category_localization")
public class TransactionCategoryLocalization {

    @Id
    @Column("localization_id")
    private UUID localizationId;

    @Column("category_id")
    private UUID categoryId;  // References transaction_category_catalog(category_id)

    @Column("locale_id")
    private UUID localeId;  // References language_locale(locale_id)

    @Column("category_name")
    private String categoryName;

    @Column("description")
    private String description;

    @Column("status")
    private StatusEnum status;

    @Column("date_created")
    private LocalDateTime dateCreated;

    @Column("date_updated")
    private LocalDateTime dateUpdated;
}
