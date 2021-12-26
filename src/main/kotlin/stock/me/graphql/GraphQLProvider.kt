package stock.me.graphql

import graphql.GraphQL
import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeRuntimeWiring

class GraphQLProvider(
    private val dataFetchers: GraphQLDataFetchers
) {
    private lateinit var graphQL: GraphQL

    init {
        graphQL = this::class.java.getResource("/graphql/schema.graphqls")
            .let { buildSchema(it!!.toString()) }
            .let { GraphQL.newGraphQL(it).build() }
    }

    private fun buildSchema(sdl: String): GraphQLSchema {
        val typeRegistry = SchemaParser().parse(sdl)

        return SchemaGenerator()
            .makeExecutableSchema(typeRegistry, buildRuntimeWiring())
    }

    private fun buildRuntimeWiring() = RuntimeWiring.newRuntimeWiring()
        .type(
            TypeRuntimeWiring.newTypeWiring("Quote")
                .dataFetcher("quoteBySymbol", dataFetchers.stockQuoteDataFetcher())
        )
        .type(
            TypeRuntimeWiring.newTypeWiring("Symbol")
        )
        .build()


    fun getGraphQL() = graphQL
}