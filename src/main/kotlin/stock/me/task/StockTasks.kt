package stock.me.task

import io.ktor.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.service.symbolpopulator.SymbolPopulatorService

suspend fun Application.initStockTasks() {

    val entityPopulatorService by kodein.instance<SymbolPopulatorService>()

    CoroutineScope(Default).launch {
        while (true) {
            entityPopulatorService.populateStocksByTickerSymbol()
        }
    }
}
