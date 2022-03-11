package io.supersimple.common.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID

@Serializer(forClass = UUID::class)
public object UUIDSerializer : KSerializer<UUID> {
    public override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    public override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }
}
