package stock.me.service

import kotlinx.serialization.json.JsonElement
import org.elasticsearch.client.RestHighLevelClient

interface EntitySearchService {
    fun getSuggestions(restClient: RestHighLevelClient, query: String): List<JsonElement>
}