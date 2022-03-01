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
import androidx.compose.runtime.snapshots.SnapshotStateList
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
        var addRoomList = remember { mutableStateListOf<RoomClass>()}

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
                // following code builds the row that the information will sit within
                Row(verticalAlignment = Alignment.CenterVertically) {
                    /* following code builds the first block of the row
                     * (this is the text/title section)
                     */
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
                    /* If the level 1 card is titled "Rooms", do the following code which
                    *  builds the second and third portion of the row (the buttons) */
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
                            //bedroom
                            DropdownMenuItem(
                                onClick = {
                                    addRoomList.add(RoomClass("Bedroom", "Bedroom"))
                                    //addRoomList.add("Bedroom")
                                    addRoomShowMenu = !addRoomShowMenu
                                })
                            { Text(text = "Bedroom") }

                            //bathroom
                            DropdownMenuItem(onClick = {
                                //addRoomList.add("Bathroom")
                                addRoomShowMenu = !addRoomShowMenu
                            })
                            { Text(text = "Bathroom") }

                            //kitchen
                            DropdownMenuItem(
                                onClick = {
                                    //addRoomList.add("Kitchen")
                                    addRoomShowMenu = !addRoomShowMenu
                                })
                            { Text(text = "Kitchen") }

                            //Dining room
                            DropdownMenuItem(
                                onClick = {
                                    //addRoomList.add("Dining Room")
                                    addRoomShowMenu = !addRoomShowMenu
                                })
                            { Text(text = "Dining Room") }

                            //living room
                            DropdownMenuItem(
                                onClick = {
                                    //addRoomList.add("Living Room")
                                    addRoomShowMenu = !addRoomShowMenu
                                })
                            { Text(text = "Living Room") }

                            //outdoors
                            DropdownMenuItem(
                                onClick = {
                                    //addRoomList.add("Outdoors")
                                    addRoomShowMenu = !addRoomShowMenu
                                })
                            { Text(text = "Outdoors") }

                            //garage
                            DropdownMenuItem(
                                onClick = {
                                    //addRoomList.add("Garage")
                                    addRoomShowMenu = !addRoomShowMenu
                                })
                            { Text(text = "Garage") }

                            //utility room
                            DropdownMenuItem(
                                onClick = {
                                    //addRoomList.add("Utility Room")
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

                        // creates the up and down arrow icon for if 1st level card has been expanded
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
                    /* if the level 1 card is not titled "Rooms", do the following code which
                    *  builds the second and third sections of the row (the buttons) */
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
                //
                IsCardExpanded( expandedState, addRoomList, title )
            }

        }
    }


@ExperimentalMaterialApi
@Composable
// Determines if the card is expanded
fun IsCardExpanded(expandedState: Boolean, addRoomList: SnapshotStateList<RoomClass>, title: String) {
    // True?
    if ( expandedState ){
        // Do this if the title is 'Rooms'
        if (title == "Rooms"){

            // passes each item on the addRoomList into expandable card level

            //addRoomList.forEach{position -> ExpandableCardLevelTwo(title = position as String)}
            //addRoomList.forEach{position -> ExpandableCardLevelTwo(title = position as String)}
            addRoomList.forEach{position -> ExpandableCardLevelTwo(title = position.name)}
        }

        // Do this if the title is 'Common Tasks'
        if ( title == "Common Tasks" ){
            /* following code needs to be replaced with a list of tasks that are
            *  shared between multiple rooms
            */
            Text(text = "Here are the common tasks")
        }
        // Do this if the title is 'UpcomingTasks'
        if ( title == "Upcoming Tasks" ){
            /* following code needs to be replaced with a list of tasks in order
             * from most needed to least
             */
            Text(text = "here are the upcoming tasks")
        }

    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun ExpandableCardPreview(){
    ExpandableCard("Rooms")
}

