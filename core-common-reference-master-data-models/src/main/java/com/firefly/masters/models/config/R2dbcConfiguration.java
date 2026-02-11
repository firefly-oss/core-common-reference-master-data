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


package com.firefly.masters.models.config;

import com.firefly.masters.interfaces.enums.commons.v1.StatusEnum;
import com.firefly.masters.interfaces.enums.country.v1.RegionEnum;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.client.SSLMode;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Value("${DB_HOST:localhost}")
    private String host;

    @Value("${DB_PORT:5432}")
    private int port;

    @Value("${DB_NAME:postgres}")
    private String database;

    @Value("${DB_USERNAME:postgres}")
    private String username;

    @Value("${DB_PASSWORD:postgres}")
    private String password;

    @Value("${DB_SSL_MODE:disable}")
    private String sslMode;

    @WritingConverter
    static class StatusEnumConverter implements Converter<StatusEnum, StatusEnum> {
        @Override
        public StatusEnum convert(StatusEnum source) {
            return source;
        }
    }

    @WritingConverter
    static class RegionEnumConverter implements Converter<RegionEnum, RegionEnum> {
        @Override
        public RegionEnum convert(RegionEnum source) {
            return source;
        }
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(new StatusEnumConverter());
        converters.add(new RegionEnumConverter());
        return converters;
    }

    @Bean
    @Primary
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(host)
                .port(port)
                .username(username)
                .password(password)
                .database(database)
                .sslMode(SSLMode.valueOf(sslMode.toUpperCase()))
                .codecRegistrar(EnumCodec.builder()
                    .withEnum("status_enum", StatusEnum.class)
                    .withEnum("region_enum", RegionEnum.class)
                    .build())
                .build()
        );
    }
}
