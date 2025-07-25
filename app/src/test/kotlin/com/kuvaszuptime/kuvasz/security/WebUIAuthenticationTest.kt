package com.kuvaszuptime.kuvasz.security

import com.kuvaszuptime.kuvasz.DatabaseStringSpec
import com.kuvaszuptime.kuvasz.config.AdminAuthConfig
import com.kuvaszuptime.kuvasz.mocks.createMonitor
import com.kuvaszuptime.kuvasz.repositories.MonitorRepository
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.micronaut.views.htmx.http.HtmxRequestHeaders
import io.micronaut.views.htmx.http.HtmxResponseHeaders
import kotlinx.coroutines.reactive.awaitFirst

@MicronautTest
@Property(name = "micronaut.security.enabled", value = "true")
@Property(name = "admin-auth.api-key", value = TEST_API_KEY)
@Property(name = "admin-auth.username", value = TEST_USERNAME)
@Property(name = "admin-auth.password", value = TEST_PASSWORD)
@Property(name = "micronaut.http.client.follow-redirects", value = "false")
class WebUIAuthenticationTest(
    @Client("/") private val client: HttpClient,
    private val authConfig: AdminAuthConfig,
    monitorRepository: MonitorRepository,
) : DatabaseStringSpec({

    "all the web UI endpoints should be secured - anonymous user" {

        table(
            headers("url"),
            row("/"),
            row("/monitors"),
            row("/monitors/1"),
            row("/fragments/monitors/list"),
            row("/fragments/monitors/1/details-heading"),
            row("/fragments/monitors/1/details-uptime-events"),
            row("/fragments/monitors/1/details-ssl-events"),
            row("/fragments/monitors/stats"),
            row("/settings"),
        ).forAll { url ->
            val response = client.exchange(url).awaitFirst()

            response.status shouldBe HttpStatus.SEE_OTHER
            response.headers.get(HttpHeaders.LOCATION).shouldNotBeNull().let { locationHeader ->
                locationHeader shouldBe "/login"
            }
        }
    }

    "all the web UI endpoints should be secured - valid API key is used" {

        val cases = table(
            headers("url"),
            row("/"),
            row("/monitors"),
            row("/monitors/1"),
            row("/fragments/monitors/list"),
            row("/fragments/monitors/1/details-heading"),
            row("/fragments/monitors/1/details-uptime-events"),
            row("/fragments/monitors/1/details-ssl-events"),
            row("/fragments/monitors/stats"),
            row("/settings"),
        )
        cases.forAll { url ->
            val request = HttpRequest.GET<Any>(url).header("X-API-KEY", TEST_API_KEY)
            val response = client.exchange(request).awaitFirst()

            response.status shouldBe HttpStatus.SEE_OTHER
            response.headers.get(HttpHeaders.LOCATION).shouldNotBeNull().let { locationHeader ->
                locationHeader shouldBe "/login"
            }
        }
        cases.forAll { url ->
            val request = HttpRequest.GET<Any>(url).bearerAuth(TEST_API_KEY)
            val response = client.exchange(request).awaitFirst()

            response.status shouldBe HttpStatus.SEE_OTHER
            response.headers.get(HttpHeaders.LOCATION).shouldNotBeNull().let { locationHeader ->
                locationHeader shouldBe "/login"
            }
        }
    }

    "all the web endpoints should be accessible with a valid JWT" {

        val jwt = getValidJWT(client, authConfig)
        val monitor = createMonitor(monitorRepository)

        table(
            headers("url"),
            row("/"),
            row("/monitors"),
            row("/monitors/${monitor.id}"),
            row("/fragments/monitors/list"),
            row("/fragments/monitors/${monitor.id}/details-heading"),
            row("/fragments/monitors/${monitor.id}/details-uptime-events"),
            row("/fragments/monitors/${monitor.id}/details-ssl-events"),
            row("/fragments/monitors/stats"),
            row("/settings"),
        ).forAll { url ->
            val response = client.exchange(
                HttpRequest.GET<Any>(url).header(HttpHeaders.COOKIE, "JWT=$jwt")
            ).awaitFirst()

            response.status shouldBe HttpStatus.OK
        }
    }

    "already authenticated request against /login should be redirected to /" {
        val jwt = getValidJWT(client, authConfig)
        val request = HttpRequest.GET<Any>("/login").header(HttpHeaders.COOKIE, "JWT=$jwt")

        val response = client.exchange(request).awaitFirst()

        response.status shouldBe HttpStatus.SEE_OTHER
        response.headers.get(HttpHeaders.LOCATION).shouldNotBeNull().let { locationHeader ->
            locationHeader shouldBe "/"
        }
    }

    "anonymous HTMX requests should be redirected to the login page with a 204 and a specific header" {
        val request = HttpRequest.GET<Any>("/").header(HtmxRequestHeaders.HX_REQUEST, "true")
        val response = client.exchange(request).awaitFirst()

        response.status shouldBe HttpStatus.NO_CONTENT
        response.headers.get(HtmxResponseHeaders.HX_REDIRECT).shouldNotBeNull().let { redirectHeader ->
            redirectHeader shouldBe "/login"
        }
    }
})
