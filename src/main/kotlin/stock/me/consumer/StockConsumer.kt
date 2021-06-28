package stock.me.consumer

import stock.me.model.Exchange
import stock.me.model.Stock

interface StockConsumer {

    suspend fun getAllStockExchanges(): List<Exchange>

    suspend fun getAllStocks(nextPage : String?) : Pair<List<Stock>, String?>

}