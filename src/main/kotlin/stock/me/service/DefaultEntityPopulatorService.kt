package stock.me.service

import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.elasticsearch.action.DocWriteResponse
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer
import stock.me.model.Stock

class DefaultEntityPopulatorService : EntityPopulatorService {

    private val logger = LoggerFactory.getLogger(DefaultEntityPopulatorService::class.simpleName)

    override suspend fun populateStocksByTickerSymbol(stockConsumer: StockConsumer, restClient: RestHighLevelClient) {
        logger.info("Populating stocks by ticker symbol : Started")
        val exchanges = getStockExchanges()
        logger.info("Fetched ${exchanges.size} exchanges")
        exchanges.forEach {
            val stocks = stockConsumer.getAllStocksFromExchange(it)
            logger.info("\tFetched ${stocks.size} stocks from exchange $it")
            stocks.forEach { stock ->
                insertDocumentToIndex(restClient, stock)
            }
            delay(5000L)
        }
        logger.info("Populating stocks by ticker symbol : Completed")
        delay(30000L)
    }


    override suspend fun getStockExchanges(): List<String> {
        return this::class.java.getResourceAsStream("/domain/stockexchanges.csv")
            .bufferedReader()
            .readLines()
            .map { it.split(",") }
            .map { it[0] }
    }

    private suspend fun insertDocumentToIndex(restClient: RestHighLevelClient, stock: Stock) {
        try {
            val request = IndexRequest("stocks")
            val jsonMap = mapOf("symbol" to stock.symbol, "description" to stock.description)
            request.id(stock.symbol.hashCode().toString())
            request.source(jsonMap)
            request.timeout(TimeValue.timeValueSeconds(10))

            val response = restClient.index(request, RequestOptions.DEFAULT)
            if (response.result == DocWriteResponse.Result.CREATED) {
                logger.info("Added stock ${stock.symbol} to index ${response.index}")
            } else if (response.result == DocWriteResponse.Result.UPDATED) {
                logger.info("Updated stock ${stock.symbol} to index ${response.index}")
            }
        } catch (exception: Exception) {
            logger.warn("Could not post to ES : ${exception.stackTrace}")
        }
        delay(5000L)
    }
}
