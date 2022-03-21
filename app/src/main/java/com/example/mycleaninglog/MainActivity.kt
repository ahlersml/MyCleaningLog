package com.example.mycleaninglog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycleaninglog.dto.myRoom
import com.example.mycleaninglog.ui.theme.MyCleaningLogTheme
import org.koin.androidx.viewmodel.ext.android.viewModel



class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModel<MainViewModel>()


    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val myRooms by viewModel.myRooms.observeAsState(initial = emptyList())

            MyCleaningLogTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                            .padding(24.dp)
                    ) {
                        //calls the 1st level expandable cards. hardcoded because they should never change. These make up the main menu
                            ExpandableCard(title = "Rooms", myRooms = myRooms, inViewModel = viewModel)
                            ExpandableCard(title = "Common Tasks", inViewModel = viewModel)
                            ExpandableCard(title = "Upcoming Tasks", inViewModel = viewModel)

                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HomeScreen(myRooms: List<myRoom> = ArrayList<myRoom>()) {
    //ExpandableCard(title = "Rooms", myRooms = myRooms, )
    //ExpandableCard(title = "Common Tasks")
    //ExpandableCard(title = "Upcoming Tasks")
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyCleaningLogTheme {
        //ExpandableCard(title = "Rooms")
        //ExpandableCard(title = "Common Tasks")
        //ExpandableCard(title = "Upcoming Tasks")
    }
}