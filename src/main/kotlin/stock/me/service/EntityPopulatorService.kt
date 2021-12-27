package stock.me.service

interface EntityPopulatorService {

    suspend fun getStockExchanges(): List<String>

    suspend fun populateStocksByTickerSymbol()
}