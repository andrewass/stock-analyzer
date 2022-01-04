package stock.me.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import kotlinx.coroutines.delay
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer
import stock.me.model.Stock

class DefaultEntityPopulatorService(
    private val restClient: RestHighLevelClient,
    private val elasticsearchClient: ElasticsearchClient,
    private val stockConsumer: StockConsumer
) : EntityPopulatorService {

    private val logger = LoggerFactory.getLogger(DefaultEntityPopulatorService::class.simpleName)

    override suspend fun populateStocksByTickerSymbol() {
        logger.info("Populating stocks by ticker symbol : Started")
        val exchanges = getStockExchanges()
        logger.info("Fetched ${exchanges.size} exchanges")

        exchanges.forEach { exchange ->
            stockConsumer.getAllStocksFromExchange(exchange).also { stocks ->
                logger.info("Fetched ${stocks.size} stocks from exchange $exchange")
                if (stocks.isNotEmpty()) {
                    if (indexExists()) {
                        deleteDocumentsFromIndex(exchange)
                    }
                    indexDocuments(stocks, exchange)
                }
            }
            delay(60000L)
        }
        logger.info("Populating stocks by ticker symbol : Completed")
        delay(60000L)
    }

    override suspend fun getStockExchanges(): List<String> {
        return this::class.java.getResourceAsStream("/domain/stockexchanges.csv")
            .bufferedReader()
            .readLines()
            .map { it.split(",") }
            .map { it[0] }
    }

    private fun deleteDocumentsFromIndex(exchange: String) {
        try {
            elasticsearchClient.deleteByQuery { request ->
                request.index("stocks")
                    .query { query ->
                        query.match { match ->
                            match.field("exchange")
                            match.query { it.stringValue(exchange) }
                        }
                    }
            }.also { logger.info("Deleted ${it.deleted()} documents from exchange $exchange") }
        } catch (e: Exception) {
            logger.error("Unable to delete documents : ${e.stackTraceToString()}")
        }
    }

    private fun indexExists(): Boolean =
        elasticsearchClient.indices().exists { it.index("stocks") }.value()


    private fun indexDocuments(stocks: List<Stock>, exchange: String) {
        try {
            val bulkRequest = BulkRequest("stocks")
            stocks.forEach { stock ->
                val jsonMap = mapOf(
                    "symbol" to stock.symbol,
                    "description" to stock.description, "exchange" to exchange
                )
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

