package com.chama.googleplacestest.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chama.googleplacestest.R
import com.chama.googleplacestest.data.model.OperationResult
import com.chama.googleplacestest.data.model.PlaceDTO
import com.chama.googleplacestest.data.FetchPlacesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PlacesViewModel(
    private val application: Application,
    private val fetchPlacesUseCase: FetchPlacesUseCase
) : ViewModel() {
    val placesLiveData = MutableLiveData<OperationResult<List<PlaceDTO>>>().apply {
        if (this.value == null) {
            val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
            ioScope.launch {
                try {
                    this@apply.postValue(getOperationLoading())
                    application.applicationContext.run {
                        val coords = getCoords()
                        val radius: Int = R.integer.radius
                        val type: String = getString(R.string.nearBySearchTypes)
                        val key: String = getString(R.string.googleApiKey)

                        this@apply.postValue(fetchPlacesUseCase.execute(coords, radius, type, key))
                    }
                } catch (e: Exception) {
                    this@apply.postValue(getOperationResultError())
                }
            }
        }
    }

    fun getCoords(): String {
        val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED
        val context = application.applicationContext

        if (ActivityCompat.checkSelfPermission(context, FINE_LOCATION) == PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(context, COARSE_LOCATION) == PERMISSION_GRANTED
        ) {
            val lm = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location: Location =
                lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val longitude: String = location.getLongitude().toString().substring(5)
            val latitude: String = location.getLatitude().toString().substring(5)

            return "$longitude..., $latitude..."
        } else {
            return context.getString(R.string.defaultCoordinates)
        }
    }

    private fun getOperationLoading(): OperationResult<List<PlaceDTO>> =
        OperationResult(status = OperationResult.OperationResultStatus.LOADING)

    private fun getOperationResultError(): OperationResult<List<PlaceDTO>> =
        OperationResult(status = OperationResult.OperationResultStatus.ERROR)
}
