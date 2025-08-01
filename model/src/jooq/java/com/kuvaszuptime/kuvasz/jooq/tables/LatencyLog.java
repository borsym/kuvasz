/*
 * This file is generated by jOOQ.
 */
package com.kuvaszuptime.kuvasz.jooq.tables;


import com.kuvaszuptime.kuvasz.jooq.Indexes;
import com.kuvaszuptime.kuvasz.jooq.Keys;
import com.kuvaszuptime.kuvasz.jooq.Kuvasz;
import com.kuvaszuptime.kuvasz.jooq.tables.Monitor.MonitorPath;
import com.kuvaszuptime.kuvasz.jooq.tables.records.LatencyLogRecord;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
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
public class LatencyLog extends TableImpl<LatencyLogRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>kuvasz.latency_log</code>
     */
    public static final LatencyLog LATENCY_LOG = new LatencyLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LatencyLogRecord> getRecordType() {
        return LatencyLogRecord.class;
    }

    /**
     * The column <code>kuvasz.latency_log.id</code>.
     */
    public final TableField<LatencyLogRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>kuvasz.latency_log.monitor_id</code>.
     */
    public final TableField<LatencyLogRecord, Long> MONITOR_ID = createField(DSL.name("monitor_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>kuvasz.latency_log.latency</code>. Lateny in ms
     */
    public final TableField<LatencyLogRecord, Integer> LATENCY = createField(DSL.name("latency"), SQLDataType.INTEGER.nullable(false), this, "Lateny in ms");

    /**
     * The column <code>kuvasz.latency_log.created_at</code>.
     */
    public final TableField<LatencyLogRecord, OffsetDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "");

    private LatencyLog(Name alias, Table<LatencyLogRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private LatencyLog(Name alias, Table<LatencyLogRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>kuvasz.latency_log</code> table reference
     */
    public LatencyLog(String alias) {
        this(DSL.name(alias), LATENCY_LOG);
    }

    /**
     * Create an aliased <code>kuvasz.latency_log</code> table reference
     */
    public LatencyLog(Name alias) {
        this(alias, LATENCY_LOG);
    }

    /**
     * Create a <code>kuvasz.latency_log</code> table reference
     */
    public LatencyLog() {
        this(DSL.name("latency_log"), null);
    }

    public <O extends Record> LatencyLog(Table<O> path, ForeignKey<O, LatencyLogRecord> childPath, InverseForeignKey<O, LatencyLogRecord> parentPath) {
        super(path, childPath, parentPath, LATENCY_LOG);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class LatencyLogPath extends LatencyLog implements Path<LatencyLogRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> LatencyLogPath(Table<O> path, ForeignKey<O, LatencyLogRecord> childPath, InverseForeignKey<O, LatencyLogRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private LatencyLogPath(Name alias, Table<LatencyLogRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public LatencyLogPath as(String alias) {
            return new LatencyLogPath(DSL.name(alias), this);
        }

        @Override
        public LatencyLogPath as(Name alias) {
            return new LatencyLogPath(alias, this);
        }

        @Override
        public LatencyLogPath as(Table<?> alias) {
            return new LatencyLogPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Kuvasz.KUVASZ;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.LATENCY_LOG_LATENCY_IDX, Indexes.LATENCY_LOG_MONITOR_IDX);
    }

    @Override
    public Identity<LatencyLogRecord, Long> getIdentity() {
        return (Identity<LatencyLogRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<LatencyLogRecord> getPrimaryKey() {
        return Keys.LATENCY_LOG_PKEY;
    }

    @Override
    public List<ForeignKey<LatencyLogRecord, ?>> getReferences() {
        return Arrays.asList(Keys.LATENCY_LOG__LATENCY_LOG_MONITOR_ID_FKEY);
    }

    private transient MonitorPath _monitor;

    /**
     * Get the implicit join path to the <code>kuvasz.monitor</code> table.
     */
    public MonitorPath monitor() {
        if (_monitor == null)
            _monitor = new MonitorPath(this, Keys.LATENCY_LOG__LATENCY_LOG_MONITOR_ID_FKEY, null);

        return _monitor;
    }

    @Override
    public LatencyLog as(String alias) {
        return new LatencyLog(DSL.name(alias), this);
    }

    @Override
    public LatencyLog as(Name alias) {
        return new LatencyLog(alias, this);
    }

    @Override
    public LatencyLog as(Table<?> alias) {
        return new LatencyLog(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public LatencyLog rename(String name) {
        return new LatencyLog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public LatencyLog rename(Name name) {
        return new LatencyLog(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public LatencyLog rename(Table<?> name) {
        return new LatencyLog(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public LatencyLog where(Condition condition) {
        return new LatencyLog(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public LatencyLog where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public LatencyLog where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public LatencyLog where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public LatencyLog where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public LatencyLog where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public LatencyLog where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public LatencyLog where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public LatencyLog whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public LatencyLog whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
