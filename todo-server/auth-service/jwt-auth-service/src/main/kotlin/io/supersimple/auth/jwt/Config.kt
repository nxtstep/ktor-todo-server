package io.supersimple.auth.jwt

import com.auth0.jwt.algorithms.Algorithm

data class Config(
    val jwtAlgorithm: Algorithm,
    val issuer: String,
    val audience: String,
    val myRealm: String
) {
    companion object {
        const val CLAIM_USERNAME = "username"
    }
}
