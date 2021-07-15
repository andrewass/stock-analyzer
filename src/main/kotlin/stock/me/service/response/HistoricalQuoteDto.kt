package stock.me.service.response

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
class HistoricalQuoteDto(
    val price : Double,
    @Serializable(with = LocalDateIso8601Serializer::class)
    val date : LocalDate,
    val volume : Long
)