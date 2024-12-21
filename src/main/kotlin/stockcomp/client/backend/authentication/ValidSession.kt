package stockcomp.client.backend.authentication

import kotlinx.serialization.Serializable

@Serializable
data class ValidSession(
    val validSession: Boolean
)
