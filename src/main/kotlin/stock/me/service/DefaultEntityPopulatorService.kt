package stock.me.service

import kotlinx.coroutines.delay
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.reindex.DeleteByQueryRequest
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer
import stock.me.model.Stock

class DefaultEntityPopulatorService : EntityPopulatorService {

    private val logger = LoggerFactory.getLogger(DefaultEntityPopulatorService::class.simpleName)

    override suspend fun populateStocksByTickerSymbol(stockConsumer: StockConsumer, restClient: RestHighLevelClient) {
        logger.info("Populating stocks by ticker symbol : Started")
        val exchanges = getStockExchanges()
        logger.info("Fetched ${exchanges.size} exchanges")
        exchanges.forEach { exchange ->
            stockConsumer.getAllStocksFromExchange(exchange).also { stocks ->
                logger.info("\tFetched ${stocks.size} stocks from exchange $exchange")
                if (stocks.isNotEmpty()) {
                    deleteDocumentsFromIndex(restClient, exchange)
                    indexDocuments(restClient, stocks, exchange)
                }
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

    private fun deleteDocumentsFromIndex(restClient: RestHighLevelClient, exchange: String) {
        try {
            val request = DeleteByQueryRequest("stocks")
            request.setQuery(QueryBuilders.matchQuery("exchange", exchange))
            val response = restClient.deleteByQuery(request, RequestOptions.DEFAULT)
            logger.info("Deleted ${response.deleted} documents from exchange $exchange")
        } catch (e: Exception) {
            logger.warn("Unable to delete documents : ${e.stackTraceToString()}")
        }
    }

    private fun indexDocuments(restClient: RestHighLevelClient, stocks: List<Stock>, exchange: String) {
        try {
            val bulkRequest = BulkRequest("stocks")
            stocks.forEach { stock ->
                val jsonMap = mapOf("symbol" to stock.symbol,
                    "description" to stock.description, "exchange" to exchange)
                val indexRequest = IndexRequest()
                    .id(stock.symbol.hashCode().toString())
                    .source(jsonMap)
                bulkRequest.add(indexRequest)
            }
            restClient.bulk(bulkRequest, RequestOptions.DEFAULT)
        } catch (exception: Exception) {
            logger.warn("Could not index documents : ${exception.stackTraceToString()}")
        }
    }
}
