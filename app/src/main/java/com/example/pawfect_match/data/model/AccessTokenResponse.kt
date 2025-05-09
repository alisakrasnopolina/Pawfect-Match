package com.example.pawfect_match.data.model

data class AccessTokenResponse(
    val token_type: String,
    val expires_in: Int,
    val access_token: String
)
