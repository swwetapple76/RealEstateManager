package com.lwt.realestatemanager.screens.editestate

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.lwt.realestatemanager.model.Photo
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@SuppressLint("SimpleDateFormat")
@ExperimentalCoilApi
@Composable
fun EditEstatePhotoPicker(onPhotoSelected: (Photo) -> (Unit)) {
	val context = LocalContext.current

	var imageUri by remember { mutableStateOf<Uri?>(null) }
	var bitmap by remember { mutableStateOf<Bitmap?>(null) }
	var enteringName by remember { mutableStateOf("") }
	var enteringDescription by remember { mutableStateOf("") }
	var openNameDialog by remember { mutableStateOf(false) }

	val launcherGallery = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
		if (uri != null) {
			imageUri = uri
			openNameDialog = true
		} else {
			imageUri = null
			openNameDialog = false
		}
	}
	val launcherCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
		if (it != null) {
			bitmap = it
			openNameDialog = true
		} else {
			bitmap = null
			openNameDialog = false
		}
	}

	Column(modifier = Modifier.fillMaxHeight()) {
		IconButton(
			onClick = { launcherGallery.launch("image/*") },
			modifier = Modifier.fillMaxSize(),
			content = {
				Icon(
					imageVector = Icons.Default.Image,
					contentDescription = "Add picture from gallery to Estate",
					modifier = Modifier.fillMaxSize())
			}
		)

		IconButton(
			onClick = { launcherCamera.launch() },
			modifier = Modifier.fillMaxSize(),
			content = {
				Icon(
					imageVector = Icons.Default.AddAPhoto,
					contentDescription = "Add picture from camera to Estate",
					modifier = Modifier.fillMaxSize())
			}
		)
	}

	if (openNameDialog) AlertDialog(
		onDismissRequest = {},
		text = {
			Column() {
				OutlinedTextField(
					value = enteringName,
					onValueChange = { enteringName = it },
					label = { Text("Photo Name") },
					modifier = Modifier
						.fillMaxWidth()
						.padding(8.dp)
				)
				OutlinedTextField(
					value = enteringDescription,
					onValueChange = { enteringDescription = it },
					label = { Text("Photo Description") },
					modifier = Modifier
						.fillMaxWidth()
						.padding(8.dp)
				)
				Spacer(modifier = Modifier.height(12.dp))
			}
		},
		confirmButton = {
			Button(
				onClick = {
					imageUri?.let {
						if (Build.VERSION.SDK_INT < 28) {
							bitmap = MediaStore.Images
								.Media.getBitmap(context.contentResolver, it)
						} else {
							val source = ImageDecoder
								.createSource(context.contentResolver, it)
							bitmap = ImageDecoder.decodeBitmap(source)
						}
						imageUri = null
					}

					val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + Random.nextInt() % 9998 + ".jpg"
					val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
					val copyDestination = File(storageDir, timeStamp)
					val fOut = FileOutputStream(copyDestination);
					bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

					onPhotoSelected(Photo(name = enteringName, description = enteringDescription, localUri = copyDestination.absolutePath))
				}
			) {
				Text("Add Photo")
			}
		},
		dismissButton = {
			Button(
				onClick = {
					openNameDialog = false
				}) {
				Text("Cancel")
			}
		}
	)
}