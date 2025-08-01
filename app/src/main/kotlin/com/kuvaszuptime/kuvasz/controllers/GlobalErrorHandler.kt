@file:Suppress("UNUSED_PARAMETER")

package com.kuvaszuptime.kuvasz.controllers

import com.fasterxml.jackson.core.JsonParseException
import com.kuvaszuptime.kuvasz.models.DuplicationException
import com.kuvaszuptime.kuvasz.models.MonitorNotFoundException
import com.kuvaszuptime.kuvasz.models.PersistenceException
import com.kuvaszuptime.kuvasz.models.SchedulingException
import com.kuvaszuptime.kuvasz.models.ServiceError
import com.kuvaszuptime.kuvasz.models.handlers.InvalidIntegrationIDException
import com.kuvaszuptime.kuvasz.validation.NonExistingIntegrationIdException
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import jakarta.validation.ValidationException

@Controller
class GlobalErrorHandler {

    @Error(global = true)
    fun notFoundExceptionHandler(request: HttpRequest<*>, ex: MonitorNotFoundException): HttpResponse<ServiceError> {
        val error = ServiceError(ex.message)
        return HttpResponse.notFound(error)
    }

    @Error(global = true)
    fun duplicationExceptionHandler(request: HttpRequest<*>, ex: DuplicationException): HttpResponse<ServiceError> {
        val error = ServiceError(ex.message)
        return HttpResponse.status<ServiceError>(HttpStatus.CONFLICT).body(error)
    }

    @Error(global = true)
    fun validationExceptionHandler(request: HttpRequest<*>, ex: ValidationException): HttpResponse<ServiceError> =
        HttpResponse.badRequest(ServiceError(ex.message))

    @Error(global = true)
    fun conversionExceptionHandler(request: HttpRequest<*>, ex: ConversionErrorException): HttpResponse<ServiceError> {
        val message = "Failed to convert argument: ${ex.message}"
        return HttpResponse.badRequest(ServiceError(message))
    }

    @Error(global = true)
    fun jsonParseExceptionHandler(request: HttpRequest<*>, ex: JsonParseException): HttpResponse<ServiceError> {
        val message = "Can't parse the JSON in the payload"
        return HttpResponse.badRequest(ServiceError(message))
    }

    @Error(global = true)
    fun persistenceExceptionHandler(request: HttpRequest<*>, ex: PersistenceException): HttpResponse<ServiceError> {
        val error = ServiceError(ex.message)
        return HttpResponse.status<ServiceError>(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }

    @Error(global = true)
    fun schedulingErrorHandler(request: HttpRequest<*>, ex: SchedulingException): HttpResponse<ServiceError> {
        val error = ServiceError(ex.message)
        return HttpResponse.status<ServiceError>(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }

    @Error(global = true)
    fun readOnlyMonitorExceptionHandler(
        request: HttpRequest<*>,
        ex: ReadOnlyMonitorException
    ): HttpResponse<ServiceError> {
        val error = ServiceError(ex.message)
        return HttpResponse.status<ServiceError>(HttpStatus.METHOD_NOT_ALLOWED).body(error)
    }

    @Error(global = true)
    fun nonExistingIntegrationExceptionHandler(
        request: HttpRequest<*>,
        ex: NonExistingIntegrationIdException
    ): HttpResponse<ServiceError> {
        val error = ServiceError(ex.message)
        return HttpResponse.status<ServiceError>(HttpStatus.BAD_REQUEST).body(error)
    }

    @Error(global = true)
    fun invalidIntegrationIDExceptionHandler(
        request: HttpRequest<*>,
        ex: InvalidIntegrationIDException
    ): HttpResponse<ServiceError> {
        val error = ServiceError(ex.message)
        return HttpResponse.status<ServiceError>(HttpStatus.BAD_REQUEST).body(error)
    }
}
