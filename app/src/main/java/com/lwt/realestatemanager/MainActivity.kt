package com.lwt.realestatemanager

import android.annotation.SuppressLint
import androidx.compose.ui.tooling.preview.Preview
import com.lwt.realestatemanager.data.database.DBHandler
import com.lwt.realestatemanager.ui.theme.activity.ViewRealty
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lwt.realestatemanager.ui.theme.theme.RealEstateManagerTheme


class MainActivity : ComponentActivity() {


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealEstateManagerTheme(){
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
                            TopAppBar(backgroundColor = Color.Red,

                                // along with that we are specifying title for our top bar.
                                title = {

                                    // in the top bar we are specifying tile as a text
                                    Text(
                                        // on below line we are specifying
                                        // text to display in top app bar.
                                        text = "Realty Detail",

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
                        addDataToDatabase(LocalContext.current)
                    }
                }
            }
        }
    }
}

// on below line we are creating battery status function.
@Composable
fun addDataToDatabase(
    context: Context
) {

    val activity = context as Activity
    // on below line creating a variable for battery status
    val realtyName = remember {
        mutableStateOf(TextFieldValue())
    }
    val realtyType = remember {
        mutableStateOf(TextFieldValue())
    }
    val realtyAddress = remember {
        mutableStateOf(TextFieldValue())
    }
    val realtyPriceInDollars = remember {
        mutableStateOf(TextFieldValue())
    }


    // on below line we are creating a column,
    Column(
        // on below line we are adding a modifier to it,
        modifier = Modifier
            .fillMaxSize()
            // on below line we are adding a padding.
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        var dbHandler: DBHandler = DBHandler(context)

        // on below line we are adding a text for heading.
        Text(
            // on below line we are specifying text
            text = "Please submit your information of realty",
            // on below line we are specifying text color, font size and font weight
            color = Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.Bold
        )

        // on below line adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))

        // on below line we are creating a text field.
        TextField(
            // on below line we are specifying value for our email text field.
            value = realtyName.value,
            // on below line we are adding on value change for text field.
            onValueChange = { realtyName.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Enter your realty name") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(20.dp))

        // on below line we are creating a text field.
        TextField(
            // on below line we are specifying value for our email text field.
            value = realtyType.value,
            // on below line we are adding on value change for text field.
            onValueChange = { realtyType.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Enter your realty type") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(20.dp))


        // on below line we are creating a text field.
        TextField(
            // on below line we are specifying value for our email text field.
            value = realtyAddress.value,
            // on below line we are adding on value change for text field.
            onValueChange = { realtyAddress.value = it },
            // on below line we are adding place holder as text
            placeholder = { Text(text = "Enter your realty address") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(20.dp))

        // on below line we are creating a text field.
        TextField(
            // on below line we are specifying value for our email text field.
            value = realtyPriceInDollars.value,
            // on below line we are adding on value change for text field.
            onValueChange = { realtyPriceInDollars.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Enter your realtyPriceInDollars") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(15.dp))

        // on below line creating a button to check battery charging status
        Button(onClick = {
            dbHandler.addNewRealty(
                realtyName.value.text,
                realtyType.value.text,
                realtyPriceInDollars.value.text,
                realtyAddress.value.text
            )
            Toast.makeText(context, "Realty Added to Database", Toast.LENGTH_SHORT).show()
        }) {
            // on below line adding a text for our button.
            Text(text = "Add Realty to Database", color = Color.White)
        }

        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(15.dp))

        // on below line creating a button to open view realty activity
        Button(onClick = {
            val i = Intent(context, ViewRealty::class.java)
            context.startActivity(i)
        }) {
            // on below line adding a text for our button.
            Text(text = "Read Realtys to Database", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RealEstateManagerTheme {
        addDataToDatabase(LocalContext.current)
    }
}