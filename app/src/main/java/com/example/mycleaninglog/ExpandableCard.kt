package com.example.mycleaninglog

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

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    )
    {
        //Used for Expandable card level 1 functionality
        var expandedState by remember { mutableStateOf(false) }
        val rotationState by animateFloatAsState(
            targetValue = if(expandedState) 180f else 0f)

        //Used for Add Room Button
        var addRoomShowMenu by remember { mutableStateOf(false)}
        val context = LocalContext.current


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
            onClick = { expandedState = !expandedState }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .padding(12.dp)
            ) {
                //following code builds the row that the information will sit within
                Row(verticalAlignment = Alignment.CenterVertically) {
                    //following code builds the first block of the row (this is the text/title section)
                    Text(
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .weight(6f),
                        text = title,
                        fontSize = titleFontSize,
                        fontWeight = titleFontWeight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    // If the level 1 card is titled "Rooms", do the following code which builds the second and third portion of the row (the buttons)
                    if(title == "Rooms"){
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
                            DropdownMenuItem(onClick = {}) {
                                Text(text = "Bedroom")
                            }
                            DropdownMenuItem(onClick = {}) {
                                Text(text = "Bathroom")
                            }
                            DropdownMenuItem(onClick = {}) {
                                Text(text = "Kitchen")
                            }
                            DropdownMenuItem(onClick = {}) {
                                Text(text = "Dining Room")
                            }
                            DropdownMenuItem(onClick = {}) {
                                Text(text = "Living Room")
                            }
                            DropdownMenuItem(onClick = {}) {
                                Text(text = "Outdoors")
                            }
                            DropdownMenuItem(onClick = {}) {
                                Text(text = "Garage")
                            }
                            DropdownMenuItem(onClick = {}) {
                                Text(text = "Utility Room")
                            }
                            DropdownMenuItem(onClick = {}) {
                                Text(text = "Other")
                            }

                        }
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
                    }
                    //if the level 1 card is not titled "Rooms", do the following code which builds the second and third secions of the row (the buttons)
                    else{
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
                    }

                }
                //displays the options for if the card is expanded
                if(expandedState){
                    //what to do if the 1st level expandable card is labeled "Rooms"
                    if (title == "Rooms"){
                        //currently calls 3x 2nd level expandable cards which will be used for individual rooms
                        //These cards are currently being called with hard code and needs to be replaced
                        //following code needs to be replaced with calling from a list of rooms that is saved in firebase data
                        ExpandableCardLevelTwo(title = "Bedroom1")
                        ExpandableCardLevelTwo(title = "Bedroom2")
                        ExpandableCardLevelTwo(title = "Bedroom3")
                    }
                    //what to do if the 1st level expandable card is labeled "Common Tasks"
                    if (title == "Common Tasks"){
                        //currently just displays a text line
                        //following code needs to be replaced with a list of tasks that are shared between multiple rooms
                        Text(text = "Here are the common tasks")
                    }
                    //what to do if the 1st level expandable card is labeled "Upcoming tasks"
                    if (title == "Upcoming Tasks"){
                        //currently just displays a text line
                        //following code needs to be replaced with a list of tasks in order from most needed to least
                        Text(text = "here are the upcoming tasks")
                    }

                }
            }

        }
    }


@ExperimentalMaterialApi
@Composable
@Preview
fun ExpandableCardPreview(){
    ExpandableCard("Rooms")
}

