package com.lwt.realestatemanager.screens.simulator.info

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun InfoDialogPreview(){
    RealEstateManagerTheme {
        val state = remember { mutableStateOf(true)}
        InfoDialog(
            state = state
        ){

        }
    }
}


@Composable
fun InfoDialog(
    state: State<Boolean>,
    onDismiss: () -> Unit
){
    if (state.value){
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                elevation = dimensionResource(id = R.dimen.m_default_elevation),
                shape = AbsoluteRoundedCornerShape(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Image(
                            painter = painterResource(id = R.drawable.ic_logo_v2),
                            contentDescription = "",
                            modifier = Modifier.size(128.dp).padding(16.dp)
                        )

                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(30.dp))

                    Text(
                        text = stringResource(id = R.string.title_note),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colors.secondary)
                            .padding(4.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.description_note),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    )

                }
            }
        }
    }
}