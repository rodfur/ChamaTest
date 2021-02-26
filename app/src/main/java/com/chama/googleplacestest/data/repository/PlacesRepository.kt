package com.chama.googleplacestest.data.repository

import com.chama.googleplacestest.data.RetrofitGooglePlacesInstance
import com.chama.googleplacestest.data.model.NearbySearchResponse
import com.chama.googleplacestest.data.model.OperationResult
import com.chama.googleplacestest.data.model.PlaceDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlacesRepository {
    fun getPlaces(
        location: String,
        radius: Int,
        type: String,
        key: String
    ): OperationResult<List<PlaceDTO>> {
        var operationResult =
            OperationResult<List<PlaceDTO>>(
                status = OperationResult.OperationResultStatus.UNDEFINED
            )

        RetrofitGooglePlacesInstance.service.getNearbyPlaces(location, radius, type, key)
            .enqueue(object : Callback<NearbySearchResponse> {
                override fun onResponse(
                    call: Call<NearbySearchResponse>,
                    response: Response<NearbySearchResponse>
                ) {
                    when {
                        response.isSuccessful -> {
                            var places = mutableListOf<PlaceDTO>()
                            response.body()?.let { response ->
                                response.results?.let {
                                    for (result in it) {
                                        var photoUrl: String? = null
                                        var photoWidth: Int? = null
                                        var photoHeight: Int? = null

                                        if (result.photos.count() > 0) {
                                            val firstPhoto = result.photos[0]
                                            photoUrl = firstPhoto.photoReference
                                            photoWidth = firstPhoto.width as Int
                                            photoHeight = firstPhoto.height as Int
                                        }
                                        places.add(
                                            PlaceDTO(
                                                photoUrl,
                                                photoWidth,
                                                photoHeight,
                                                result.vicinity,
                                                result.name
                                            )
                                        )
                                    }
                                }
                            }
                            operationResult =
                                OperationResult(
                                    places.toList(),
                                    OperationResult.OperationResultStatus.SUCCESS
                                )
                        }
                        response.code() == 401 -> operationResult =
                            OperationResult<List<PlaceDTO>>(
                                status = OperationResult.OperationResultStatus.NOT_AUTHORIZED
                            )
                        else -> operationResult =
                            OperationResult<List<PlaceDTO>>(
                                status = OperationResult.OperationResultStatus.ERROR
                            )
                    }
                }
                override fun onFailure(call: Call<NearbySearchResponse>, t: Throwable) {
                    operationResult =
                        OperationResult<List<PlaceDTO>>(
                            status = OperationResult.OperationResultStatus.ERROR
                        )
                }
            })
        return operationResult
    }
}