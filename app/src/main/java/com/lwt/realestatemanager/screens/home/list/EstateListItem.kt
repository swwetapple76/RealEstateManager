package com.lwt.realestatemanager.screens.home.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.lwt.realestatemanager.utils.ComposerUtils
import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.model.EstateStatus
import com.lwt.realestatemanager.screens.home.HomeViewModel
import java.io.File
import java.text.NumberFormat
import java.util.*


@ExperimentalCoilApi
@Composable
fun EstateListItem(estate: Estate, isSelected: Boolean, viewModel: HomeViewModel) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.background(if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.background)
			.drawBehind {
				val strokeWidth = 1 * density
				val y = size.height - strokeWidth / 2
				drawLine(
					Color.LightGray,
					Offset(0f, y),
					Offset(size.width, y),
					strokeWidth
				)
			}
			.clickable {
				viewModel.setSelectedEstate(estate.uid)
			}
	)
	{
		Box(modifier = Modifier.size(108.dp)) {
			if (estate.photos.isNotEmpty()) {
				val photo = estate.photos.first()
				Image(
					painter = rememberImagePainter(if (photo.localUri != null) File(photo.localUri.toString()) else photo.onlineUrl),
					contentDescription = estate.photos.first().name,
					modifier = Modifier.fillMaxSize(),
					contentScale = ContentScale.Crop,
					colorFilter = if (estate.status == EstateStatus.Sold) ComposerUtils.colorFilterGrayscale else null
				)
			} else {
				Surface(modifier = Modifier
					.align(Center)
					.fillMaxSize()) {}
				Text(text = "No Photo\nAvailable", modifier = Modifier.align(Center))
			}

			if (estate.status == EstateStatus.Sold)
				Text(modifier = Modifier
					.align(Center)
					.rotate(45f),
					text = "Sold",
					fontWeight = FontWeight.ExtraBold,
					color = Color(ColorUtils.HSLToColor(floatArrayOf(0.0f, 0.75f, 0.5f))),
					fontSize = 38.sp)
		}
		Column(
			Modifier
				.padding(start = 8.dp)
				.align(Alignment.CenterVertically)
		) {
			Text(text = estate.type.toString(), fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
			Text(text = estate.district, fontSize = 15.sp, color = Color.DarkGray)

			// Format value to USD formatting
			val format: NumberFormat = NumberFormat.getCurrencyInstance()
			format.maximumFractionDigits = 0
			format.currency = Currency.getInstance("USD")
			Text(text = format.format(estate.price),
				color = if (isSelected) Color.White else MaterialTheme.colors.secondary,
				style = MaterialTheme.typography.h6.copy(fontSize = 19.sp),
				fontWeight = FontWeight.ExtraBold)
		}
	}

}