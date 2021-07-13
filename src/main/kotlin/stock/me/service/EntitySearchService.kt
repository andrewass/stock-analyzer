package stock.me.service

import org.elasticsearch.client.RestHighLevelClient
import stock.me.model.Stock

interface EntitySearchService {
    fun getSuggestions(restClient: RestHighLevelClient, word: String): List<Stock>
}