package stock.me.service

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import stock.me.consumer.PolygonConsumer
import stock.me.consumer.StockConsumer

/**
 * Make components available with dependency injection by using Kodein
 */
fun DI.MainBuilder.bindServices() {

    bind<StockService>() with singleton { StockService() }

    bind<StockConsumer>() with singleton { PolygonConsumer() }

    bind<TaskService>() with singleton { DefaultTaskService() }
}