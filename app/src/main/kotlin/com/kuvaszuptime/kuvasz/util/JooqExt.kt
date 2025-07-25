@file:Suppress("RedundantLambdaArrow")

package com.kuvaszuptime.kuvasz.util

import com.kuvaszuptime.kuvasz.models.DuplicationException
import com.kuvaszuptime.kuvasz.models.PersistenceException
import org.jooq.InsertResultStep
import org.jooq.TableRecord
import org.jooq.UpdateResultStep
import org.jooq.exception.DataAccessException
import org.jooq.exception.NoDataFoundException
import org.postgresql.util.PSQLException

fun DataAccessException.toPersistenceError(): PersistenceException =
    getCause(PSQLException::class.java)?.message?.let { message ->
        if (message.contains("duplicate key")) DuplicationException() else PersistenceException(message)
    } ?: PersistenceException(message)

fun <R : TableRecord<R>> InsertResultStep<R>.fetchOneOrThrow(): R =
    fetchOne() ?: throw NoDataFoundException()

fun <R : TableRecord<R>> UpdateResultStep<R>.fetchOneOrThrow(): R =
    fetchOne() ?: throw NoDataFoundException()
