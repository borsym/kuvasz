package com.kuvaszuptime.kuvasz.models

import com.kuvaszuptime.kuvasz.jooq.enums.HttpMethod
import com.kuvaszuptime.kuvasz.jooq.tables.records.MonitorRecord
import com.kuvaszuptime.kuvasz.models.dto.Validation
import com.kuvaszuptime.kuvasz.models.handlers.IntegrationID
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.PositiveOrZero

@Suppress("ComplexInterface")
interface MonitorCreatorLike {
    @get:NotBlank
    val name: String

    @get:NotNull
    @get:Pattern(regexp = Validation.URI_REGEX)
    val url: String

    @get:NotNull
    @get:Min(Validation.MIN_UPTIME_CHECK_INTERVAL)
    val uptimeCheckInterval: Int
    val enabled: Boolean
    val sslCheckEnabled: Boolean
    val requestMethod: HttpMethod
    val latencyHistoryEnabled: Boolean
    val forceNoCache: Boolean
    val followRedirects: Boolean

    @get:NotNull
    @get:PositiveOrZero
    val sslExpiryThreshold: Int

    val integrations: List<String>?
}

fun MonitorCreatorLike.toMonitorRecord(validatedIntegrations: Set<IntegrationID>): MonitorRecord =
    MonitorRecord()
        .setName(name)
        .setUrl(url)
        .setEnabled(enabled)
        .setUptimeCheckInterval(uptimeCheckInterval)
        .setSslCheckEnabled(sslCheckEnabled)
        .setRequestMethod(requestMethod)
        .setLatencyHistoryEnabled(latencyHistoryEnabled)
        .setForceNoCache(forceNoCache)
        .setFollowRedirects(followRedirects)
        .setSslExpiryThreshold(sslExpiryThreshold)
        .setIntegrations(validatedIntegrations.toTypedArray())
