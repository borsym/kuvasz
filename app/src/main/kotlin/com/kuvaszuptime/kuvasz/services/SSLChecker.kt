package com.kuvaszuptime.kuvasz.services

import com.kuvaszuptime.kuvasz.jooq.tables.records.MonitorRecord
import com.kuvaszuptime.kuvasz.models.events.SSLInvalidEvent
import com.kuvaszuptime.kuvasz.models.events.SSLValidEvent
import com.kuvaszuptime.kuvasz.models.events.SSLWillExpireEvent
import com.kuvaszuptime.kuvasz.repositories.SSLEventRepository
import com.kuvaszuptime.kuvasz.repositories.UptimeEventRepository
import com.kuvaszuptime.kuvasz.util.getCurrentTimestamp
import jakarta.inject.Singleton
import java.net.URI

@Singleton
class SSLChecker(
    private val sslValidator: SSLValidator,
    private val uptimeEventRepository: UptimeEventRepository,
    private val eventDispatcher: EventDispatcher,
    private val sslEventRepository: SSLEventRepository
) {

    fun check(monitor: MonitorRecord) {
        if (uptimeEventRepository.isMonitorUp(monitor.id, nullAsUp = true)) {
            val previousEvent = sslEventRepository.getPreviousEventByMonitorId(monitorId = monitor.id)
            sslValidator.validate(URI(monitor.url).toURL()).fold(
                { error ->
                    eventDispatcher.dispatch(
                        SSLInvalidEvent(
                            monitor = monitor,
                            error = error,
                            previousEvent = previousEvent
                        )
                    )
                },
                { certInfo ->
                    val expiryThresholdDays = monitor.sslExpiryThreshold.toLong()
                    if (certInfo.validTo.isBefore(getCurrentTimestamp().plusDays(expiryThresholdDays))) {
                        eventDispatcher.dispatch(
                            SSLWillExpireEvent(
                                monitor = monitor,
                                certInfo = certInfo,
                                previousEvent = previousEvent
                            )
                        )
                    } else {
                        eventDispatcher.dispatch(
                            SSLValidEvent(
                                monitor = monitor,
                                certInfo = certInfo,
                                previousEvent = previousEvent
                            )
                        )
                    }
                }
            )
        }
    }
}
