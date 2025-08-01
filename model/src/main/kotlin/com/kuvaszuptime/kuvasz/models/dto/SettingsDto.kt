package com.kuvaszuptime.kuvasz.models.dto

import com.kuvaszuptime.kuvasz.models.handlers.DiscordNotificationConfig
import com.kuvaszuptime.kuvasz.models.handlers.EmailNotificationConfig
import com.kuvaszuptime.kuvasz.models.handlers.IntegrationID
import com.kuvaszuptime.kuvasz.models.handlers.IntegrationType
import com.kuvaszuptime.kuvasz.models.handlers.PagerdutyConfig
import com.kuvaszuptime.kuvasz.models.handlers.SlackNotificationConfig
import com.kuvaszuptime.kuvasz.models.handlers.TelegramNotificationConfig
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema

@Introspected
data class SettingsDto(
    @Schema(description = "Authentication settings", required = true)
    val authentication: AuthenticationSettingsDto,
    @Schema(description = "Application settings", required = true)
    val app: AppSettingsDto,
    @Schema(description = "Integration settings", required = true)
    val integrations: IntegrationSettingsDto,
    @Schema(description = "Metrics exporter settings", required = true)
    val metricsExport: MetricsExportSettingsDto,
) {
    @Introspected
    data class AuthenticationSettingsDto(
        @Schema(description = "Whether authentication is enabled", required = true)
        val enabled: Boolean,
        @Schema(description = "The maximum age of the access token in seconds", required = true)
        val accessTokenMaxAge: Long,
    )

    @Introspected
    data class AppSettingsDto(
        @Schema(description = "The version of the application", required = true)
        val version: String,
        @Schema(description = "Number of days to retain event data", required = true)
        val eventDataRetentionDays: Int,
        @Schema(description = "Number of days to retain latency data", required = true)
        val latencyDataRetentionDays: Int,
        @Schema(description = "The language of the application", required = true)
        val language: String,
        @Schema(description = "Whether event logging is enabled", required = true)
        val eventLoggingEnabled: Boolean,
        @Schema(
            description = "Whether the application is in read-only mode (i.e. monitors are configured via YAML",
            required = true,
        )
        val readOnlyMode: Boolean,
    )

    @Introspected
    data class IntegrationSettingsDto(
        @Schema(description = "SMTP configuration for email notifications", required = true, nullable = false)
        val smtp: SmtpConfigDto?,
        @Schema(description = "List of Slack notification configurations", required = true)
        val slack: List<SlackNotificationConfigDto>,
        @Schema(description = "List of Discord notification configurations", required = true)
        val discord: List<DiscordNotificationConfigDto>,
        @Schema(description = "List of PagerDuty configurations", required = true)
        val pagerduty: List<PagerdutyConfigDto>,
        @Schema(description = "List of email notification configurations", required = true)
        val email: List<EmailNotificationConfigDto>,
        @Schema(description = "List of Telegram notification configurations", required = true)
        val telegram: List<TelegramNotificationConfigDto>,
    )

    @Introspected
    data class SmtpConfigDto(
        @Schema(description = "The SMTP host", required = true)
        val host: String,
        @Schema(description = "The SMTP port", required = true)
        val port: Int,
        @Schema(description = "The SMTP transport strategy", required = true)
        val transportStrategy: String,
    )

    interface IntegrationConfigDto {
        val id: IntegrationID
        val type: IntegrationType
        val name: String
        val enabled: Boolean
        val global: Boolean
    }

    @Introspected
    data class SlackNotificationConfigDto(
        override val id: IntegrationID,
        @Schema(description = IntegrationDocs.TYPE, required = true)
        override val type: IntegrationType,
        @Schema(description = IntegrationDocs.NAME, required = true)
        override val name: String,
        @Schema(description = IntegrationDocs.ENABLED, required = true)
        override val enabled: Boolean,
        @Schema(description = IntegrationDocs.GLOBAL, required = true)
        override val global: Boolean,
    ) : IntegrationConfigDto {
        constructor(integrationID: IntegrationID, config: SlackNotificationConfig) : this(
            id = integrationID,
            type = integrationID.type,
            name = config.name,
            enabled = config.enabled,
            global = config.global,
        )
    }

    @Introspected
    data class DiscordNotificationConfigDto(
        override val id: IntegrationID,
        @Schema(description = IntegrationDocs.TYPE, required = true)
        override val type: IntegrationType,
        @Schema(description = IntegrationDocs.NAME, required = true)
        override val name: String,
        @Schema(description = IntegrationDocs.ENABLED, required = true)
        override val enabled: Boolean,
        @Schema(description = IntegrationDocs.GLOBAL, required = true)
        override val global: Boolean
    ) : IntegrationConfigDto {
        constructor(integrationID: IntegrationID, config: DiscordNotificationConfig) : this(
            id = integrationID,
            type = integrationID.type,
            name = config.name,
            enabled = config.enabled,
            global = config.global
        )
    }

    @Introspected
    data class PagerdutyConfigDto(
        override val id: IntegrationID,
        @Schema(description = IntegrationDocs.TYPE, required = true)
        override val type: IntegrationType,
        @Schema(description = IntegrationDocs.NAME, required = true)
        override val name: String,
        @Schema(description = IntegrationDocs.ENABLED, required = true)
        override val enabled: Boolean,
        @Schema(description = IntegrationDocs.GLOBAL, required = true)
        override val global: Boolean,
    ) : IntegrationConfigDto {
        constructor(integrationID: IntegrationID, config: PagerdutyConfig) : this(
            id = integrationID,
            type = integrationID.type,
            name = config.name,
            enabled = config.enabled,
            global = config.global,
        )
    }

    @Introspected
    data class EmailNotificationConfigDto(
        override val id: IntegrationID,
        @Schema(description = IntegrationDocs.TYPE, required = true)
        override val type: IntegrationType,
        @Schema(description = IntegrationDocs.NAME, required = true)
        override val name: String,
        @Schema(description = IntegrationDocs.ENABLED, required = true)
        override val enabled: Boolean,
        @Schema(description = IntegrationDocs.GLOBAL, required = true)
        override val global: Boolean,
        @Schema(description = "The email address from which notifications are sent", required = true)
        val fromAddress: String,
        @Schema(description = "The email address to which notifications are sent", required = true)
        val toAddress: String,
    ) : IntegrationConfigDto {
        constructor(integrationID: IntegrationID, config: EmailNotificationConfig) : this(
            id = integrationID,
            type = integrationID.type,
            name = config.name,
            enabled = config.enabled,
            global = config.global,
            fromAddress = config.fromAddress,
            toAddress = config.toAddress,
        )
    }

    @Introspected
    data class TelegramNotificationConfigDto(
        override val id: IntegrationID,
        @Schema(description = IntegrationDocs.TYPE, required = true)
        override val type: IntegrationType,
        @Schema(description = IntegrationDocs.NAME, required = true)
        override val name: String,
        @Schema(description = IntegrationDocs.ENABLED, required = true)
        override val enabled: Boolean,
        @Schema(description = IntegrationDocs.GLOBAL, required = true)
        override val global: Boolean,
        @Schema(description = "The chat ID for Telegram notifications", required = true)
        val chatId: String,
    ) : IntegrationConfigDto {
        constructor(integrationID: IntegrationID, config: TelegramNotificationConfig) : this(
            id = integrationID,
            type = integrationID.type,
            name = config.name,
            enabled = config.enabled,
            global = config.global,
            chatId = config.chatId,
        )
    }

    @Introspected
    data class MetricsExportSettingsDto(
        @Schema(description = "Whether the metrics exporting is generally enabled", required = true)
        val exportEnabled: Boolean,
        @Schema(description = "Settings for individual meters", required = true)
        val meters: MeterSettingsDto,
        @Schema(description = "Settings for individual exporters", required = true)
        val exporters: ExporterSettingsDto,
    ) {
        @Introspected
        data class MeterSettingsDto(
            @Schema(description = "Whether SSL certificate expiry exporter is enabled", required = true)
            val sslExpiry: Boolean,
            @Schema(description = "Whether latest latency exporter is enabled", required = true)
            val latestLatency: Boolean,
            @Schema(description = "Whether monitor status exporter is enabled", required = true)
            val uptimeStatus: Boolean,
            @Schema(description = "Whether SSL status exporter is enabled", required = true)
            val sslStatus: Boolean,
        )

        @Introspected
        data class ExporterSettingsDto(
            @Schema(description = "Prometheus exporter settings", required = true)
            val prometheus: PrometheusSettingsDto,
            @Schema(description = "OpenTelemetry exporter settings", required = true)
            val openTelemetry: OTLPSettingsDto,
        ) {
            @Introspected
            data class PrometheusSettingsDto(
                @Schema(description = "Whether the exporter is enabled", required = true)
                val enabled: Boolean,
                @Schema(description = "Whether descriptions are included in the export", required = true)
                val descriptions: Boolean,
            )

            @Introspected
            data class OTLPSettingsDto(
                @Schema(description = "Whether the exporter is enabled", required = true)
                val enabled: Boolean,
                @Schema(description = "The endpoint where the metrics will be published", required = true)
                val url: String,
                @Schema(
                    description = "The step for the metrics reporting as an ISO 8601 duration string",
                    required = true
                )
                val step: String,
            )
        }
    }
}
