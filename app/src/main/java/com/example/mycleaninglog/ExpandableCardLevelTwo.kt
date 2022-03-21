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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun ExpandableCardLevelTwo(
    //expandable text variables
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    )
    {

        // used for expandable card level 2 functionality
        var expandedState by remember { mutableStateOf(false) }
        val rotationState by animateFloatAsState(
            targetValue = if(expandedState) 180f else 0f)

        //used for drop down menu
        var dropDownMenu by remember{mutableStateOf(false)}

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
                        text = title,
                        fontSize = titleFontSize,
                        fontWeight = titleFontWeight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .background(color = Color.Cyan)
                            .weight(1f),
                        onClick = { dropDownMenu = !dropDownMenu }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Symbol"
                        )
                    }
                    DropdownMenu(
                        expanded = dropDownMenu,
                        onDismissRequest = { dropDownMenu = false }) {
                        //list of menu items to be displayed
                        //bedroom
                        DropdownMenuItem(
                            onClick = {
                                dropDownMenu = !dropDownMenu
                            })
                        { Text(text = "Rename Room") }

                        DropdownMenuItem(
                            onClick = {
                                dropDownMenu = !dropDownMenu
                            })
                        { Text(text = "Delete Room") }
                    }



                    }
                //what happens if the card is expanded
                if(expandedState){
                    //currently just displays a text line
                    //needs to be replaced with code to determine which room it is and display the proper information
                    Text(text = "hello")
                }
            }

        }
    }


@ExperimentalMaterialApi
@Composable
@Preview
fun ExpandableCardLevelTwoPreview(){
    //ExpandableCard("Rooms")
}