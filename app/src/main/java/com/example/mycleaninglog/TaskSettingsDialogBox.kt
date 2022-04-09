package com.example.mycleaninglog


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
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

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu

import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom


@ExperimentalMaterialApi
@Composable
fun TaskSettingsDialogBox(c: Context, selectedTaskSettings: cleaningTask, viewModel: MainViewModel, selectedRoomSettings: myRoom) {
    val openDialog = remember { mutableStateOf(true) }
    val timerChange = remember {mutableStateOf("")}


    if(openDialog.value){

        Dialog(onDismissRequest = {openDialog.value = false}) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(500.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(verticalAlignment = Alignment.CenterVertically){
                        //Header to dialog box
                        Text(
                            modifier = Modifier
                                .weight(6f)
                                .padding(10.dp),
                            text = "Task Settings",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        //cancel button to dialog box
                        IconButton(
                            onClick = {
                                openDialog.value = false
                            },
                            modifier = Modifier
                                .weight(1.5f)
                                //.height(60.dp)
                                .padding(10.dp)
                                .background(color = Color.Red, shape = RoundedCornerShape(5.dp))


                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Symbol"
                            )
                        }

                    }


                    Spacer(modifier = Modifier.padding(10.dp))

                    //text box for changing room name
                    OutlinedTextField(
                        value = timerChange.value,
                        onValueChange = { timerChange.value = it },
                        label = { Text(text = "Change Reset Timer") },
                        placeholder = { Text(text = "Change Reset Timer") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    //submit button for changing room name
                    Button(
                        onClick = {

                            openDialog.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(60.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.green))
                    ) {
                        Text(
                            text = "Change Reset Timer",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.padding(10.dp))


                    Button(
                        onClick = {

                            viewModel.deleteTask(selectedRoomSettings, selectedTaskSettings)

                            openDialog.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(60.dp)
                            .padding(10.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text(
                            text = "Delete Task",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
        }

    }




}
