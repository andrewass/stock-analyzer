package stock.me.service

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import stock.me.model.Stock

class DefaultEntitySearchService : EntitySearchService {

    override fun getSuggestions(restClient: RestHighLevelClient, word: String): List<Stock> {
        val request = SearchRequest()
        val ssb = SearchSourceBuilder()
        ssb.query(QueryBuilders.matchAllQuery())
        request.source(ssb)

        val response = restClient.search(request, RequestOptions.DEFAULT)

        return response.hits.hits
            .map { it.sourceAsString }
            .map { Json.decodeFromString(it) }
    }
}