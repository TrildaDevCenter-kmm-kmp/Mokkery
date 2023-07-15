package dev.mokkery.internal.tracing

import dev.mokkery.internal.callToString
import dev.mokkery.internal.generateSignature
import dev.mokkery.internal.templating.CallTemplate
import dev.mokkery.internal.toListOrNull

internal data class CallTrace(
    val receiver: String,
    val name: String,
    val args: List<CallArg>,
    val orderStamp: Long,
) {

    val signature: String by lazy { generateSignature(name, args) }

    override fun toString(): String = callToString(receiver, name, args)
}

internal infix fun CallTrace.matches(template: CallTemplate): Boolean {
    return receiver == template.receiver &&
            signature == template.signature &&
            args.all { arg -> template.matchers[arg.name]?.matches(arg.value) ?: false }
}

internal infix fun CallTrace.doesNotMatch(template: CallTemplate): Boolean = matches(template).not()
