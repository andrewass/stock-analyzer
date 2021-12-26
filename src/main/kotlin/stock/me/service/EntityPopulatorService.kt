package stock.me.service

import org.elasticsearch.client.RestHighLevelClient
import stock.me.consumer.StockConsumer

interface EntityPopulatorService {

    suspend fun getStockExchanges() : List<String>

    suspend fun populateStocksByTickerSymbol()
}