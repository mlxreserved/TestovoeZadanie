package com.example.data2.storage.model.coordinate

import kotlinx.serialization.Serializable

@Serializable
data class GeoObjectCollection(
    val featureMember: List<FeatureMember>
)