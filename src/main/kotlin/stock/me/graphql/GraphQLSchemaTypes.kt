package stock.me.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import stock.me.provider.response.HistoricalQuoteDto
import stock.me.provider.response.StockDto
import stock.me.provider.response.StockInformationDto
import stock.me.provider.response.StockQuoteDto

fun createSchemaTypes(builder: SchemaBuilder){

    builder.type<StockDto>(){
        description = "Root entity of stock data for a given symbol"
    }

    builder.type<StockInformationDto>(){
        description = "General information of a stock for a given symbol"
    }

    builder.type<StockQuoteDto> {
        description = "Stock quote containing price information for a given symbol"
    }

    builder.type<HistoricalQuoteDto> {
        description = "List of historical stock quotes for a given symbol"
    }
}