package stock.me.service

interface EntityPopulatorService {

    suspend fun populateStocksByTickerSymbol()
}