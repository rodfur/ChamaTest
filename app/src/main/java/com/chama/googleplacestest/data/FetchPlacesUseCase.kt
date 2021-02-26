package com.chama.googleplacestest.data

import com.chama.googleplacestest.data.model.OperationResult
import com.chama.googleplacestest.data.model.PlaceDTO
import com.chama.googleplacestest.data.repository.PlacesRepository

class FetchPlacesUseCase(private val placesRespository: PlacesRepository) {
    fun execute(
        location: String,
        radius: Int,
        type: String,
        key: String
    ): OperationResult<List<PlaceDTO>> {
        return placesRespository.getPlaces(location, radius, type, key)
    }
}