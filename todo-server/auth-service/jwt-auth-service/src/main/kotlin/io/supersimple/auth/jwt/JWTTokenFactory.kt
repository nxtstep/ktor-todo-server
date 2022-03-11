package io.supersimple.auth.jwt

import com.auth0.jwt.JWT
import io.supersimple.auth.model.User
import io.supersimple.auth.spi.UserTokenFactory
import java.util.Date

typealias TimeInMillisProvider = () -> Long

class JWTTokenFactory(
    private val config: Config,
    private val expirationInterval: Int = 3_600_000 * 24, // 24 hours,
    private val currentTimeProvider: TimeInMillisProvider = System::currentTimeMillis,
) : UserTokenFactory {
    override fun generateToken(user: User): String = with(config) {
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withSubject(user.id.value.toString())
            .withClaim(Config.CLAIM_USERNAME, user.name)
            .withExpiresAt(expiresAt())
            .sign(jwtAlgorithm)
    }

    private fun expiresAt() = Date(currentTimeProvider() + expirationInterval)
}
