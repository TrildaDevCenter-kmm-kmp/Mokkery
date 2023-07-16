package dev.mokkery.matcher.logical

import dev.mokkery.internal.MissingMatchersForComposite
import dev.mokkery.matcher.ArgMatcher

/**
 * Contains composite matchers for logical operations.
 */
public object LogicalMatchers {

    /**
     * Matches argument that satisfies all the [matchers]. It must be merged with [expectedMatchers] number of [ArgMatcher]s.
     */
    public data class And<T>(
        val expectedMatchers: Int,
        val matchers: List<ArgMatcher<T>> = emptyList()
    ): ArgMatcher.Composite<T> {
        override fun matches(arg: T): Boolean = matchers.all { it.matches(arg) }

        override fun compose(matcher: ArgMatcher<T>): ArgMatcher.Composite<T> {
            return copy(matchers = listOf(matcher) + matchers)
        }

        override fun isFilled(): Boolean = matchers.size == expectedMatchers

        override fun assertFilled() {
            if (matchers.size < expectedMatchers) {
                throw MissingMatchersForComposite("and", expectedMatchers, matchers)
            }
        }

        override fun toString(): String = "and(${matchers.joinToString()})"
    }

    /**
     * Matches argument that satisfies any matcher from [matchers].
     * It must be merged with [expectedMatchers] number of ArgMatchers.
     */
    public data class Or<T>(
        val expectedMatchers: Int,
        val matchers: List<ArgMatcher<T>> = emptyList()
    ): ArgMatcher.Composite<T> {
        override fun matches(arg: T): Boolean = matchers.any { it.matches(arg) }

        override fun compose(matcher: ArgMatcher<T>): ArgMatcher.Composite<T> {
            return copy(matchers = listOf(matcher) + matchers)
        }

        override fun isFilled(): Boolean = matchers.size == expectedMatchers

        override fun assertFilled() {
            if (matchers.size < expectedMatchers) {
                throw MissingMatchersForComposite("or", expectedMatchers, matchers)
            }
        }

        override fun toString(): String = "or(${matchers.joinToString()})"
    }

    /**
     * Matches argument that does not satisfy [matcher].
     */
    public data class Not<T>(val matcher: ArgMatcher<T>? = null) : ArgMatcher.Composite<T> {
        override fun matches(arg: T): Boolean = matcher!!.matches(arg).not()

        override fun compose(matcher: ArgMatcher<T>): ArgMatcher.Composite<T> = copy(matcher = matcher)

        override fun isFilled(): Boolean = matcher != null

        override fun assertFilled() {
            if (matcher == null) {
                throw MissingMatchersForComposite("not", 1, listOf())
            }
        }

        override fun toString(): String = "not($matcher)"
    }
}
