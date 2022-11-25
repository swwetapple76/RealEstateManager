package com.lwt.realestatemanager.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import com.lwt.realestatemanager.BuildConfig
import com.lwt.realestatemanager.utils.ComposerUtils.getScreenWidthInfo
import com.lwt.realestatemanager.network.NetworkStatus
import com.lwt.realestatemanager.network.NetworkStatusTracker
import com.lwt.realestatemanager.network.NetworkStatusViewModel
import com.lwt.realestatemanager.screens.home.detail.EstateDetails
import com.lwt.realestatemanager.screens.home.filter.FilterSettings
import com.lwt.realestatemanager.screens.home.list.EstateList
import com.lwt.realestatemanager.screens.home.maps.EstateMap
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
class HomeActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// ----------------------------
		// ViewModels
		// ----------------------------
		val viewModel: HomeViewModel by viewModels()
		viewModel.initDatabase(applicationContext)
		val networkViewModel: NetworkStatusViewModel by lazy {
			ViewModelProvider(
				this,
				object : ViewModelProvider.Factory {
					//override
					override fun <T : ViewModel> create(modelClass: Class<T>): T {
						val networkStatusTracker = NetworkStatusTracker(this@HomeActivity)
						@Suppress("UNCHECKED_CAST") return NetworkStatusViewModel(networkStatusTracker) as T
					}
				}
			).get(NetworkStatusViewModel::class.java)
		}

		setContent {
			// ----------------------------
			// Remember / Var
			// ----------------------------
			val (small) = getScreenWidthInfo()
			var openLeftDrawer by remember { mutableStateOf(true) }
			val estateList by viewModel.rememberEstateList()
			viewModel.updateEstateListFromDB()
			val estateSelected by viewModel.rememberEstateSelected()
			val networkStatusState = networkViewModel.networkState.observeAsState()
			viewModel.ObserveEstateSelected(LocalLifecycleOwner.current) { if (small) openLeftDrawer = false }
			var openMap by remember { mutableStateOf(false) }
			RealEstateManagerTheme {
				HomeTopBar(
					viewModel = viewModel,
					listEstate = estateList,
					toggleDrawer = {
						openLeftDrawer = !openLeftDrawer
					},
					mapOpen = openMap,
					toggleMap = {
						openMap = !openMap
					},
				) {
					when {
						// ----------------------------
						// Show Map
						// ----------------------------
						openMap -> {
							estateList?.let {
								EstateMap(list = it, setSelectedEstate = { it1 ->
									viewModel.setSelectedEstate(it1.uid)
									openMap = !openMap
								})
							}
						}

						// ----------------------------
						// Message Filter
						// ----------------------------
						estateList.isNullOrEmpty() -> {
							Column(
								modifier = Modifier.fillMaxSize(),
								verticalArrangement = Arrangement.Center,
								horizontalAlignment = Alignment.CenterHorizontally
							) {
								Text(
									text = "Filter Showed no Result",
									style = MaterialTheme.typography.h6.copy(color = Color.LightGray),
									modifier = Modifier.padding(64.dp)
								)
								Button({ viewModel.setFilterSetting(FilterSettings.Default.copy()) }) {
									Text("Reset Filter")
								}
							}
						}
						// ----------------------------
						// List and Details
						// ----------------------------
						else -> {
							estateList?.let { estateListChecked ->
								Row(Modifier.fillMaxSize()) {
									val pair = estateSelected ?: 0
									// -------------------------
									// Estate List
									// -------------------------
									AnimatedVisibility(visible = openLeftDrawer, enter = expandHorizontally(), exit = shrinkHorizontally()) {
										EstateList(estateListChecked, pair, viewModel)
									}
									Column()
									{
										// -------------------------
										// Network Status Message
										// -------------------------
										AnimatedVisibility(
											visible = networkStatusState.value == NetworkStatus.Unavailable,
											enter = expandVertically(),
											exit = shrinkVertically()
										) {
											Box()
											{
												Surface(color = Color(ColorUtils.HSLToColor(floatArrayOf(0.0f, 0.75f, 0.5f)))) {
													Text(text = "No Internet Connection, Viewing Local Copy",
														color = Color.White,
														textAlign = TextAlign.Center,
														modifier = Modifier
															.align(Alignment.Center)
															.fillMaxWidth()
															.padding(4.dp)

													)
												}
											}
										}

										// -------------------------
										// Estate Details
										// -------------------------
										EstateDetails(viewModel.getSelectedEstate())
									}
								}
							}
						}
					}
				}
			}

			// ----------------------------
			// Debug Build Only
			// ----------------------------
			if (BuildConfig.DEBUG) {
				HomeDebug(viewModel = viewModel)
			}
		}
	}
}