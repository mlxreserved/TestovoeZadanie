package com.example.data2.storage.model.coordinate

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val GeoObjectCollection: GeoObjectCollection
)