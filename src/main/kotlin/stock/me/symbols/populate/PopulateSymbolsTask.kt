package stock.me.symbols.populate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.symbols.populate.service.SymbolPopulatorService


fun initStockTasks() {
    val entityPopulatorService by kodein.instance<SymbolPopulatorService>()
    CoroutineScope(Default).launch {
        while (true) {
            entityPopulatorService.populateStocksByTickerSymbol()
        }
    }
}
