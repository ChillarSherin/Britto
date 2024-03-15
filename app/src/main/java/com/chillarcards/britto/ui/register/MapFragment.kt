package com.chillarcards.britto.ui.register

//import kotlinx.android.synthetic.main.fragment_map.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentMapBinding
import com.chillarcards.britto.di.module.ConfigBuild
import com.chillarcards.britto.utills.Const
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var placesClient: PlacesClient
    lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        Const.enableButton(binding.loginBtn)

        // Initialize the Places SDK
        Places.initialize(requireContext(), ConfigBuild.google_maps_key)
        placesClient = Places.createClient(requireContext())

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

      //            searchPlace()

        binding.loginBtn.setOnClickListener{

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val defaultLocation = LatLng(10.001850, 76.361320)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))

        // Enable user interaction with the map
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun searchPlace() {
        val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val sessionToken = AutocompleteSessionToken.newInstance()

        val request = FindAutocompletePredictionsRequest.builder()
            .setTypeFilter(TypeFilter.ADDRESS)
            .setSessionToken(sessionToken)
            //.setSessionToken(UUID.randomUUID().toString())
            .setQuery(binding.searchEt.text.toString())
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            if (response != null && response.autocompletePredictions.isNotEmpty()) {
                val prediction = response.autocompletePredictions[0]
                placesClient.fetchPlace(FetchPlaceRequest.builder(prediction.placeId, placeFields).build())
                    .addOnSuccessListener { fetchPlaceResponse ->
                        val place = fetchPlaceResponse.place
                        val latLng = place.latLng

                        // Move the camera to the selected location
                        if (latLng != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

                            // Add a marker for the selected place
                            mMap.addMarker(MarkerOptions().position(latLng).title(place.name))
                        }
                    }
                    .addOnFailureListener { exception ->
                        exception.printStackTrace()
                    }
            }
        }.addOnFailureListener { exception ->
            exception.printStackTrace()
        }
    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.map)
    }
}
