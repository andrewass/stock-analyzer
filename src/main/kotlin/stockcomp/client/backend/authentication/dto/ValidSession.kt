package stockcomp.client.backend.authentication.dto

import kotlinx.serialization.Serializable

@Serializable
data class ValidSession(
    val validSession: Boolean
)
