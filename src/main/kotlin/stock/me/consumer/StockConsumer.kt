package stock.me.consumer

import stock.me.model.Dividend
import stock.me.model.HistoricFinancial
import stock.me.model.Exchange
import stock.me.model.Stock

interface StockConsumer {

    suspend fun getAllStockExchanges(): List<Exchange>

    suspend fun getAllStocks(nextPage : String?) : Pair<List<Stock>, String?>

    suspend fun getDividendsForStock(ticker: String) : List<Dividend>

    suspend fun getHistoricalFinancials(ticker: String) : List<HistoricFinancial>
}