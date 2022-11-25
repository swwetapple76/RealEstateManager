package com.lwt.realestatemanager.screens.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.model.Photo
import com.lwt.realestatemanager.utils.ActivityUtils
import com.lwt.realestatemanager.utils.ExtensionUtils.truncat
import com.lwt.realestatemanager.utils.MapUtils
import com.google.android.libraries.maps.model.LatLng

@ExperimentalCoilApi
@Composable
fun EstateDetailMinimap(localisation: LatLng) {
	val context = LocalContext.current
	Box(modifier = Modifier.fillMaxSize()) {
		Image(
			rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
				.data(data = MapUtils.formatApiRequestStaticMap(400, 400, 19, "${localisation.latitude.truncat(6)},${localisation.longitude.truncat(6)}"))
				.apply(block = fun ImageRequest.Builder.() {
					placeholder(R.drawable.ic_launcher_background)
					error(R.drawable.ic_launcher_foreground)
				}).build()),
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.size(250.dp)
				.clickable {
					ActivityUtils.openPhotoViewerActivity(context,
						Photo(onlineUrl = MapUtils.formatApiRequestStaticMap(1024, 1024, 14, "${localisation.latitude.truncat(6)},${localisation.longitude.truncat(6)}")))
				}
				.align(Alignment.Center),
			contentDescription = stringResource(R.string.content_description_mini_map_preview),
		)
	}
}