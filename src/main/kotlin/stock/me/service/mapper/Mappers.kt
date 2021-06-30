package stock.me.service.mapper

import stock.me.model.Exchange
import stock.me.model.ExchangeEntity
import stock.me.model.Stock
import stock.me.model.StockEntity


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