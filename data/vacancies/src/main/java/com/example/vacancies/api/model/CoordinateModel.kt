package com.example.vacancies.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val response: Response
)

@Serializable
data class Response(
    val GeoObjectCollection: GeoObjectCollection
)

@Serializable
data class GeoObjectCollection(
    val featureMember: List<FeatureMember>
)

@Serializable
data class FeatureMember(
    val GeoObject: GeoObject
)

@Serializable
data class GeoObject(
    val Point: Point
)

@Serializable
data class Point(
    var pos: String
)