package stock.me.service.mapper

import stock.me.model.*


fun toExchangeEntity(exchange: Exchange) =
    ExchangeEntity.new {
        market = exchange.market
        type = exchange.type
        fullName = exchange.name
        idCode = exchange.mic
    }

fun toStockEntity(stock: Stock) =
    StockEntity.new {
        ticker = stock.ticker
        exchange = stock.exchange
        name = stock.name
    }

fun toStockFinancialEntity(stockFinancial: StockFinancial) =
    StockFinancialEntity.new {
        ticker = stockFinancial.ticker
    }

fun toDividendEntity(dividend: Dividend){

}