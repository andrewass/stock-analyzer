package stock.me.graphql

import com.apurebase.kgraphql.GraphQL
import io.ktor.application.*


fun Application.registerGraphQLSchema(){

    install(GraphQL){
        playground = true
        schema {
            configure {
                objectMapper = objectMapper
            }
            query("Hello"){
                resolver { -> " World" }
            }
            query("Goodnight"){
                resolver { -> " Earth" }
            }
        }
    }
}