package com.example.mycleaninglog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycleaninglog.ui.theme.MyCleaningLogTheme


class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCleaningLogTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                            .padding(24.dp)
                    ) {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HomeScreen() {
    // calls the 1st level expandable cards. hardcoded because they should never change. These make up the main menu
    ExpandableCard(title = "Rooms")
    ExpandableCard(title = "Common Tasks")
    ExpandableCard(title = "Upcoming Tasks")
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyCleaningLogTheme {
        ExpandableCard(title = "Rooms")
        ExpandableCard(title = "Common Tasks")
        ExpandableCard(title = "Upcoming Tasks")
    }
}