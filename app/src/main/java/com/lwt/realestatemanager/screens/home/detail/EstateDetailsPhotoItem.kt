package com.lwt.realestatemanager.screens.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.lwt.realestatemanager.model.Photo
import com.lwt.realestatemanager.utils.ActivityUtils
import java.io.File


@ExperimentalCoilApi
@Composable
fun EstateDetailPhotoItem(photo: Photo, content: @Composable () -> Unit = {}) {
	val image = rememberImagePainter(if (photo.localUri != null) File(photo.localUri.toString()) else photo.onlineUrl)
	val context = LocalContext.current
	Card(elevation = 4.dp, modifier = Modifier
		.padding(8.dp)
		.clickable {
			ActivityUtils.openPhotoViewerActivity(context, photo)
		}
	)
	{
		Box {
			Image(
				image,
				contentDescription = photo.name,
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.size(108.dp)
					.align(Alignment.Center)
			)
			Surface(
				color = Color.Black.copy(alpha = 0.5f),
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.width(108.dp),
			) {
				Text(
					text = photo.name,
					style = MaterialTheme.typography.subtitle1.copy(color = Color.White),
					modifier = Modifier
						.align(Alignment.Center)
						.padding(8.dp),
					maxLines = 3
				)
			}
			content()
		}
	}
}