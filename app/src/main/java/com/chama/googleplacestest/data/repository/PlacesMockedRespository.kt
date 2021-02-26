package com.chama.googleplacestest.data.repository

import com.chama.googleplacestest.data.model.OperationResult
import com.chama.googleplacestest.data.model.PlaceDTO

class PlacesMockedRespository {
    fun getPlaces() : OperationResult<List<PlaceDTO>> {
        val places = listOf<PlaceDTO>(
            PlaceDTO(
                "http://maps.gstatic.com/mapfiles/place_api/icons/travel_agent-71.png",
                100,
                100,
                "Pyrmont Bay Wharf Darling Dr, Sydney",
                "Rhythmboat Cruises"
            ),
            PlaceDTO(
                "http://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png",
                100,
                100,
                "Australia",
                "Private Charter Sydney Habour Cruise"
            ),
            PlaceDTO(
                "http://maps.gstatic.com/mapfiles/place_api/icons/travel_agent-71.png",
                100,
                100,
                "32 The Promenade, King Street Wharf 5, Sydney",
                "Australian Cruise Group"
            )
        )
        return OperationResult<List<PlaceDTO>>(
            places,
            OperationResult.OperationResultStatus.SUCCESS
        )
    }
}