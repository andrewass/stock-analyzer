package stock.me.service

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

/**
 * Make services available with dependency injection by using Kodein
 */
fun DI.MainBuilder.bindServices() {

    bind<StockService>() with singleton { StockService() }

}