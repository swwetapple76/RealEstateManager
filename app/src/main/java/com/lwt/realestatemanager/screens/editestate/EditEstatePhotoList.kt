package com.lwt.realestatemanager.screens.editestate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.screens.home.detail.EstateDetailPhotoItem

@ExperimentalCoilApi
@Composable
fun EditEstatePhotoList(estate: Estate, onEstateChange: (Estate) -> (Unit)) {
	LazyRow {
		itemsIndexed(estate.photos) { index, photo ->
			Column {
				Row(modifier = Modifier.padding(4.dp)) {
					Button(shape = RoundedCornerShape(20.dp), onClick = {
						estate.photos -= photo
						onEstateChange(estate)
					}) {
						Icon(imageVector = Icons.Filled.Delete,
							contentDescription = "Remove picture from Estate",
							tint = Color.White)
					}
					var openRenamePhotoDialog by remember { mutableStateOf(false) }
					Button(shape = RoundedCornerShape(20.dp), onClick = {
						openRenamePhotoDialog = true

					}) {
						Icon(imageVector = Icons.Filled.Edit,
							contentDescription = "Rename picture from Estate",
							tint = Color.White)
					}

					if (openRenamePhotoDialog) {
						var newName by remember { mutableStateOf("") }
						AlertDialog(
							onDismissRequest = { openRenamePhotoDialog = false },
							title = { Text(modifier = Modifier.padding(8.dp), text = "Rename Photo") },
							text = {
								Spacer(modifier = Modifier.height(64.dp))
								OutlinedTextField(
									value = newName,
									onValueChange = {
										newName = it
									},
									label = { Text("Photo Name") },
									modifier = Modifier
										.fillMaxWidth()
										.padding(8.dp)
								)
								Spacer(modifier = Modifier.height(10.dp))
							},



							confirmButton = {
								Button(
									onClick = {
										val mList = estate.photos.toMutableList()
										photo.name = newName
										mList[index] = photo
										onEstateChange(estate)
										openRenamePhotoDialog = false
									}
								) {
									Text("Confirm New Name")
								}
							},
							dismissButton = {
								Button(
									onClick = { openRenamePhotoDialog = false }) {
									Text("Cancel")

								}
							}
						)
					}
				}
				EstateDetailPhotoItem(photo)
			}
		}
		item {
			EditEstatePhotoPicker(onPhotoSelected = { photo ->
				estate.photos += photo
				onEstateChange(estate)
			})
		}
	}
}