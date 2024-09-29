package com.example.data2.repository

import com.example.data2.storage.geocoderStorage.GeocoderStorage
import com.example.data2.storage.model.coordinate.Coordinate
import com.example.data2.storage.model.coordinate.FeatureMember
import com.example.data2.storage.model.coordinate.GeoObject
import com.example.data2.storage.model.coordinate.GeoObjectCollection
import com.example.data2.storage.model.coordinate.Point
import com.example.data2.storage.model.coordinate.Response
import com.example.data2.storage.services.GeocoderService
import com.example.data2.storage.services.NetworkService
import com.example.domain.model.coordinate.CoordinateDomain
import com.example.domain.model.coordinate.FeatureMemberDomain
import com.example.domain.model.coordinate.GeoObjectCollectionDomain
import com.example.domain.model.coordinate.GeoObjectDomain
import com.example.domain.model.coordinate.PointDomain
import com.example.domain.model.coordinate.ResponseDomain
import com.example.domain.repository.GeocoderRepository
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor(private val geocoderStorage: GeocoderStorage): GeocoderRepository {
    override suspend fun getCoordinate(city: String): CoordinateDomain {
        val res = geocoderStorage.getCoordinate(city)
        return mapToCoordinateDomain(res)
    }

    private fun mapToCoordinateDomain(coordinate: Coordinate): CoordinateDomain{
        return CoordinateDomain(
            response = mapToResponseDomain(coordinate.response)
        )
    }

    private fun mapToResponseDomain(response: Response): ResponseDomain{
        return ResponseDomain(
            GeoObjectCollection = mapToGeoObjectCollectionDomain(response.GeoObjectCollection)
        )
    }

    private fun mapToGeoObjectCollectionDomain(geoObjectCollection: GeoObjectCollection): GeoObjectCollectionDomain{
        return GeoObjectCollectionDomain(
            featureMember = geoObjectCollection.featureMember.map { mapToFeatureMemberDomain(it) }
        )
    }

    private fun mapToFeatureMemberDomain(featureMember: FeatureMember): FeatureMemberDomain {
        return FeatureMemberDomain(
            GeoObject = mapToGeoObjectDomain(featureMember.GeoObject)
        )
    }

    private fun mapToGeoObjectDomain(geoObject: GeoObject): GeoObjectDomain{
        return GeoObjectDomain(
            Point = mapToPointDomain(geoObject.Point)
        )
    }

    private fun mapToPointDomain(point: Point): PointDomain{
        return PointDomain(
            pos = point.pos
        )
    }

}