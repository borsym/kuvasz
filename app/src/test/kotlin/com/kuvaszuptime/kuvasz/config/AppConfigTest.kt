package com.kuvaszuptime.kuvasz.config

import io.kotest.assertions.exceptionToMessage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldContain
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.PropertySource
import io.micronaut.context.exceptions.BeanInstantiationException

class AppConfigTest : BehaviorSpec(
    {
        given("an AppConfig bean") {
            `when`("there is an event-data-retention-days parameter with a null value") {
                val properties = PropertySource.of(
                    "test",
                    mapOf(
                        "app-config.event-data-retention-days" to "null",
                        "app-config.latency-data-retention-days" to 7,
                    )
                )
                then("ApplicationContext should throw a BeanInstantiationException") {
                    val exception = shouldThrow<BeanInstantiationException> {
                        ApplicationContext.run(properties)
                    }
                    exceptionToMessage(exception) shouldContain
                        "Error resolving property value [app-config.event-data-retention-days]"
                }
            }

            `when`("there is an event-data-retention-days parameter with an exceptionally low value") {
                val properties = PropertySource.of(
                    "test",
                    mapOf(
                        "app-config.event-data-retention-days" to "0",
                        "app-config.latency-data-retention-days" to 7,
                    )
                )
                then("ApplicationContext should throw a BeanInstantiationException") {
                    val exception = shouldThrow<BeanInstantiationException> {
                        ApplicationContext.run(properties)
                    }
                    exceptionToMessage(exception) shouldContain "AppConfig.p0 - must be greater than or equal to 1"
                }
            }

            `when`("there is a latency-data-retention-days parameter with a null value") {
                val properties = PropertySource.of(
                    "test",
                    mapOf(
                        "app-config.event-data-retention-days" to 7,
                        "app-config.latency-data-retention-days" to "null"
                    )
                )
                then("ApplicationContext should throw a BeanInstantiationException") {
                    val exception = shouldThrow<BeanInstantiationException> {
                        ApplicationContext.run(properties)
                    }
                    exceptionToMessage(exception) shouldContain
                        "Error resolving property value [app-config.latency-data-retention-days]"
                }
            }

            `when`("there is a latency-data-retention-days parameter with an exceptionally low value") {
                val properties = PropertySource.of(
                    "test",
                    mapOf(
                        "app-config.event-data-retention-days" to 7,
                        "app-config.latency-data-retention-days" to "0"
                    )
                )
                then("ApplicationContext should throw a BeanInstantiationException") {
                    val exception = shouldThrow<BeanInstantiationException> {
                        ApplicationContext.run(properties)
                    }
                    exceptionToMessage(exception) shouldContain "AppConfig.p0 - must be greater than or equal to 1"
                }
            }
        }
    }
)
