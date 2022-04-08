package com.example.mycleaninglog


import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom
import com.example.mycleaninglog.ui.theme.RegularBlue

/**
 * Creates secondary card for room information including tasks and settings.
 */
@ExperimentalMaterialApi
@Composable
fun ExpandableCardLevelTwo(
    c: Context,
    selectedRoom: myRoom,
    viewModel: MainViewModel,
    myCleaningTasks: List<cleaningTask>,
    myRooms: List<myRoom>
    ) {

    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    var dropDownMenu by remember { mutableStateOf(false) }

    var roomSettingsPopup by remember { mutableStateOf(false) }

    val montFont = FontFamily(
        Font(R.font.mont, FontWeight.Normal)
    )
    val louisFont = FontFamily(
        Font(R.font.louis, FontWeight.Normal)
    )


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
                        fontFamily = montFont,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .background(color = RegularBlue)
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
                                fontFamily = louisFont,
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

