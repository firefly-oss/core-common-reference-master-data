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


package com.firefly.masters.core.utils;

import org.fireflyframework.core.queries.PaginationRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * A test utility class that extends PaginationRequest for testing purposes.
 * This class provides methods to get and set page and size values for tests.
 */
public class TestPaginationRequest extends PaginationRequest {
    
    private int page;
    private int size;
    
    public TestPaginationRequest() {
        this(0, 10);
    }
    
    public TestPaginationRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
