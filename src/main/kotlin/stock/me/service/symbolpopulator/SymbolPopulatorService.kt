package stock.me.service.symbolpopulator

interface SymbolPopulatorService {

    suspend fun populateStocksByTickerSymbol()
}