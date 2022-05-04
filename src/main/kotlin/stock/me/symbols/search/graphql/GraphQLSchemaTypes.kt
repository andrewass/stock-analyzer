package stock.me.symbols.search.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import stock.me.symbols.model.*

fun createSchemaTypes(builder: SchemaBuilder){
    builder.type<Stock> {
        description = "Root entity of stock data for a given symbol"
    }

    builder.type<StockStats> {
        description = "General information of a stock for a given symbol"
    }

    builder.type<StockQuote> {
        description = "Stock quote containing price information for a given symbol"
    }

    builder.type<HistoricalQuote> {
        description = "List of historical stock quotes for a given symbol"
    }
}