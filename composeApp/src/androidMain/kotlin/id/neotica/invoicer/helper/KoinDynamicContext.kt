package id.neotica.invoicer.helper

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.KoinContext
import org.koin.core.error.DefinitionOverrideException
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

@Composable
fun KoinDynamicContext(
    context: Context,
    content: @Composable () -> Unit,
) {
    KoinContext(
        context = remember {

            KoinPlatformTools.defaultContext().get().apply {
                try {
                    loadModules(
                        modules = listOf(module {
                            single(named("context")) { context }
                        }),
                        allowOverride = false,
                        createEagerInstances = false,
                    )

                } catch (_: DefinitionOverrideException) {
                }
            }
        },
        content = content,
    )
}