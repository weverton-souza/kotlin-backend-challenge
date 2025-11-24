package com.kotlin.backend.challenge.extensions

import com.kotlin.backend.challenge.exception.ResourceNotFoundException
import com.kotlin.backend.challenge.utils.MessageKeys
import java.util.Collections
import java.util.Optional

object OptionalExtensions {
    fun <T> Optional<T>.orThrowNotFound(message: String = MessageKeys.HTTP_4XX_404_NOT_FOUND): T =
        orElseThrow { ResourceNotFoundException(message) }

    fun <T> Optional<T>.orNull(): T? = orElse(null)
}

object CollectionExtensions {
    fun <T> Collection<T>.toImmutableSet(): MutableSet<T> = Collections.unmodifiableSet(HashSet(this))

    fun <T> Collection<T>.toImmutableList(): List<T> = Collections.unmodifiableList(ArrayList(this))

    fun <T> Collection<T>.toMutableListSafe(): MutableList<T> = ArrayList(this)
}
