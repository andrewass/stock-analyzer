package stockcomp.client.backend.symbols.populate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import org.kodein.di.instance
import stockcomp.client.backend.config.kodein
import stockcomp.client.backend.symbols.populate.service.SymbolPopulatorService


fun initStockTasks() {
    val entityPopulatorService by kodein.instance<SymbolPopulatorService>()
    CoroutineScope(Default).launch {
        while (true) {
            entityPopulatorService.populateStocksByTickerSymbol()
        }
    }
}
