package com.lwt.realestatemanager.ui.theme.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lwt.realestatemanager.data.database.RealtyModel
import com.lwt.realestatemanager.data.database.DBHandler
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme

class ViewRealty : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealEstateManagerTheme {
                // on below line we are specifying background color for our application
                Surface(
                    // on below line we are specifying modifier and color for our app
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    // on the below line we are specifying the theme as the scaffold.
                    Scaffold(
                        // in scaffold we are specifying the top bar.
                        topBar = {
                            // inside top bar we are specifying background color.
                            TopAppBar(backgroundColor = Color.Gray,
                                // along with that we are specifying title for our top bar.
                                title = {
                                    // in the top bar we are specifying tile as a text
                                    Text(
                                        // on below line we are specifying
                                        // text to display in top app bar.
                                        text = "Realty Detail Preview",

                                        // on below line we are specifying
                                        // modifier to fill max width.
                                        modifier = Modifier.fillMaxWidth(),

                                        // on below line we are specifying
                                        // text alignment.
                                        textAlign = TextAlign.Center,

                                        // on below line we are specifying
                                        // color for our text.
                                        color = Color.White
                                    )
                                })
                        }) {
                        // on below line we are calling our method to display UI
                        readDataFromDatabase(LocalContext.current)
                    }
                }
            }
        }
    }
}

@Composable
fun readDataFromDatabase(context: Context) {
    // on below line we are creating and initializing our array list
    lateinit var realtyList: List<RealtyModel>
    realtyList = ArrayList<RealtyModel>()

    val dbHandler: DBHandler = DBHandler(context);
    realtyList = dbHandler.readRealty()!!

    // on below line we are creating a lazy column for displaying a list view.
    LazyColumn {
        // on below line we are setting data for each item of our listview.
        itemsIndexed(realtyList) { index, item ->
            // on below line we are creating a card for our list view item.
            Card(
                // on below line we are adding padding from our all sides.
                modifier = Modifier.padding(8.dp),
                // on below line we are adding elevation for the card.
                elevation = 6.dp
            ) {
                // on below line we are creating a row for our list view item.
                Column(
                    // for our row we are adding modifier to set padding from all sides.
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    // on the below line we are creating a text.
                    Text(
                        // inside the text on below line we are
                        // setting text as the language name
                        // from our model class.
                        text = realtyList[index].realtyName,

                        // on below line we are adding padding
                        // for our text from all sides.
                        modifier = Modifier.padding(4.dp),

                        // on below line we are adding color for our text
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                    // on below line inside row we are adding spacer
                    Spacer(modifier = Modifier.width(5.dp))

                    // on the below line we are creating a text.
                    Text(
                        // inside the text on below line we are
                        // setting text as the language name
                        // from our model class.
                        text = "Realty Address : " + realtyList[index].realtyAddress,

                        // on below line we are adding padding
                        // for our text from all sides.
                        modifier = Modifier.padding(4.dp),

                        // on below line we are adding color for our text
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                    // on below line inside row we are adding spacer
                    Spacer(modifier = Modifier.width(5.dp))

                    // on the below line we are creating a text.
                    Text(
                        // inside the text on below line we are
                        // setting text as the language name
                        // from our model class.
                        text = ": " + realtyList[index].realtyType,

                        // on below line we are adding padding
                        // for our text from all sides.
                        modifier = Modifier.padding(4.dp),

                        // on below line we are adding color for our text
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                    // on below line inside row we are adding spacer
                    Spacer(modifier = Modifier.width(5.dp))

                    // on the below line we are creating a text.
                    Text(
                        // inside the text on below line we are
                        // setting text as the language name
                        // from our model class.
                        text = "Price in dollars: " + realtyList[index].realtyPriceInDollars,

                        // on below line we are adding padding
                        // for our text from all sides.
                        modifier = Modifier.padding(4.dp),

                        // on below line we are adding color for our text
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


