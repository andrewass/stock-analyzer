package stock.me.consumer

interface StockConsumer {

    suspend fun getAllStockExchanges()

    fun getAllSymbols()

}