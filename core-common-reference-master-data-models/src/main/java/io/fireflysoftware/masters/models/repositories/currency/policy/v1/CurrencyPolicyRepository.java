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

package io.fireflysoftware.masters.models.repositories.currency.policy.v1;

import io.fireflysoftware.masters.interfaces.dtos.currency.policy.v1.*;
import io.fireflysoftware.masters.interfaces.dtos.currency.v1.CurrencyDTO;
import io.fireflysoftware.masters.interfaces.enums.commons.v1.StatusEnum;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.*;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CurrencyPolicyRepository {
    private final DatabaseClient db;

    public CurrencyPolicyRepository(DatabaseClient db) {
        this.db = db;
    }

    public record Action(
            UUID actionId,
            UUID currencyId,
            UUID policyId,
            String kind,
            String canonical,
            String digest) {}

    public record Binding(UUID currencyId, String isoCode, long headVersion) {}

    public Mono<Long> now() {
        return db.sql(
                        "SELECT pg_catalog.floor(extract(epoch from"
                                + " pg_catalog.clock_timestamp())*1000)::bigint n")
                .map((r, m) -> r.get("n", Long.class))
                .one();
    }

    public Mono<Void> actionGate(UUID action) {
        return gate("MASTERS_CURRENCY_ACTION:" + action);
    }

    public Mono<Void> gates(UUID id, String iso) {
        var keys = new TreeSet<String>();
        keys.add("CURRENCY:" + id);
        keys.add("ISO:" + iso);
        return Flux.fromIterable(keys).concatMap(k -> gate("MASTERS_CURRENCY:" + k)).then();
    }

    private Mono<Void> gate(String key) {
        return org.springframework.transaction.reactive.TransactionSynchronizationManager
                .forCurrentTransaction()
                .filter(
                        org.springframework.transaction.reactive.TransactionSynchronizationManager
                                ::isActualTransactionActive)
                .switchIfEmpty(
                        Mono.error(
                                new IllegalStateException(
                                        "Native currency policy transaction required")))
                .then(
                        db.sql(
                                        "SELECT"
                                            + " pg_catalog.pg_advisory_xact_lock(pg_catalog.hashtextextended(:key,0))")
                                .bind("key", key)
                                .map((r, m) -> true)
                                .one())
                .then();
    }

    public Mono<CurrencyDTO> currency(UUID id, boolean lock) {
        return db.sql(
                        "SELECT * FROM public.currencies WHERE currency_id=:id"
                                + (lock ? " FOR NO KEY UPDATE" : ""))
                .bind("id", id)
                .map(
                        (r, m) ->
                                CurrencyDTO.builder()
                                        .currencyId(r.get("currency_id", UUID.class))
                                        .isoCode(r.get("iso_code", String.class))
                                        .currencyName(r.get("currency_name", String.class))
                                        .symbol(r.get("symbol", String.class))
                                        .decimalPrecision(r.get("decimal_precision", Integer.class))
                                        .isMajor(r.get("is_major", Boolean.class))
                                        .status(StatusEnum.valueOf(r.get("status", String.class)))
                                        .dateCreated(r.get("date_created", LocalDateTime.class))
                                        .dateUpdated(r.get("date_updated", LocalDateTime.class))
                                        .build())
                .one();
    }

    public Mono<Binding> binding(UUID id) {
        return db.sql(
                        "SELECT currency_id,iso_code,head_version FROM"
                                + " public.currency_policy_identity WHERE currency_id=:id")
                .bind("id", id)
                .map(
                        (r, m) ->
                                new Binding(
                                        r.get("currency_id", UUID.class),
                                        r.get("iso_code", String.class),
                                        r.get("head_version", Long.class)))
                .one();
    }

    public Mono<Void> ensureBinding(UUID id, String iso) {
        return db.sql(
                        "INSERT INTO"
                            + " public.currency_policy_identity(currency_id,iso_code,head_version)"
                            + " VALUES(:id,:iso,0) ON CONFLICT DO NOTHING")
                .bind("id", id)
                .bind("iso", iso)
                .fetch()
                .rowsUpdated()
                .then();
    }

    public Mono<Long> advance(UUID id, long expected) {
        return db.sql(
                        "UPDATE public.currency_policy_identity SET head_version=head_version+1"
                                + " WHERE currency_id=:id AND head_version=:head AND"
                                + " head_version<9223372036854775807")
                .bind("id", id)
                .bind("head", expected)
                .fetch()
                .rowsUpdated();
    }

    public Mono<Action> action(UUID id, UUID currency) {
        return db.sql(
                        "SELECT * FROM public.currency_policy_action WHERE action_id=:id AND"
                                + " currency_id=:currency")
                .bind("id", id)
                .bind("currency", currency)
                .map(
                        (r, m) ->
                                new Action(
                                        r.get("action_id", UUID.class),
                                        r.get("currency_id", UUID.class),
                                        r.get("policy_id", UUID.class),
                                        r.get("kind", String.class),
                                        r.get("canonical_intent", String.class),
                                        r.get("intent_digest", String.class)))
                .one();
    }

    public Mono<Long> claim(Action a) {
        return db.sql(
                        "INSERT INTO"
                            + " public.currency_policy_action(action_id,currency_id,policy_id,kind,canonical_intent,intent_digest)"
                            + " VALUES(:id,:currency,:policy,:kind,:canonical,:digest) ON CONFLICT"
                            + " DO NOTHING")
                .bind("id", a.actionId())
                .bind("currency", a.currencyId())
                .bind("policy", a.policyId())
                .bind("kind", a.kind())
                .bind("canonical", a.canonical())
                .bind("digest", a.digest())
                .fetch()
                .rowsUpdated();
    }

    public Mono<CurrencyPolicyView> find(UUID id, UUID currency) {
        return db.sql(
                        "SELECT p.*,c.lifecycle,c.state_version,pg_catalog.floor(extract(epoch from"
                                + " pg_catalog.clock_timestamp())*1000)::bigint observed_at FROM"
                                + " public.currency_execution_policy p JOIN"
                                + " public.currency_policy_control c ON c.policy_id=p.policy_id AND"
                                + " c.currency_id=p.currency_id WHERE p.policy_id=:id AND"
                                + " p.currency_id=:currency")
                .bind("id", id)
                .bind("currency", currency)
                .map(
                        (r, m) -> {
                            long observed = r.get("observed_at", Long.class),
                                    from = r.get("effective_from", Long.class);
                            Long to = r.get("effective_to", Long.class);
                            String lifecycle = r.get("lifecycle", String.class);
                            return new CurrencyPolicyView(
                                    new CurrencyPolicyReference(
                                            r.get("policy_id", UUID.class).toString(),
                                            r.get("currency_id", UUID.class).toString(),
                                            r.get("iso_code", String.class),
                                            r.get("version", Long.class),
                                            r.get("definition_digest", String.class),
                                            r.get("encoding_version", String.class)),
                                    r.get("canonical_encoding", String.class),
                                    r.get("source_encoding", String.class),
                                    r.get("source_digest", String.class),
                                    r.get("posted_scale", Integer.class),
                                    from,
                                    to,
                                    r.get("provenance_reference", String.class),
                                    r.get("publication_action_id", UUID.class).toString(),
                                    r.get("published_at", Long.class),
                                    lifecycle,
                                    r.get("state_version", Long.class),
                                    observed,
                                    lifecycle.equals("PUBLISHED")
                                            && observed >= from
                                            && (to == null || observed < to));
                        })
                .one();
    }

    public Mono<Void> insert(
            UUID policy,
            CurrencyPolicyPublishRequest r,
            long version,
            String encoding,
            String digest,
            String sourceEncoding,
            String sourceDigest,
            long now,
            String actor,
            String issuer) {
        var q =
                db.sql(
                                "INSERT INTO"
                                    + " public.currency_execution_policy(policy_id,currency_id,iso_code,version,encoding_version,canonical_encoding,definition_digest,source_encoding,source_digest,posted_scale,effective_from,effective_to,provenance_reference,publication_action_id,published_at,published_by,published_issuer)"
                                    + " VALUES(:id,:currency,:iso,:version,'MASTERS_CURRENCY_POLICY_V1',:encoding,:digest,:source,:sourceDigest,:scale,:from,:to,:provenance,:action,:now,:actor,:issuer)")
                        .bind("id", policy)
                        .bind("currency", UUID.fromString(r.currencyId()))
                        .bind("iso", r.isoCode())
                        .bind("version", version)
                        .bind("encoding", encoding)
                        .bind("digest", digest)
                        .bind("source", sourceEncoding)
                        .bind("sourceDigest", sourceDigest)
                        .bind("scale", r.postedScale())
                        .bind("from", r.effectiveFromEpochMillis())
                        .bind("provenance", r.provenanceReference())
                        .bind("action", UUID.fromString(r.actionId()))
                        .bind("now", now)
                        .bind("actor", actor)
                        .bind("issuer", issuer);
        q =
                r.effectiveToEpochMillis() == null
                        ? q.bindNull("to", Long.class)
                        : q.bind("to", r.effectiveToEpochMillis());
        return q.fetch()
                .rowsUpdated()
                .then(
                        db.sql(
                                        "INSERT INTO"
                                            + " public.currency_policy_control(policy_id,currency_id,lifecycle,state_version)"
                                            + " VALUES(:id,:currency,'PUBLISHED',0)")
                                .bind("id", policy)
                                .bind("currency", UUID.fromString(r.currencyId()))
                                .fetch()
                                .rowsUpdated())
                .then();
    }

    public Mono<Long> retire(
            UUID policy, UUID currency, UUID action, long now, String actor, String reason) {
        return db.sql(
                        "UPDATE public.currency_policy_control SET"
                            + " lifecycle='RETIRED',state_version=1,retired_action_id=:action,retired_at=:now,retired_by=:actor,reason=:reason"
                            + " WHERE policy_id=:id AND currency_id=:currency AND"
                            + " lifecycle='PUBLISHED' AND state_version=0")
                .bind("id", policy)
                .bind("currency", currency)
                .bind("action", action)
                .bind("now", now)
                .bind("actor", actor)
                .bind("reason", reason)
                .fetch()
                .rowsUpdated();
    }

    public Mono<Void> event(
            UUID policy,
            UUID currency,
            UUID action,
            String kind,
            long now,
            String actor,
            String issuer,
            String payload) {
        var event = UUID.randomUUID();
        return db.sql(
                        "INSERT INTO"
                            + " public.currency_policy_audit(event_id,currency_id,policy_id,action_id,kind,observed_at,actor,issuer)"
                            + " VALUES(:event,:currency,:policy,:action,:kind,:now,:actor,:issuer)")
                .bind("event", event)
                .bind("currency", currency)
                .bind("policy", policy)
                .bind("action", action)
                .bind("kind", kind)
                .bind("now", now)
                .bind("actor", actor)
                .bind("issuer", issuer)
                .fetch()
                .rowsUpdated()
                .then(
                        db.sql(
                                        "INSERT INTO"
                                            + " public.currency_policy_outbox(event_id,currency_id,policy_id,action_id,payload,observed_at)"
                                            + " VALUES(:event,:currency,:policy,:action,:payload,:now)")
                                .bind("event", event)
                                .bind("currency", currency)
                                .bind("policy", policy)
                                .bind("action", action)
                                .bind("payload", payload)
                                .bind("now", now)
                                .fetch()
                                .rowsUpdated())
                .then();
    }
}
