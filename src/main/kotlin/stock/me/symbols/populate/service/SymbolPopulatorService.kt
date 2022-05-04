package stock.me.symbols.populate.service

interface SymbolPopulatorService {

    suspend fun populateStocksByTickerSymbol()
}