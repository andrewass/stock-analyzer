package stock.me.service.mapper

import stock.me.model.Exchange
import stock.me.model.ExchangeEntity


fun toExchangeEntity(exchange: Exchange) =
    ExchangeEntity.new {
        market = exchange.market
        type = exchange.type
        fullName = exchange.name
        idCode = exchange.mic
    }