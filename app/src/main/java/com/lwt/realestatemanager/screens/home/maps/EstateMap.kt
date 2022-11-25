package com.lwt.realestatemanager.screens.home.maps

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.lwt.realestatemanager.model.Estate
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@ExperimentalPermissionsApi
@SuppressLint("MissingPermission")
@Composable
fun EstateMap(list: List<Estate>, modifier: Modifier = Modifier, setSelectedEstate: (Estate) -> (Unit)) {
	val mapView = rememberMapViewWithLifecycle()
	val finePermissionState = rememberMultiplePermissionsState(listOf(
		android.Manifest.permission.ACCESS_FINE_LOCATION,
		android.Manifest.permission.ACCESS_COARSE_LOCATION
	))

	PermissionsRequired(
		multiplePermissionsState = finePermissionState,
		permissionsNotGrantedContent = {
			Box(modifier.fillMaxSize()) {
				Column(modifier = Modifier.align(Alignment.Center)) {
					Text(text = "You need to grand localisation permission")
					Button({
						finePermissionState.launchMultiplePermissionRequest()
					}) {
						Text("Resquest Window")
					}
				}
			}
		},
		permissionsNotAvailableContent = {
			Box(modifier.fillMaxSize()) {
				Text(text = "Localisation permission not available", modifier = Modifier.align(Alignment.Center))
			}
		}
	) {
		val context = LocalContext.current
		AndroidView(factory = { mapView }, modifier = modifier) { view ->
			CoroutineScope(Dispatchers.Main).launch {
				val googleMap = view.awaitMap()
				val markerMap = mutableMapOf<Marker, Estate>()

				googleMap.isMyLocationEnabled = true
				googleMap.uiSettings.isZoomControlsEnabled = true

				val loc = getMyLocation(context)

				val cameraPosition = CameraPosition.Builder()
					.target(LatLng(loc.latitude, loc.longitude))
					.zoom(17.0f)
					.build()

				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

				list.forEach {
					val latlng = LatLng(it.latitude, it.longitude)
					val markerOptionsDestination = MarkerOptions()
						.title(it.address)
						.position(latlng)
					markerMap[googleMap.addMarker(markerOptionsDestination)] = it
				}

				googleMap.setOnInfoWindowClickListener {
					val estate = markerMap[it]
					estate?.let { it1 -> setSelectedEstate(it1) }
				}
			}
		}
	}
}