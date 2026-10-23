/*
 * Copyright 2026 Firefly Software Foundation
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

package io.fireflysoftware.masters.core.services.currency.policy.v1;

import org.fireflyframework.security.api.domain.SecurityPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class CurrencyPolicyDatabaseContext {
    private final DatabaseClient db;

    public CurrencyPolicyDatabaseContext(DatabaseClient db) {
        this.db = db;
    }

    public Mono<Void> establish(SecurityPrincipal principal, UUID currency, String purpose) {
        return Mono.defer(
                () -> {
                    CurrencyPolicyAuthority.requireCurrent(principal);
                    if (currency == null || !Set.of("READ", "PUBLISH", "RETIRE").contains(purpose))
                        return Mono.error(
                                new IllegalStateException(
                                        "Server currency policy context required"));
                    return org.springframework.transaction.reactive
                            .TransactionSynchronizationManager.forCurrentTransaction()
                            .filter(
                                    org.springframework.transaction.reactive
                                                    .TransactionSynchronizationManager
                                            ::isActualTransactionActive)
                            .switchIfEmpty(
                                    Mono.error(
                                            new IllegalStateException(
                                                    "Native currency transaction required")))
                            .then(
                                    db.sql(
                                                    """
                                                    SELECT NOT r.rolsuper AND NOT r.rolbypassrls
                                                     AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
                                                     AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_class c JOIN pg_catalog.pg_namespace n ON n.oid=c.relnamespace
                                                      WHERE n.nspname='public' AND c.relname IN('currency_policy_identity','currency_execution_policy','currency_policy_action','currency_policy_control','currency_policy_audit','currency_policy_outbox')
                                                      AND (pg_catalog.pg_get_userbyid(c.relowner)=current_user OR NOT c.relrowsecurity OR NOT c.relforcerowsecurity))
                                                     AND (SELECT count(*) FROM pg_catalog.pg_class c JOIN pg_catalog.pg_namespace n ON n.oid=c.relnamespace WHERE n.nspname='public'
                                                      AND c.relname IN('currency_policy_identity','currency_execution_policy','currency_policy_action','currency_policy_control','currency_policy_audit','currency_policy_outbox'))=6
                                                     AND EXISTS(SELECT 1 FROM pg_catalog.pg_proc p JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace JOIN pg_catalog.pg_roles o ON o.oid=p.proowner
                                                      WHERE n.nspname='public' AND p.proname='guard_currency_catalog_identity' AND p.prosecdef AND NOT o.rolsuper AND NOT o.rolbypassrls AND NOT o.rolcanlogin
                                                      AND NOT pg_catalog.pg_has_role(current_user,o.oid,'MEMBER') AND p.proconfig @> ARRAY['search_path=pg_catalog']) AS allowed
                                                    FROM pg_catalog.pg_roles r WHERE r.rolname=current_user
                                                    """)
                                            .map(
                                                    (row, m) ->
                                                            Boolean.TRUE.equals(
                                                                    row.get(
                                                                            "allowed",
                                                                            Boolean.class)))
                                            .one()
                                            .filter(Boolean::booleanValue)
                                            .switchIfEmpty(
                                                    Mono.error(
                                                            new ResponseStatusException(
                                                                    HttpStatus.SERVICE_UNAVAILABLE,
                                                                    "Nonprivileged scoped currency"
                                                                            + " policy runtime"
                                                                            + " required"))))
                            .then(
                                    db.sql(
                                                    "SELECT"
                                                        + " pg_catalog.set_config('firefly.currency.currency_id',:currency,true),pg_catalog.set_config('firefly.currency.purpose',:purpose,true)")
                                            .bind("currency", currency.toString())
                                            .bind("purpose", purpose)
                                            .fetch()
                                            .rowsUpdated())
                            .then();
                });
    }
}
