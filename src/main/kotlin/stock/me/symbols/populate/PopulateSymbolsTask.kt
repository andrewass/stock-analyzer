package stock.me.symbols.populate

import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.symbols.populate.service.SymbolPopulatorService


suspend fun Application.initStockTasks() {

    val entityPopulatorService by kodein.instance<SymbolPopulatorService>()

    CoroutineScope(Default).launch {
        while (true) {
            entityPopulatorService.populateStocksByTickerSymbol()
        }
    }
}
