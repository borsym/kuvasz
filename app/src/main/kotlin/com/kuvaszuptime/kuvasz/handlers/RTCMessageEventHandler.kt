package com.kuvaszuptime.kuvasz.handlers

import com.kuvaszuptime.kuvasz.models.events.MonitorUpEvent
import com.kuvaszuptime.kuvasz.models.events.SSLMonitorEvent
import com.kuvaszuptime.kuvasz.models.events.SSLValidEvent
import com.kuvaszuptime.kuvasz.models.events.UptimeMonitorEvent
import com.kuvaszuptime.kuvasz.models.events.formatters.RichTextMessageFormatter
import com.kuvaszuptime.kuvasz.services.EventDispatcher
import com.kuvaszuptime.kuvasz.services.IntegrationRepository
import com.kuvaszuptime.kuvasz.services.TextMessageService
import com.kuvaszuptime.kuvasz.util.getBodyAs
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import org.slf4j.Logger

abstract class RTCMessageEventHandler(
    private val eventDispatcher: EventDispatcher,
    private val messageService: TextMessageService,
    integrationRepository: IntegrationRepository,
) : AbstractIntegrationProvider(integrationRepository) {

    internal abstract val logger: Logger

    internal abstract val formatter: RichTextMessageFormatter

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        eventDispatcher.subscribeToMonitorUpEvents { event ->
            logger.debug("A MonitorUpEvent has been received for monitor with ID: ${event.monitor.id}")
            event.handle()
        }
        eventDispatcher.subscribeToMonitorDownEvents { event ->
            logger.debug("A MonitorDownEvent has been received for monitor with ID: ${event.monitor.id}")
            event.handle()
        }
        eventDispatcher.subscribeToSSLValidEvents { event ->
            logger.debug("An SSLValidEvent has been received for monitor with ID: ${event.monitor.id}")
            event.handle()
        }
        eventDispatcher.subscribeToSSLInvalidEvents { event ->
            logger.debug("An SSLInvalidEvent has been received for monitor with ID: ${event.monitor.id}")
            event.handle()
        }
        eventDispatcher.subscribeToSSLWillExpireEvents { event ->
            logger.debug("An SSLWillExpireEvent has been received for monitor with ID: ${event.monitor.id}")
            event.handle()
        }
    }

    private fun UptimeMonitorEvent.handle() =
        this.runWhenStateChanges { event ->
            if (this is MonitorUpEvent && previousEvent == null) {
                return@runWhenStateChanges
            }
            val message = formatter.toFormattedMessage(event)
            filterTargetConfigs(event.monitor.integrations).forEach { target ->
                messageService.sendMessage(target, message).handleResponse()
            }
        }

    private fun SSLMonitorEvent.handle() =
        this.runWhenStateChanges { event ->
            if (this is SSLValidEvent && previousEvent == null) {
                return@runWhenStateChanges
            }
            val message = formatter.toFormattedMessage(event)
            filterTargetConfigs(event.monitor.integrations).forEach { target ->
                messageService.sendMessage(target, message).handleResponse()
            }
        }

    private fun Single<String>.handleResponse(): Disposable =
        subscribe(
            {
                logger.debug("The message to your configured webhook has been successfully sent")
            },
            { ex ->
                val message = if (ex is HttpClientResponseException) {
                    ex.response.getBodyAs<String>() ?: "Empty response"
                } else {
                    ex.message
                }
                logger.error("The message cannot be sent to your configured webhook: $message")
            }
        )
}
