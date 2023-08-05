package dev.mokkery.plugin.core

import dev.mokkery.plugin.core.ClassResolver
import dev.mokkery.plugin.core.CompilerPluginScope
import dev.mokkery.plugin.core.FunctionResolver
import dev.mokkery.plugin.core.TransformerScope
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid

abstract class CoreTransformer(
    compilerPluginScope: CompilerPluginScope
) : TransformerScope, IrElementTransformerVoid() {
    final override lateinit var currentFile: IrFile
        private set
    override val classes = mutableMapOf<ClassResolver, IrClass>()
    override val functions = mutableMapOf<FunctionResolver, IrSimpleFunction>()
    override val compilerConfig: CompilerConfiguration = compilerPluginScope.compilerConfig
    override val pluginContext: IrPluginContext = compilerPluginScope.pluginContext

    override fun visitFile(declaration: IrFile): IrFile {
        currentFile = declaration
        return super.visitFile(declaration)
    }
}
