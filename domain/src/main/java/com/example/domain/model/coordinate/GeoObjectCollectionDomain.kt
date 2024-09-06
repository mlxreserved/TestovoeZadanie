package com.example.domain.model.coordinate

import kotlinx.serialization.Serializable

@Serializable
data class GeoObjectCollectionDomain(
    val featureMember: List<FeatureMemberDomain>
)