package com.lwt.realestatemanager.screens.home.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.annotation.ExperimentalCoilApi
import com.lwt.realestatemanager.utils.ComposerUtils
import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.screens.home.HomeViewModel
import com.lwt.realestatemanager.screens.home.filter.EtateListFilter

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun EstateList(
    estateList: List<Estate>,
    estateSelected: Long?,
    viewModel: HomeViewModel,
    onItemSelected: (Long) -> Unit
) {
    val (small, width) = ComposerUtils.getScreenWidthInfo()
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    Box(Modifier.width(if (small) width.dp else 250.dp)) {
        var filterDialogOpened by remember { mutableStateOf(false) }

        AnimatedVisibility(
            visible = filterDialogOpened,
            enter = expandVertically(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = shrinkVertically(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            )
        ) {
            EtateListFilter(with(LocalDensity.current) { textFieldSize.height.toDp() } + 16.dp,
                viewModel = viewModel,
                closeFilterView = { filterDialogOpened = !filterDialogOpened })
        }
        AnimatedVisibility(
            visible = !filterDialogOpened,
            enter = expandVertically(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = shrinkVertically(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            )
        ) {
            LazyColumn(
                contentPadding = PaddingValues(top = with(LocalDensity.current) { textFieldSize.height.toDp() + 16.dp }),
                modifier = Modifier
                    .fillMaxHeight()
                    .drawBehind {
                        val strokeWidth = 1 * density
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            Color.LightGray,
                            Offset(size.width, 0f),
                            Offset(size.width, y),
                            strokeWidth
                        )
                    }) {
                items(estateList) { estate ->
                    EstateListItem(
                        estate = estate,
                        isSelected = estate.uid == estateSelected,
                        viewModel = viewModel
                    ) {
                        onItemSelected(it)
                    }
                }
            }
        }

        Button(
            onClick = { filterDialogOpened = !filterDialogOpened },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.TopCenter)
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
        ) {
            Text(if (filterDialogOpened) "Hide Filters" else "Show Filters")
        }
    }
}


