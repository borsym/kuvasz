package com.kuvaszuptime.kuvasz

import com.kuvaszuptime.kuvasz.models.handlers.IntegrationConfig
import com.kuvaszuptime.kuvasz.models.handlers.IntegrationMap
import com.kuvaszuptime.kuvasz.models.handlers.IntegrationType
import de.comahe.i18n4k.Locale
import de.comahe.i18n4k.config.I18n4kConfigDefault
import de.comahe.i18n4k.i18n4k

data class AppGlobals(
    val isReadOnlyMode: () -> Boolean,
    val isAuthenticated: () -> Boolean,
    val isAuthEnabled: Boolean,
    val appVersion: String,
    val locale: Locale,
    val configuredIntegrations: IntegrationMap,
    val enabledIntegrations: IntegrationMap,
    val configuredIntegrationsByType: Map<IntegrationType, Set<IntegrationConfig>>,
) {
    init {
        // Setting up the locale for i18n messages
        val i18n4kConfig = I18n4kConfigDefault()
        i18n4k = i18n4kConfig
        i18n4kConfig.locale = this.locale
    }
}
