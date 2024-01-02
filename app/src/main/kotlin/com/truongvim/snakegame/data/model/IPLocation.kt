// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json       = Json { allowStructuredMapKeys = true }
// val iPLocation = json.parse(IPLocation.serializer(), jsonString)

package com.truongvim.snakegame.data.model

import kotlinx.serialization.*

@Serializable
data class IPLocation (
    val status: String? = null,
    val country: String? = null,
    val countryCode: String? = null,
    val region: String? = null,
    val regionName: String? = null,
    val city: String? = null,
    val zip: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val isp: String? = null,
    val org: String? = null,

    @SerialName("as")
    val ipLocationAs: String? = null,

    val query: String? = null
)
