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
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.mycleaninglog.dto.myRoom

@ExperimentalMaterialApi
@Composable
fun ExpandableCardLevelTwo(
    //expandable card 2 variables
    c: Context,
    selectedRoom: myRoom,
    viewModel: MainViewModel
    ) {

    // used for expandable card level 2 functionality
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

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
            //onClick = { expandedState = !expandedState }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 12.dp, 0.dp, 12.dp)
            ) {
                //following code builds the row that the information will sit within
                Row(verticalAlignment = Alignment.CenterVertically) {

                    //creates the up and down arrow icon for if 2nd level card has been expanded
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
                        //var c = MainActivity()
                        RoomSettingsDialogBox(c = c, selectedRoomSettings = selectedRoom, viewModel = viewModel)
                    }

                }
                //what happens if the card is expanded
                if (expandedState) {
                    //currently just displays a text line
                    //needs to be replaced with code to show all tasks and timers

                    Text(text = selectedRoom.uniqueID)

                }
            }

        }


    }

