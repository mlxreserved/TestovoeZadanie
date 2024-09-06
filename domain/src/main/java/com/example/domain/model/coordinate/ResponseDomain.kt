package com.example.domain.model.coordinate

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDomain(
    val GeoObjectCollection: GeoObjectCollectionDomain
)