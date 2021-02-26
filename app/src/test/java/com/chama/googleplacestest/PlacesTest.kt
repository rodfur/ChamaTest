package com.chama.googleplacestest

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chama.googleplacestest.data.FetchPlacesUseCase
import com.chama.googleplacestest.data.model.OperationResult
import com.chama.googleplacestest.data.model.PlaceDTO
import com.chama.googleplacestest.ui.viewmodel.PlacesViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class PlacesTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var viewModel: PlacesViewModel
    private lateinit var placesUseCase: FetchPlacesUseCase
    private lateinit var application: Application

    @Before
    fun init() {
        placesUseCase = mock()
        application = mock()
        viewModel = PlacesViewModel(application, placesUseCase)
    }

    @Test
    fun get() = runBlocking {
        val places = listOf(
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
        val operationResult =
            OperationResult<List<PlaceDTO>>(places, OperationResult.OperationResultStatus.SUCCESS)

        val location = "0.0,0.0"
        val radius = 1500
        val type = "bar, restaurant, cafe"
        val key = "XXXXAPIKEYXXXX"

        Mockito.`when`(placesUseCase.execute(location, radius, type, key))
            .thenReturn(operationResult)

        val result = placesUseCase.execute(location, radius, type, key)

        if (operationResult.status == OperationResult.OperationResultStatus.SUCCESS) {
            viewModel.placesLiveData.postValue(operationResult)
        }
        viewModel.placesLiveData.observeForever { }
        assert(viewModel.placesLiveData != null)
    }
}
