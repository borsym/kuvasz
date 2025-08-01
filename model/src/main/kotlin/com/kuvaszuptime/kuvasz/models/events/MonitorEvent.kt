package com.kuvaszuptime.kuvasz.models.events

import com.kuvaszuptime.kuvasz.i18n.Messages
import com.kuvaszuptime.kuvasz.jooq.enums.SslStatus
import com.kuvaszuptime.kuvasz.jooq.enums.UptimeStatus
import com.kuvaszuptime.kuvasz.jooq.tables.records.MonitorRecord
import com.kuvaszuptime.kuvasz.jooq.tables.records.SslEventRecord
import com.kuvaszuptime.kuvasz.jooq.tables.records.UptimeEventRecord
import com.kuvaszuptime.kuvasz.models.CertificateInfo
import com.kuvaszuptime.kuvasz.models.SSLValidationError
import com.kuvaszuptime.kuvasz.models.events.MonitorEvent.Companion.ERROR_MAX_LENGTH
import com.kuvaszuptime.kuvasz.util.diffToDuration
import com.kuvaszuptime.kuvasz.util.getCurrentTimestamp
import com.kuvaszuptime.kuvasz.util.toDurationString
import io.micronaut.http.HttpStatus
import java.net.URI
import kotlin.time.Duration

sealed class MonitorEvent {
    abstract val monitor: MonitorRecord

    abstract fun toStructuredMessage(): StructuredMessage

    val dispatchedAt = getCurrentTimestamp()

    companion object {
        const val ERROR_MAX_LENGTH = 255
    }
}

sealed class UptimeMonitorEvent : MonitorEvent() {
    abstract val previousEvent: UptimeEventRecord?

    abstract val uptimeStatus: UptimeStatus

    fun statusNotEquals(previousEvent: UptimeEventRecord) = !statusEquals(previousEvent)

    fun getEndedEventDuration(): Duration? =
        previousEvent?.let { previousEvent ->
            if (statusNotEquals(previousEvent)) {
                previousEvent.startedAt.diffToDuration(dispatchedAt)
            } else {
                null
            }
        }

    fun runWhenStateChanges(toRun: (UptimeMonitorEvent) -> Unit) =
        previousEvent?.let { previousEvent ->
            if (statusNotEquals(previousEvent)) {
                toRun(this)
            }
        } ?: toRun(this)

    private fun statusEquals(previousEvent: UptimeEventRecord) = uptimeStatus == previousEvent.status
}

data class MonitorUpEvent(
    override val monitor: MonitorRecord,
    val status: HttpStatus,
    val latency: Int,
    override val previousEvent: UptimeEventRecord?
) : UptimeMonitorEvent() {

    override val uptimeStatus = UptimeStatus.UP

    override fun toStructuredMessage() =
        StructuredMonitorUpMessage(
            summary = Messages.yourMonitorIsUp(monitor.name, monitor.url, status.code),
            latency = Messages.latencyIs(latency),
            previousDownTime = getEndedEventDuration().toDurationString()?.let { Messages.wasDownFor(it) }
        )
}

data class MonitorDownEvent(
    override val monitor: MonitorRecord,
    val status: HttpStatus?,
    val error: Exception,
    override val previousEvent: UptimeEventRecord?
) : UptimeMonitorEvent() {

    override val uptimeStatus = UptimeStatus.DOWN

    override fun toStructuredMessage(): StructuredMonitorDownMessage {
        val sanitizedError = error.message?.sanitizeMessage()
        val structuredError = if (status != null) {
            "${status.code} ${status.reason}"
        } else {
            sanitizedError
        }

        return StructuredMonitorDownMessage(
            summary = Messages.yourMonitorIsDown(
                monitor.name,
                monitor.url,
                status?.let { " (" + it.code + ")" }.orEmpty(),
            ),
            error = Messages.reasonExplanation(structuredError.orEmpty()),
            previousUpTime = getEndedEventDuration().toDurationString()?.let { Messages.wasUpFor(it) }
        )
    }
}

data class RedirectEvent(
    override val monitor: MonitorRecord,
    val redirectLocation: URI
) : MonitorEvent() {

    override fun toStructuredMessage() = StructuredRedirectMessage(
        summary = Messages.requestHasBeenRedirected(monitor.name, monitor.url, redirectLocation),
    )
}

sealed class SSLMonitorEvent : MonitorEvent() {
    abstract val previousEvent: SslEventRecord?

    abstract val sslStatus: SslStatus

    fun statusNotEquals(previousEvent: SslEventRecord) = !statusEquals(previousEvent)

    fun getEndedEventDuration(): Duration? =
        previousEvent?.let { previousEvent ->
            if (statusNotEquals(previousEvent)) {
                previousEvent.startedAt.diffToDuration(dispatchedAt)
            } else {
                null
            }
        }

    fun getPreviousStatusString(): String = previousEvent?.status?.name.orEmpty()

    fun runWhenStateChanges(toRun: (SSLMonitorEvent) -> Unit) =
        previousEvent?.let { previousEvent ->
            if (statusNotEquals(previousEvent)) {
                toRun(this)
            }
        } ?: toRun(this)

    private fun statusEquals(previousEvent: SslEventRecord) = sslStatus == previousEvent.status
}

interface WithCertInfo {
    val monitor: MonitorRecord
    val certInfo: CertificateInfo
}

data class SSLValidEvent(
    override val monitor: MonitorRecord,
    override val certInfo: CertificateInfo,
    override val previousEvent: SslEventRecord?
) : SSLMonitorEvent(), WithCertInfo {

    override val sslStatus = SslStatus.VALID

    override fun toStructuredMessage() =
        StructuredSSLValidMessage(
            summary = Messages.yourSiteHasAValidCert(monitor.name, monitor.url),
            previousInvalidEvent = getEndedEventDuration().toDurationString()
                ?.let { Messages.wasXForY(getPreviousStatusString(), it) }
        )
}

data class SSLInvalidEvent(
    override val monitor: MonitorRecord,
    val error: SSLValidationError,
    override val previousEvent: SslEventRecord?
) : SSLMonitorEvent() {

    override val sslStatus = SslStatus.INVALID

    override fun toStructuredMessage() =
        StructuredSSLInvalidMessage(
            summary = Messages.yourSiteHasAnInvalidCert(monitor.name, monitor.url),
            error = Messages.reasonExplanation(error.message?.sanitizeMessage().orEmpty()),
            previousValidEvent = getEndedEventDuration().toDurationString()
                ?.let { Messages.wasXForY(getPreviousStatusString(), it) }
        )
}

data class SSLWillExpireEvent(
    override val monitor: MonitorRecord,
    override val certInfo: CertificateInfo,
    override val previousEvent: SslEventRecord?
) : SSLMonitorEvent(), WithCertInfo {

    override val sslStatus = SslStatus.WILL_EXPIRE

    override fun toStructuredMessage() =
        StructuredSSLWillExpireMessage(
            summary = Messages.yourCertWillExpireSoon(monitor.url),
            validUntil = Messages.expiryDate(certInfo.validTo)
        )
}

private fun String.sanitizeMessage(): String {
    val sanitized = removeControlChars()
    return if (sanitized.length > ERROR_MAX_LENGTH) {
        "${sanitized.take(ERROR_MAX_LENGTH)} ... [REDACTED]"
    } else {
        sanitized
    }
}

/**
 * Removes non-visible or non-readable characters from the string, also replaces null characters with the string
 * "null" (null characters can't be handled by Postgres)
 */
private fun String.removeControlChars() =
    replace("\u0000", "null")
        .filter { !it.isISOControl() }
