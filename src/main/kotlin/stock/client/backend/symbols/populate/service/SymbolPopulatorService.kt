package stock.client.backend.symbols.populate.service

interface SymbolPopulatorService {

    suspend fun populateStocksByTickerSymbol()
}