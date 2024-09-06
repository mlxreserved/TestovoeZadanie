package com.example.domain.model.coordinate

import kotlinx.serialization.Serializable

@Serializable
data class GeoObjectDomain(
    val Point: PointDomain
)