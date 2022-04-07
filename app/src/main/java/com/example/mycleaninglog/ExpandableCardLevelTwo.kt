package com.example.mycleaninglog


import android.app.AlertDialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom

@ExperimentalMaterialApi
@Composable
fun ExpandableCardLevelTwo(
    //expandable card 2 variables
    c: Context,
    selectedRoom: myRoom,
    viewModel: MainViewModel,
    myCleaningTasks: List<cleaningTask>,
    myRooms: List<myRoom>
    ) {

    // used for expandable card level 2 functionality
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )



    //viewModel.selectedRoom = selectedRoom
    //viewModel.listenToCleaningTasks()



    //used for drop down menu
    var dropDownMenu by remember { mutableStateOf(false) }

    //used for settings menu
    var roomSettingsPopup by remember { mutableStateOf(false) }


        //building the card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 12.dp, 0.dp, 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {

                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .weight(1f)
                            .rotate(rotationState),
                        onClick = {
                            if(!selectedRoom.expanded) {
                                myRooms.forEach { position -> position.expanded = false }
                                myRooms.forEach { position -> viewModel.saveRoom(position) }
                                expandedState = false
                                selectedRoom.expanded = true
                                viewModel.saveRoom(selectedRoom)
                                expandedState = !expandedState
                            }else{
                                expandedState = false
                                selectedRoom.expanded = false
                                viewModel.saveRoom(selectedRoom)
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop-Down Arrow"
                        )
                    }

                    Text(
                        modifier = Modifier
                            .weight(6f),
                        text = selectedRoom.myRoomName,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .background(color = Color.Cyan)
                            .weight(1f),
                        onClick = {
                            roomSettingsPopup = !roomSettingsPopup
                        }

                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Symbol"
                        )
                    }
                    if (roomSettingsPopup) {
                        RoomSettingsDialogBox(c = c, selectedRoomSettings = selectedRoom, viewModel = viewModel)
                    }

                }
                if (selectedRoom.expanded){
                if (expandedState) {
                    viewModel.selectedRoom = selectedRoom
                    viewModel.listenToCleaningTasks()

                    myCleaningTasks.forEach { position ->
                        var selectedTask: cleaningTask = position

                        var taskSettingsPopup by remember { mutableStateOf(false) }
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)){

                            Text(
                                modifier = Modifier
                                    .weight(8f),
                                text = position.cleaningTaskName,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            IconButton(
                                modifier = Modifier
                                    .alpha(ContentAlpha.medium)
                                    .background(color = Color.Cyan)
                                    .weight(1f),
                                onClick = {
                                    taskSettingsPopup = !taskSettingsPopup
                                }

                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu Symbol"
                                )
                            }
                            if (taskSettingsPopup) {
                                TaskSettingsDialogBox(c = c, selectedTaskSettings = selectedTask, viewModel = viewModel,
                                    selectedRoomSettings = selectedRoom)
                            }
                        }
                    }

                }else{
                    expandedState = !expandedState
                }
                }
            }

        }


    }

