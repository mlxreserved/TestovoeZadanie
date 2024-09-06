package com.example.domain.model.coordinate

import kotlinx.serialization.Serializable

@Serializable
data class FeatureMemberDomain(
    val GeoObject: GeoObjectDomain
)