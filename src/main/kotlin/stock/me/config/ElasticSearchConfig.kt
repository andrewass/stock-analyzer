package stock.me.config

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.rest_client.RestClientTransport
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient

val httpHost = HttpHost(
    System.getenv("ELASTIC_SEARCH"),
    System.getenv("ELASTIC_SEARCH_PORT").toInt(),
    "http"
)

val credentialsProvider = BasicCredentialsProvider()
    .also {
        it.setCredentials(
            AuthScope.ANY,
            UsernamePasswordCredentials(System.getenv("ES_USERNAME"), System.getenv("ES_PASSWORD"))
        )
    }

fun getRestHighLevelClient() = RestHighLevelClient(
    RestClient.builder(httpHost)
        .setHttpClientConfigCallback { it.setDefaultCredentialsProvider(credentialsProvider) }
)

fun getElasticSearchClient(): ElasticsearchClient =
    RestClient.builder(httpHost)
        .setHttpClientConfigCallback { it.setDefaultCredentialsProvider(credentialsProvider) }.build()
        .let { RestClientTransport(it, JacksonJsonpMapper()) }
        .let { ElasticsearchClient(it) }
