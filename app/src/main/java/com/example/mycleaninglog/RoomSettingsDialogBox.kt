package com.example.mycleaninglog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties


@ExperimentalMaterialApi
@Composable
fun RoomSettingsDialogBox(c: Context) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {

        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            onDismissRequest = { openDialog.value = false },

            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(25.dp))
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Settings",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                }
            },


            text = {
                Text(
                    text = "Rename Your Room",
                    color = Color.White
                )
            },


            confirmButton = {
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.green),
                            shape = CircleShape
                        ),
                    onClick = {
                        openDialog.value = false
                        //Toast.makeText(c,"ConfirmButton is Clicked", Toast.LENGTH_LONG).show()
                    })
                {
                    Text(text = "Confirm", color = Color.White)

                }
            },


            dismissButton = {
                TextButton(modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.red),
                        shape = CircleShape
                    ),

                    onClick = {
                        openDialog.value = false
                        Toast.makeText(c, "Cancel Button Clicked", Toast.LENGTH_LONG).show()
                    })
                {
                    Text(
                        text = "Cancel",
                        color = Color.White
                    )
                }
            },

            backgroundColor = colorResource(id = R.color.skycolor),
            contentColor = colorResource(id = R.color.green),
            shape = RoundedCornerShape(25.dp)
        )
    }
}

