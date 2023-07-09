package dev.mokkery.matcher.varargs

import dev.mokkery.internal.toListOrNull
import dev.mokkery.internal.capitalize
import dev.mokkery.internal.varargNameByElementType
import dev.mokkery.matcher.ArgMatcher
import kotlin.reflect.KClass

/**
 * Checks if given vararg argument satisfies provided conditions.
 */
public interface VarArgMatcher<T> : ArgMatcher<T> {

    public class AllThat(
        private val type: KClass<*>,
        private val predicate: (Any?) -> Boolean
    ) : VarArgMatcher<Any?> {
        override fun matches(arg: Any?): Boolean {
            val arrayAsList = arg.toListOrNull() ?: return false
            return arrayAsList.all(predicate)
        }

        override fun toString(): String = "${varargNameByElementType(type)} {...}"
    }

    public class AnyOf(
        private val type: KClass<*>,
    ) : VarArgMatcher<Any?> {
        override fun matches(arg: Any?): Boolean = true

        override fun toString(): String = "any${varargNameByElementType(type).capitalize()}()"

    }
}