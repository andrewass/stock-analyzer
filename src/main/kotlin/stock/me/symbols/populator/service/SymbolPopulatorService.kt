package stock.me.symbols.populator.service

interface SymbolPopulatorService {

    suspend fun populateStocksByTickerSymbol()
}