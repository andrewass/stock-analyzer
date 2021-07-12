package stock.me.consumer

import stock.me.model.Stock

interface StockConsumer {


    suspend fun getAllStocks(nextPage: String?): Pair<List<Stock>, String?>

}