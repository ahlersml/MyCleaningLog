package com.example.mycleaninglog

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom
import com.example.mycleaninglog.ui.theme.Gray
import com.google.firebase.platforminfo.DefaultUserAgentPublisher


@ExperimentalMaterialApi
@Composable

fun ExpandableCard(

    c: Context,
    title: String,
    titleFontSize: TextUnit = 25.sp,
    titleFontWeight: FontWeight = FontWeight.Bold,
    myRooms: List<myRoom> = ArrayList<myRoom>(),
    myCleaningTasks: List<cleaningTask>,
    viewModel: MainViewModel
    ) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    var addRoomShowMenu by remember { mutableStateOf(false) }
viewModel.listenToMyRooms()

    val dark = isSystemInDarkTheme()
    val color = if (dark) Gray else Color.LightGray
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
                .background(color = color)
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }

                Text(
                    modifier = Modifier
                        .background(color = color)
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (title == "Rooms") {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .background(color = Color.Green)
                            .weight(1f),
                        onClick = { addRoomShowMenu = !addRoomShowMenu }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Symbol"
                        )
                    }
                    DropdownMenu(
                        expanded = addRoomShowMenu,
                        onDismissRequest = { addRoomShowMenu = false }) {
                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Bedroom", "Bed", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Bedroom") }

                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Bathroom", "Bath", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Bathroom") }

                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Kitchen", "Kit", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Kitchen") }

                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Dining Room", "Din", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Dining Room") }

                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Living Room", "Liv", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Living Room") }

                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Outdoors", "Out", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Outdoors") }

                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Garage", "Gar", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Garage") }

                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Utility Room", "Util", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Utility Room") }

                        DropdownMenuItem(
                            onClick = {

                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "other") }
                    }
                }
            }
            if (expandedState) {
                if (title == "Rooms") {
                    myRooms.forEach { position -> ExpandableCardLevelTwo(selectedRoom = position, c = c, viewModel = viewModel, myCleaningTasks = myCleaningTasks, myRooms = myRooms )}
                }

                if (title == "Common Tasks") {
                    Text(text = "Here are the common tasks")
                }
                if (title == "Upcoming Tasks") {
                    Text(text = "here are the upcoming tasks")
                }

            }
        }

    }
}


