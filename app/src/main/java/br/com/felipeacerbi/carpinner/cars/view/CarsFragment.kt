package br.com.felipeacerbi.carpinner.cars.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import br.com.felipeacerbi.carpinner.R
import br.com.felipeacerbi.carpinner.cars.service.model.Car
import br.com.felipeacerbi.carpinner.cars.view.adapter.CarsListAdapter
import br.com.felipeacerbi.carpinner.cars.viewmodel.CarsViewModel.Action.RequestCars
import br.com.felipeacerbi.carpinner.cars.viewmodel.CarsViewModelImpl
import br.com.felipeacerbi.common.extension.observe
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_DRAGGING
import kotlinx.android.synthetic.main.bottom_sheet_cars_list.*
import kotlinx.android.synthetic.main.fragment_cars.*
import org.koin.android.viewmodel.ext.android.viewModel

class CarsFragment : Fragment(), OnMapReadyCallback {

    private val carsViewModel by viewModel<CarsViewModelImpl>()

    private lateinit var maps: GoogleMap
    private lateinit var bottomSheetList: BottomSheetBehavior<*>

    private val markerList: MutableList<Marker> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.change_image_transform)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMap()
        setUpBottomSheet()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        maps = googleMap

        observe(carsViewModel.getStateStream()) {
            placeCarMarkers(it.carsList, it.moveCamera)
            tv_cars_quantity.text = it.carsList.size.toString()
            rv_cars_list.adapter = CarsListAdapter(it.carsList.toMutableList()) { car ->
                handleCarClick(car)
            }
        }

        carsViewModel.perform(RequestCars)
    }

    private fun loadMap() {
        val mapsFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.map)

        if(mapsFragment is SupportMapFragment) {
            mapsFragment.getMapAsync(this)
        }
    }

    private fun placeCarMarkers(cars: List<Car>, moveCamera: Boolean) {
        cars.forEach {
            markerList.add(maps.addMarker(
                MarkerOptions()
                    .position(LatLng(it.latitude, it.longitude))
                    .title(it.name)
                    .snippet(getString(R.string.list_snippet, it.modelName, it.licensePlate))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))))
        }

        if (moveCamera) moveCamera(
            cars.lastOrNull()?.latitude?: DEFAULT_LAT,
            cars.lastOrNull()?.longitude ?: DEFAULT_LONG, DEFAULT_ZOOM)
    }

    private fun handleCarClick(car: Car) {
        bottomSheetList.state = STATE_COLLAPSED

        markerList.firstOrNull { it.title == car.name }?.showInfoWindow()

        moveCamera(car.latitude, car.longitude, DEFAULT_ZOOM)
    }

    private fun setUpBottomSheet() {
        bottomSheetList = BottomSheetBehavior.from(bottom_sheet_list).apply {
            setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(view: View, p1: Float) {}
                override fun onStateChanged(view: View, state: Int) {
                    if (state != STATE_DRAGGING) {
                        tv_header_subtitle.text = getString(
                            if (state == STATE_COLLAPSED)
                                R.string.swipe_up_to_reveal else
                                R.string.swipe_down_to_collapse
                        )
                    }
                }
            })
        }
    }

    private fun moveCamera(lat: Double, long: Double, zoom: Float) {
        maps.moveCamera(CameraUpdateFactory.zoomTo(zoom))
        maps.animateCamera(CameraUpdateFactory.newLatLng(LatLng(lat, long)))
    }

    companion object {
        const val DEFAULT_LAT = 0.0
        const val DEFAULT_LONG = 0.0
        const val DEFAULT_ZOOM = 12F
    }
}