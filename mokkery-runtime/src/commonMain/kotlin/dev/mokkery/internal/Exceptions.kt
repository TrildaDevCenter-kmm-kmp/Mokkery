package dev.mokkery.internal

import dev.mokkery.MokkeryRuntimeException
import dev.mokkery.matcher.ArgMatcher
import kotlin.reflect.KClass

internal class CallNotMockedException(name: String) : MokkeryRuntimeException(message = "Call $name not mocked!")

@PublishedApi
internal class MokkeryPluginNotAppliedException : MokkeryRuntimeException(
    message = "This call should be replaced by the Mokkery plugin! Please make sure you applied the plugin correctly!"
)

internal class ObjectNotSpiedException(obj: Any?) : MokkeryRuntimeException("$obj is not spied by Mokkery!")

internal class ObjectNotMockedException(obj: Any?) : MokkeryRuntimeException("$obj is not mocked by Mokkery!")

internal class NotSingleCallInEveryBlockException : MokkeryRuntimeException("Each 'every' block requires single mock call!")

internal class SuspendingFunctionBlockingCallException : MokkeryRuntimeException(
    message = "Regular function was mocked with suspending call!"
)

internal class ConcurrentTemplatingException : MokkeryRuntimeException(
    "Any concurrent calls involving verify and every are illegal!"
)

internal class DefaultNothingException : MokkeryRuntimeException("This is the default exception for Nothing return type!")

internal class MultipleVarargGenericMatchersException : MokkeryRuntimeException("Using more than one generic vararg matcher is illegal!")

internal class MultipleMatchersForSingleArgException(name: String, matchers: List<ArgMatcher<Any?>>) : MokkeryRuntimeException(
    "Multiple matchers for param '$name' = $matchers"
)

internal class VarargsAmbiguityDetectedException : MokkeryRuntimeException(
    "Varargs matchers registered in a ambiguous way. Pleas read the documentation how to avoid varargs ambiguity or report an issue."
)

internal class NoMoreSequentialAnswersException : MokkeryRuntimeException(
    "No more sequentially defined answers!"
)

internal class MissingMatchersForComposite(
    compositeName: String,
    expected: Int,
    matchers: List<ArgMatcher<*>>
) : MokkeryRuntimeException(
    "`$compositeName` expects $expected matchers, but received ${matchers.size}! You probably used literal in composite matcher, which is illegal! Received matchers: $matchers"
)

internal class MissingSuperMethodException(type: KClass<*>) : MokkeryRuntimeException(
    """
        Super call for ${type.simpleName} could not be performed! Make sure that: 
            1. In case of direct super calls (call to original/default implementation of mocked method), there is any existing implementation of this method provided in inheritance hierarchy.
            2. In case of indirect super calls, you have them enabled in your Gradle files:
               ```
               mokkery {
                   allowIndirectSuperCalls.set(true)
               }
               ```
            3. It is not super method that is compiled as Java default. Call to Java default method is checked at bytecode level and results in runtime errors, so it is not supported.
            4. It is not a call to indirect supertype that originates from Java (like kotlin.collections.List).
    """.trimIndent()
)


internal class MissingArgsForSuperMethodException(expectedCount: Int, actualCount: Int) : MokkeryRuntimeException(
    "Super call requires $expectedCount arguments but $actualCount provided!"
)
