/*
 * This file is generated by jOOQ.
 */
package com.kuvaszuptime.kuvasz.jooq.tables.pojos;


import com.kuvaszuptime.kuvasz.jooq.enums.SslStatus;

import java.io.Serializable;
import java.time.OffsetDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class SslEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long monitorId;
    private SslStatus status;
    private String error;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime sslExpiryDate;

    public SslEvent() {}

    public SslEvent(SslEvent value) {
        this.id = value.id;
        this.monitorId = value.monitorId;
        this.status = value.status;
        this.error = value.error;
        this.startedAt = value.startedAt;
        this.endedAt = value.endedAt;
        this.updatedAt = value.updatedAt;
        this.sslExpiryDate = value.sslExpiryDate;
    }

    public SslEvent(
        Long id,
        Long monitorId,
        SslStatus status,
        String error,
        OffsetDateTime startedAt,
        OffsetDateTime endedAt,
        OffsetDateTime updatedAt,
        OffsetDateTime sslExpiryDate
    ) {
        this.id = id;
        this.monitorId = monitorId;
        this.status = status;
        this.error = error;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.updatedAt = updatedAt;
        this.sslExpiryDate = sslExpiryDate;
    }

    /**
     * Getter for <code>kuvasz.ssl_event.id</code>.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>kuvasz.ssl_event.id</code>.
     */
    public SslEvent setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>kuvasz.ssl_event.monitor_id</code>.
     */
    public Long getMonitorId() {
        return this.monitorId;
    }

    /**
     * Setter for <code>kuvasz.ssl_event.monitor_id</code>.
     */
    public SslEvent setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
        return this;
    }

    /**
     * Getter for <code>kuvasz.ssl_event.status</code>. Status of the event
     */
    public SslStatus getStatus() {
        return this.status;
    }

    /**
     * Setter for <code>kuvasz.ssl_event.status</code>. Status of the event
     */
    public SslEvent setStatus(SslStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Getter for <code>kuvasz.ssl_event.error</code>.
     */
    public String getError() {
        return this.error;
    }

    /**
     * Setter for <code>kuvasz.ssl_event.error</code>.
     */
    public SslEvent setError(String error) {
        this.error = error;
        return this;
    }

    /**
     * Getter for <code>kuvasz.ssl_event.started_at</code>. The current event
     * started at
     */
    public OffsetDateTime getStartedAt() {
        return this.startedAt;
    }

    /**
     * Setter for <code>kuvasz.ssl_event.started_at</code>. The current event
     * started at
     */
    public SslEvent setStartedAt(OffsetDateTime startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    /**
     * Getter for <code>kuvasz.ssl_event.ended_at</code>. The current event
     * ended at
     */
    public OffsetDateTime getEndedAt() {
        return this.endedAt;
    }

    /**
     * Setter for <code>kuvasz.ssl_event.ended_at</code>. The current event
     * ended at
     */
    public SslEvent setEndedAt(OffsetDateTime endedAt) {
        this.endedAt = endedAt;
        return this;
    }

    /**
     * Getter for <code>kuvasz.ssl_event.updated_at</code>.
     */
    public OffsetDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Setter for <code>kuvasz.ssl_event.updated_at</code>.
     */
    public SslEvent setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Getter for <code>kuvasz.ssl_event.ssl_expiry_date</code>.
     */
    public OffsetDateTime getSslExpiryDate() {
        return this.sslExpiryDate;
    }

    /**
     * Setter for <code>kuvasz.ssl_event.ssl_expiry_date</code>.
     */
    public SslEvent setSslExpiryDate(OffsetDateTime sslExpiryDate) {
        this.sslExpiryDate = sslExpiryDate;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SslEvent other = (SslEvent) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.monitorId == null) {
            if (other.monitorId != null)
                return false;
        }
        else if (!this.monitorId.equals(other.monitorId))
            return false;
        if (this.status == null) {
            if (other.status != null)
                return false;
        }
        else if (!this.status.equals(other.status))
            return false;
        if (this.error == null) {
            if (other.error != null)
                return false;
        }
        else if (!this.error.equals(other.error))
            return false;
        if (this.startedAt == null) {
            if (other.startedAt != null)
                return false;
        }
        else if (!this.startedAt.equals(other.startedAt))
            return false;
        if (this.endedAt == null) {
            if (other.endedAt != null)
                return false;
        }
        else if (!this.endedAt.equals(other.endedAt))
            return false;
        if (this.updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        }
        else if (!this.updatedAt.equals(other.updatedAt))
            return false;
        if (this.sslExpiryDate == null) {
            if (other.sslExpiryDate != null)
                return false;
        }
        else if (!this.sslExpiryDate.equals(other.sslExpiryDate))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.monitorId == null) ? 0 : this.monitorId.hashCode());
        result = prime * result + ((this.status == null) ? 0 : this.status.hashCode());
        result = prime * result + ((this.error == null) ? 0 : this.error.hashCode());
        result = prime * result + ((this.startedAt == null) ? 0 : this.startedAt.hashCode());
        result = prime * result + ((this.endedAt == null) ? 0 : this.endedAt.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        result = prime * result + ((this.sslExpiryDate == null) ? 0 : this.sslExpiryDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SslEvent (");

        sb.append(id);
        sb.append(", ").append(monitorId);
        sb.append(", ").append(status);
        sb.append(", ").append(error);
        sb.append(", ").append(startedAt);
        sb.append(", ").append(endedAt);
        sb.append(", ").append(updatedAt);
        sb.append(", ").append(sslExpiryDate);

        sb.append(")");
        return sb.toString();
    }
}
