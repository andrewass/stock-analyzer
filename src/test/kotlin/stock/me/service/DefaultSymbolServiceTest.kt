package stock.me.service

import io.ktor.features.*
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchResponseSections
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.search.SearchHits
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import stock.me.service.symbolquery.DefaultSymbolService
import yahoofinance.Stock
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import yahoofinance.quotes.stock.StockQuote
import java.math.BigDecimal

const val AAPL = "AAPL"
private val PRICE_100 = BigDecimal("100")

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DefaultSymbolServiceTest {

    @MockK
    private lateinit var restClient: RestHighLevelClient

    @InjectMockKs
    private lateinit var symbolSearchService: DefaultSymbolService


    @BeforeAll
    private fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic(YahooFinance::class)
    }

    @AfterAll
    private fun teardown() {
        unmockkStatic(YahooFinance::class)
    }

    @Test
    fun shouldCallElasticSearchClientForSymbolSuggestions() {
        every {
            restClient.search(any(), RequestOptions.DEFAULT)
        } returns stubSearchResponse()

        val response = symbolSearchService.getSymbolSuggestions(AAPL)

        verify { restClient.search(any(), any()) }

        assertTrue(response.isEmpty())
    }

    @Test
    fun shouldGetStockQuoteFromRemoteApi() {
        val price = BigDecimal("100")

        every {
            YahooFinance.get(AAPL)
        } returns stubStockResponse()

        val response = symbolSearchService.getStockQuote(AAPL)

        verify { YahooFinance.get(AAPL) }

        assertEquals(AAPL, response.symbol)
        assertEquals("USD", response.currency)
        assertEquals(PRICE_100, response.quote.price)
        assertEquals(PRICE_100, response.quote.previousClose)
        assertEquals(PRICE_100, response.quote.dayHigh)
        assertEquals(PRICE_100, response.quote.dayLow)
        assertEquals(PRICE_100, response.quote.open)
    }

    @Test
    fun shouldThrowExceptionWhenNoQuotesAreFound() {
        every {
            YahooFinance.get(AAPL)
        } returns null

        assertThrows<NotFoundException> {
            symbolSearchService.getStockQuote(AAPL)
        }
    }

    @Test
    fun shouldGetHistoricalQuotes() {
        every {
            YahooFinance.get(AAPL, any(), any(), Interval.DAILY)
        } returns stubStockResponse()
        //symbolSearchService.getHistoricalQuotes(AAPL)
    }

    private fun stubStockResponse(): Stock =
        Stock(AAPL).apply {
            currency = "USD"
            quote = StockQuote(AAPL)
                .apply {
                    price = PRICE_100
                    previousClose = PRICE_100
                    dayHigh = PRICE_100
                    dayLow = PRICE_100
                    open = PRICE_100
                    name = AAPL
                }
        }

    private fun stubSearchResponse(): SearchResponse =
        SearchResponseSections(
            SearchHits(emptyArray(), null, 1.0f), null, null, false, null, null, 0
        ).let { SearchResponse(it, null, 1, 1, 1, 1, null, null) }
}