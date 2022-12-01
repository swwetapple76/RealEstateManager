
package com.lwt.realestatemanager.screens.simulator.info

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Composable
fun IconButtonPreview(){
    RealEstateManagerTheme {
        IconButtonSmall(
            painterResource(id = R.drawable.ic_close)
        ) {

        }
    }
}

@Composable
fun IconButtonSmall(
    icon: Painter,
    onClick: () -> Unit
){
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(dimensionResource(id = R.dimen.m_default_icon_size))
    ) {
        Icon(
            painter = icon,
            contentDescription = "",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.fillMaxSize()
        )
    }
}