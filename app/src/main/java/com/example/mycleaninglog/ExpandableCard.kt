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
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom
import com.example.mycleaninglog.ui.theme.Gray
import com.google.firebase.platforminfo.DefaultUserAgentPublisher


@ExperimentalMaterialApi
@Composable

fun ExpandableCard(

    c: Context,
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    myRooms: List<myRoom> = ArrayList<myRoom>(),
    myCleaningTasks: List<cleaningTask>,
    viewModel: MainViewModel
    ) {
    //Used for Expandable card level 1 functionality
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    //Used for Add Room Button
    var addRoomShowMenu by remember { mutableStateOf(false) }
    //val context = LocalContext.current
    //var selectedMyRoom: myRoom? = null
    //var viewModel = inViewModel
viewModel.listenToMyRooms()

    //building the card
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
        //onClick = { expandedState = !expandedState }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color)
                .padding(12.dp)
        ) {
            //following code builds the row that the information will sit within
            Row(verticalAlignment = Alignment.CenterVertically) {

                //creates the up and down arrow icon for if 1st level card has been expanded
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

                //following code builds the first block of the row (this is the text/title section)
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
                // If the level 1 card is titled "Rooms", do the following code which builds the second and third portion of the row (the buttons)
                if (title == "Rooms") {
                    //creates the icon button for adding rooms
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
                    //creates the dropdown menu when icon is clicked
                    DropdownMenu(
                        expanded = addRoomShowMenu,
                        onDismissRequest = { addRoomShowMenu = false }) {
                        //list of menu items to be displayed
                        //bedroom
                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Bedroom", "Bed", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Bedroom") }

                        //bathroom
                        DropdownMenuItem(
                            onClick = {
                                viewModel.saveItem("Bathroom", "Bath", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Bathroom") }

                        //kitchen
                        DropdownMenuItem(
                            onClick = {
                                //addRoomList.add("Kitchen")
                                viewModel.saveItem("Kitchen", "Kit", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Kitchen") }

                        //Dining room
                        DropdownMenuItem(
                            onClick = {
                                //addRoomList.add("Dining Room")
                                viewModel.saveItem("Dining Room", "Din", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Dining Room") }

                        //living room
                        DropdownMenuItem(
                            onClick = {
                                //addRoomList.add("Living Room")
                                viewModel.saveItem("Living Room", "Liv", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Living Room") }

                        //outdoors
                        DropdownMenuItem(
                            onClick = {
                                //addRoomList.add("Outdoors")
                                viewModel.saveItem("Outdoors", "Out", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Outdoors") }

                        //garage
                        DropdownMenuItem(
                            onClick = {
                                //addRoomList.add("Garage")
                                viewModel.saveItem("Garage", "Gar", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Garage") }

                        //utility room
                        DropdownMenuItem(
                            onClick = {
                                //addRoomList.add("Utility Room")
                                viewModel.saveItem("Utility Room", "Util", viewModel)
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "Utility Room") }

                        //other
                        DropdownMenuItem(
                            onClick = {

                                //add code here to create a new room that is not in the list
                                addRoomShowMenu = !addRoomShowMenu
                            })
                        { Text(text = "other") }
                    }
                }
            }
            //displays the options for if the card is expanded
            if (expandedState) {
                //what to do if the 1st level expandable card is labeled "Rooms"
                if (title == "Rooms") {
                    //passes each item on the addRoomList into expandable card level
                    myRooms.forEach { position -> ExpandableCardLevelTwo(selectedRoom = position, c = c, viewModel = viewModel, myCleaningTasks = myCleaningTasks, myRooms = myRooms )}
                }

                //what to do if the 1st level expandable card is labeled "Common Tasks"
                if (title == "Common Tasks") {
                    //currently just displays a text line
                    //following code needs to be replaced with a list of tasks that are shared between multiple rooms
                    Text(text = "Here are the common tasks")
                }
                //what to do if the 1st level expandable card is labeled "Upcoming tasks"
                if (title == "Upcoming Tasks") {
                    //currently just displays a text line
                    //following code needs to be replaced with a list of tasks in order from most needed to least
                    Text(text = "here are the upcoming tasks")
                }

            }
        }

    }
}


