package com.lwt.realestatemanager.utils


import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityOptionsCompat
import coil.annotation.ExperimentalCoilApi
import java.util.*


@ExperimentalCoilApi
object ComposerUtils {

	// Fix register for Activity not sending result

	@Composable
	fun <I, O> registerForActivityResult(
		contract: ActivityResultContract<I, O>,
		onResult: (O) -> Unit,
	): ActivityResultLauncher<I> {
		val owner = LocalContext.current as ActivityResultRegistryOwner
		val activityResultRegistry = owner.activityResultRegistry
		val currentOnResult = rememberUpdatedState(onResult)
		val key = remember { UUID.randomUUID().toString() }
		val realLauncher = remember { mutableStateOf<ActivityResultLauncher<I>?>(null) }
		val returnedLauncher = remember {
			object : ActivityResultLauncher<I>() {
				override fun launch(input: I, options: ActivityOptionsCompat?) {
					realLauncher.value?.launch(input, options)
				}

				override fun unregister() {
					realLauncher.value?.unregister()
				}

				override fun getContract() = contract
			}
		}
		DisposableEffect(activityResultRegistry, key, contract) {
			realLauncher.value = activityResultRegistry.register(key, contract) {
				currentOnResult.value(it)
			}
			onDispose {
				realLauncher.value?.unregister()
			}
		}
		return returnedLauncher
	}


	// Fix register for Activity not sending result

	@Composable
	fun getScreenWidthInfo(): Pair<Boolean, Int> {
		val configuration = LocalConfiguration.current
		return Pair(configuration.screenWidthDp <= 450, configuration.screenWidthDp)
	}


	// Matrix to get Grayscale image

	private val grayScaleMatrix = ColorMatrix(
		floatArrayOf(
			0.33f, 0.33f, 0.33f, 0f, 0f,
			0.33f, 0.33f, 0.33f, 0f, 0f,
			0.33f, 0.33f, 0.33f, 0f, 0f,
			0f, 0f, 0f, 1f, 0f
		)
	)
	val colorFilterGrayscale = ColorFilter.colorMatrix(grayScaleMatrix)
}