/*
 * This file is generated by jOOQ.
 */
package com.kuvaszuptime.kuvasz.jooq.tables;


import com.kuvaszuptime.kuvasz.jooq.Keys;
import com.kuvaszuptime.kuvasz.jooq.Kuvasz;
import com.kuvaszuptime.kuvasz.jooq.TextArrayToIntegrationIdArrayConverter;
import com.kuvaszuptime.kuvasz.jooq.enums.HttpMethod;
import com.kuvaszuptime.kuvasz.jooq.tables.LatencyLog.LatencyLogPath;
import com.kuvaszuptime.kuvasz.jooq.tables.SslEvent.SslEventPath;
import com.kuvaszuptime.kuvasz.jooq.tables.UptimeEvent.UptimeEventPath;
import com.kuvaszuptime.kuvasz.jooq.tables.records.MonitorRecord;
import com.kuvaszuptime.kuvasz.models.handlers.IntegrationID;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Monitor extends TableImpl<MonitorRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>kuvasz.monitor</code>
     */
    public static final Monitor MONITOR = new Monitor();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MonitorRecord> getRecordType() {
        return MonitorRecord.class;
    }

    /**
     * The column <code>kuvasz.monitor.id</code>.
     */
    public final TableField<MonitorRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>kuvasz.monitor.name</code>. Monitor's name
     */
    public final TableField<MonitorRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "Monitor's name");

    /**
     * The column <code>kuvasz.monitor.url</code>. URL to check
     */
    public final TableField<MonitorRecord, String> URL = createField(DSL.name("url"), SQLDataType.CLOB.nullable(false), this, "URL to check");

    /**
     * The column <code>kuvasz.monitor.uptime_check_interval</code>. Uptime
     * checking interval in seconds
     */
    public final TableField<MonitorRecord, Integer> UPTIME_CHECK_INTERVAL = createField(DSL.name("uptime_check_interval"), SQLDataType.INTEGER.nullable(false), this, "Uptime checking interval in seconds");

    /**
     * The column <code>kuvasz.monitor.enabled</code>. Flag to toggle the
     * monitor
     */
    public final TableField<MonitorRecord, Boolean> ENABLED = createField(DSL.name("enabled"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("true"), SQLDataType.BOOLEAN)), this, "Flag to toggle the monitor");

    /**
     * The column <code>kuvasz.monitor.created_at</code>.
     */
    public final TableField<MonitorRecord, OffsetDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "");

    /**
     * The column <code>kuvasz.monitor.updated_at</code>.
     */
    public final TableField<MonitorRecord, OffsetDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6), this, "");

    /**
     * The column <code>kuvasz.monitor.ssl_check_enabled</code>.
     */
    public final TableField<MonitorRecord, Boolean> SSL_CHECK_ENABLED = createField(DSL.name("ssl_check_enabled"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>kuvasz.monitor.latency_history_enabled</code>.
     */
    public final TableField<MonitorRecord, Boolean> LATENCY_HISTORY_ENABLED = createField(DSL.name("latency_history_enabled"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("true"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>kuvasz.monitor.follow_redirects</code>.
     */
    public final TableField<MonitorRecord, Boolean> FOLLOW_REDIRECTS = createField(DSL.name("follow_redirects"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("true"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>kuvasz.monitor.force_no_cache</code>.
     */
    public final TableField<MonitorRecord, Boolean> FORCE_NO_CACHE = createField(DSL.name("force_no_cache"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("true"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>kuvasz.monitor.request_method</code>.
     */
    public final TableField<MonitorRecord, HttpMethod> REQUEST_METHOD = createField(DSL.name("request_method"), SQLDataType.VARCHAR.nullable(false).defaultValue(DSL.field(DSL.raw("'GET'::kuvasz.http_method"), SQLDataType.VARCHAR)).asEnumDataType(HttpMethod.class), this, "");

    /**
     * The column <code>kuvasz.monitor.ssl_expiry_threshold</code>.
     */
    public final TableField<MonitorRecord, Integer> SSL_EXPIRY_THRESHOLD = createField(DSL.name("ssl_expiry_threshold"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field(DSL.raw("30"), SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>kuvasz.monitor.integrations</code>.
     */
    public final TableField<MonitorRecord, IntegrationID[]> INTEGRATIONS = createField(DSL.name("integrations"), SQLDataType.CLOB.array().nullable(false).defaultValue(DSL.field(DSL.raw("ARRAY[]::text[]"), SQLDataType.CLOB.array())), this, "", new TextArrayToIntegrationIdArrayConverter());

    private Monitor(Name alias, Table<MonitorRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Monitor(Name alias, Table<MonitorRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>kuvasz.monitor</code> table reference
     */
    public Monitor(String alias) {
        this(DSL.name(alias), MONITOR);
    }

    /**
     * Create an aliased <code>kuvasz.monitor</code> table reference
     */
    public Monitor(Name alias) {
        this(alias, MONITOR);
    }

    /**
     * Create a <code>kuvasz.monitor</code> table reference
     */
    public Monitor() {
        this(DSL.name("monitor"), null);
    }

    public <O extends Record> Monitor(Table<O> path, ForeignKey<O, MonitorRecord> childPath, InverseForeignKey<O, MonitorRecord> parentPath) {
        super(path, childPath, parentPath, MONITOR);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class MonitorPath extends Monitor implements Path<MonitorRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> MonitorPath(Table<O> path, ForeignKey<O, MonitorRecord> childPath, InverseForeignKey<O, MonitorRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private MonitorPath(Name alias, Table<MonitorRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public MonitorPath as(String alias) {
            return new MonitorPath(DSL.name(alias), this);
        }

        @Override
        public MonitorPath as(Name alias) {
            return new MonitorPath(alias, this);
        }

        @Override
        public MonitorPath as(Table<?> alias) {
            return new MonitorPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Kuvasz.KUVASZ;
    }

    @Override
    public Identity<MonitorRecord, Long> getIdentity() {
        return (Identity<MonitorRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<MonitorRecord> getPrimaryKey() {
        return Keys.MONITOR_PKEY;
    }

    @Override
    public List<UniqueKey<MonitorRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.UNIQUE_MONITOR_NAME);
    }

    private transient LatencyLogPath _latencyLog;

    /**
     * Get the implicit to-many join path to the <code>kuvasz.latency_log</code>
     * table
     */
    public LatencyLogPath latencyLog() {
        if (_latencyLog == null)
            _latencyLog = new LatencyLogPath(this, null, Keys.LATENCY_LOG__LATENCY_LOG_MONITOR_ID_FKEY.getInverseKey());

        return _latencyLog;
    }

    private transient SslEventPath _sslEvent;

    /**
     * Get the implicit to-many join path to the <code>kuvasz.ssl_event</code>
     * table
     */
    public SslEventPath sslEvent() {
        if (_sslEvent == null)
            _sslEvent = new SslEventPath(this, null, Keys.SSL_EVENT__SSL_EVENT_MONITOR_ID_FKEY.getInverseKey());

        return _sslEvent;
    }

    private transient UptimeEventPath _uptimeEvent;

    /**
     * Get the implicit to-many join path to the
     * <code>kuvasz.uptime_event</code> table
     */
    public UptimeEventPath uptimeEvent() {
        if (_uptimeEvent == null)
            _uptimeEvent = new UptimeEventPath(this, null, Keys.UPTIME_EVENT__UPTIME_EVENT_MONITOR_ID_FKEY.getInverseKey());

        return _uptimeEvent;
    }

    @Override
    public Monitor as(String alias) {
        return new Monitor(DSL.name(alias), this);
    }

    @Override
    public Monitor as(Name alias) {
        return new Monitor(alias, this);
    }

    @Override
    public Monitor as(Table<?> alias) {
        return new Monitor(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Monitor rename(String name) {
        return new Monitor(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Monitor rename(Name name) {
        return new Monitor(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Monitor rename(Table<?> name) {
        return new Monitor(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Monitor where(Condition condition) {
        return new Monitor(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Monitor where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Monitor where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Monitor where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Monitor where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Monitor where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Monitor where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Monitor where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Monitor whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Monitor whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
