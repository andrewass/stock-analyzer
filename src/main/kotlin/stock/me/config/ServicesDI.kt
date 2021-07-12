package stock.me.config

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import stock.me.consumer.PolygonConsumer
import stock.me.consumer.StockConsumer
import stock.me.service.*

/**
 * Make components available with dependency injection by using Kodein
 */
fun DI.MainBuilder.bindServices() {

    bind<StockConsumer>() with singleton { PolygonConsumer() }

    bind<EntityPopulatorService>() with singleton { DefaultEntityPopulatorService() }
}