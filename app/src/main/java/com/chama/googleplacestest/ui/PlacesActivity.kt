package com.chama.googleplacestest.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.chama.googleplacestest.R
import com.chama.googleplacestest.data.adapter.PlacesAdapter
import com.chama.googleplacestest.data.model.OperationResult
import com.chama.googleplacestest.data.model.PlaceDTO
import com.chama.googleplacestest.ui.viewmodel.PlacesViewModel
import kotlinx.android.synthetic.main.activity_places.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class PlacesActivity : BaseActivity() {

    companion object {
        fun getStartIntent(context: Context, name: String, user: String): Intent {
            return Intent(context, PlacesActivity::class.java).apply {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)

        val viewModel: PlacesViewModel by viewModel()
        val coords = viewModel.getCoords()
        val title = "Places near ($coords)"

        setupToolbar(tb_main as Toolbar, title)

        viewModel.placesLiveData.observe(this, Observer {
            it?.let {
                when (it.status) {
                    OperationResult.OperationResultStatus.SUCCESS -> {
                        it.result?.run {
                            setVisibility(View.GONE, View.VISIBLE, View.GONE)
                            setRvPlaces(this)
                        }
                    }
                    OperationResult.OperationResultStatus.LOADING -> setVisibility(
                        View.VISIBLE,
                        View.GONE,
                        View.GONE
                    )
                    else -> setVisibility(View.GONE, View.GONE, View.VISIBLE)
                }
            }
        })
    }

    private fun setRvPlaces(placesDTO: List<PlaceDTO>) {
        with(rv_places) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@PlacesActivity,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = PlacesAdapter(placesDTO)
        }
    }

    private fun setVisibility(
        pbLoadingVisibility: Int,
        rvPlacesVisibilty: Int,
        tvErrorVisibility: Int
    ) {
        pb_loading.visibility = pbLoadingVisibility
        rv_places.visibility = rvPlacesVisibilty
        tv_error.visibility = tvErrorVisibility
    }
}