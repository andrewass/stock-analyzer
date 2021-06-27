package stock.me.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import stock.me.model.Exchanges
import stock.me.model.Stocks

const val HIKARI_CONFIG_KEY = "ktor.hikariconfig"

fun Application.initDatabase(){
    val configPath = environment.config.property(HIKARI_CONFIG_KEY).getString()
    val databaseConfig = HikariConfig(configPath)
    val dataSource = HikariDataSource(databaseConfig)

    Database.connect(dataSource)
    createTables()
    LoggerFactory.getLogger(Application::class.simpleName).info("Initialized Database")
}

private fun createTables() = transaction {
    SchemaUtils.create(
        Stocks, Exchanges
    )
}