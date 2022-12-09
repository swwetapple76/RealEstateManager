package com.lwt.realestatemanager.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.lwt.realestatemanager.model.Estate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
class HomeActivity : ComponentActivity() {

    val viewModel: HomeViewModel by viewModels()

    var isItemSelectedd: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ----------------------------
        // ViewModels
        // ----------------------------

        viewModel.initDatabase(applicationContext)
        val networkViewModel: NetworkStatusViewModel by lazy {
            ViewModelProvider(
                this,
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        val networkStatusTracker = NetworkStatusTracker(this@HomeActivity)
                        @Suppress("UNCHECKED_CAST") return NetworkStatusViewModel(
                            networkStatusTracker
                        ) as T
                    }
                }
            )[NetworkStatusViewModel::class.java]
        }

        setContent {
            // ----------------------------
            // Remember / Var
            // ----------------------------
            val (small) = getScreenWidthInfo()
            var openMap by remember { mutableStateOf(false) }
            var openLeftDrawer by remember { mutableStateOf(true) }
            val estateSelectedId by viewModel.rememberEstateSelected()
            val estateList by viewModel.rememberEstateList()
            val networkStatusState: State<NetworkStatus?> =
                networkViewModel.networkState.observeAsState()
            viewModel.ObserveEstateSelected(LocalLifecycleOwner.current) {
                if (small) openLeftDrawer = false
            }
            viewModel.getEstatesFromDd()

            RealEstateManagerTheme {
                HomeTopBar(
                    viewModel = viewModel,
                    toggleDrawer = {
                        openLeftDrawer = !openLeftDrawer
                        isItemSelectedd = !isItemSelectedd
                    },
                    mapOpen = openMap,
                    toggleMap = { openMap = !openMap },
                ) {
                    if (openMap) {
                        estateList?.let {
                            EstateMap(list = it, setSelectedEstate = { it1 ->
                                viewModel.setSelectedEstate(it1.uid)
                                openMap = !openMap
                            })
                        }
                    } else {
                        when (val state = viewModel.observeEstate().value) {
                            is HomeViewModel.EstateUIState.Loading -> {
                                // show loader
                                CircularProgressAnimated()
                            }
                            is HomeViewModel.EstateUIState.Failure -> {
                                EmptyView()
                            }
                            is HomeViewModel.EstateUIState.Ready -> {
                                EstateListView(
                                    estates = state.estates,
                                    estateSelected = estateSelectedId,
                                    networkStatusState = networkStatusState,
                                    openLeftDrawer = openLeftDrawer
                                )
                            }
                            else -> Unit
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

    @Composable
    fun EstateListView(
        estates: List<Estate>,
        estateSelected: Long?,
        networkStatusState: State<NetworkStatus?>,
        openLeftDrawer: Boolean
    ) {

        var isItemSelected by remember { mutableStateOf(false) }

        Row(Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = !isItemSelectedd,
                enter = expandHorizontally(),
                exit = shrinkHorizontally()
            ) {
                EstateList(
                    estateList = estates,
                    estateSelected = estateSelected,
                    viewModel = viewModel
                ) {
                    isItemSelectedd = true
                }
            }
            DetailEstateView(networkStatusState, isItemSelectedd != openLeftDrawer)
        }
    }

    @Composable
    fun EmptyView() {
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

    @Composable
    fun DetailEstateView(
        networkStatusState: State<NetworkStatus?>,
        isVisible: Boolean
    ) {
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
                        Text(
                            text = "No Internet Connection, Viewing Local Copy",
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
            if (isVisible) {
                EstateDetails(viewModel.getSelectedEstate())
            }
        }
    }

    @Composable
    private fun CircularProgressAnimated() {
        val progressValue = 0.75f
        val infiniteTransition = rememberInfiniteTransition()

        val progressAnimationValue by infiniteTransition.animateFloat(
            initialValue = 0.0f,
            targetValue = progressValue, animationSpec = infiniteRepeatable(animation = tween(900))
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(progress = progressAnimationValue)
        }
    }
}
