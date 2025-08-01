package com.kuvaszuptime.kuvasz.mocks

import com.kuvaszuptime.kuvasz.jooq.enums.HttpMethod
import com.kuvaszuptime.kuvasz.jooq.enums.SslStatus
import com.kuvaszuptime.kuvasz.jooq.enums.UptimeStatus
import com.kuvaszuptime.kuvasz.jooq.tables.SslEvent.SSL_EVENT
import com.kuvaszuptime.kuvasz.jooq.tables.UptimeEvent.UPTIME_EVENT
import com.kuvaszuptime.kuvasz.jooq.tables.records.MonitorRecord
import com.kuvaszuptime.kuvasz.jooq.tables.records.SslEventRecord
import com.kuvaszuptime.kuvasz.jooq.tables.records.UptimeEventRecord
import com.kuvaszuptime.kuvasz.models.CertificateInfo
import com.kuvaszuptime.kuvasz.models.handlers.IntegrationID
import com.kuvaszuptime.kuvasz.repositories.MonitorRepository
import com.kuvaszuptime.kuvasz.util.getCurrentTimestamp
import io.kotest.matchers.nulls.shouldNotBeNull
import org.jooq.DSLContext
import java.time.OffsetDateTime
import java.util.UUID

fun createMonitor(
    repository: MonitorRepository,
    enabled: Boolean = true,
    sslCheckEnabled: Boolean = true,
    uptimeCheckInterval: Int = 30000,
    monitorName: String = UUID.randomUUID().toString(),
    url: String = "http://irrelevant.com",
    requestMethod: HttpMethod = HttpMethod.GET,
    latencyHistoryEnabled: Boolean = true,
    forceNoCache: Boolean = true,
    followRedirects: Boolean = true,
    sslExpiryThreshold: Int = 30,
    integrations: List<IntegrationID> = emptyList(),
): MonitorRecord {
    val monitor = MonitorRecord()
        .setName(monitorName)
        .setUptimeCheckInterval(uptimeCheckInterval)
        .setUrl(url)
        .setEnabled(enabled)
        .setRequestMethod(requestMethod)
        .setSslCheckEnabled(sslCheckEnabled)
        .setCreatedAt(getCurrentTimestamp())
        .setRequestMethod(requestMethod)
        .setLatencyHistoryEnabled(latencyHistoryEnabled)
        .setForceNoCache(forceNoCache)
        .setFollowRedirects(followRedirects)
        .setSslExpiryThreshold(sslExpiryThreshold)
        .setIntegrations(integrations.toTypedArray())
    return repository.returningInsert(monitor).orNull().shouldNotBeNull()
}

fun createUptimeEventRecord(
    dslContext: DSLContext,
    monitorId: Long,
    status: UptimeStatus = UptimeStatus.UP,
    startedAt: OffsetDateTime,
    endedAt: OffsetDateTime?,
) = dslContext
    .insertInto(UPTIME_EVENT)
    .set(
        UptimeEventRecord()
            .setMonitorId(monitorId)
            .setStatus(status)
            .setStartedAt(startedAt)
            .setUpdatedAt(endedAt ?: startedAt)
            .setEndedAt(endedAt)
    )
    .execute()

fun createSSLEventRecord(
    dslContext: DSLContext,
    monitorId: Long,
    status: SslStatus = SslStatus.VALID,
    startedAt: OffsetDateTime,
    endedAt: OffsetDateTime?,
    sslExpiryDate: OffsetDateTime? = null,
) = dslContext
    .insertInto(SSL_EVENT)
    .set(
        SslEventRecord()
            .setMonitorId(monitorId)
            .setStatus(status)
            .setStartedAt(startedAt)
            .setUpdatedAt(endedAt ?: startedAt)
            .setEndedAt(endedAt)
            .setSslExpiryDate(sslExpiryDate)
    )
    .execute()

fun generateCertificateInfo(validTo: OffsetDateTime = getCurrentTimestamp().plusDays(60)) =
    CertificateInfo(validTo)
