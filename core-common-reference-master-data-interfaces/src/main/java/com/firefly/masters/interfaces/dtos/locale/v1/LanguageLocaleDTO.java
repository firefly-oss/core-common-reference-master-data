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


package com.firefly.masters.interfaces.dtos.locale.v1;

import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguageLocaleDTO {

    private UUID localeId;
    private String languageCode;
    private String countryCode;
    private String localeCode;
    private String languageName;
    private String nativeName;
    private String regionName;
    private Boolean rtl;
    private Integer sortOrder;
    private StatusEnum status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
