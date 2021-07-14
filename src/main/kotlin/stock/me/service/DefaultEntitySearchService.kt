package stock.me.service

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.RegexpFlag
import org.elasticsearch.search.builder.SearchSourceBuilder

class DefaultEntitySearchService : EntitySearchService {

    override fun getSuggestions(restClient: RestHighLevelClient, query: String): List<JsonElement> {
        val request = SearchRequest("stocks")
        val ssb = SearchSourceBuilder()
        ssb.query(createBoolQuery(query))
        request.source(ssb)
        val response = restClient.search(request, RequestOptions.DEFAULT)

        return response.hits.hits
            .map { it.sourceAsString }
            .map { Json.parseToJsonElement(it) }
    }

    private fun createBoolQuery(query: String) =
        BoolQueryBuilder()
            .should(
                QueryBuilders.regexpQuery("symbol", ".*$query.*")
                    .caseInsensitive(true)
                    .flags(RegexpFlag.ALL)
            )
            .should(
                QueryBuilders.regexpQuery("description", ".*$query.*")
                    .caseInsensitive(true)
                    .flags(RegexpFlag.ALL)
            )
}