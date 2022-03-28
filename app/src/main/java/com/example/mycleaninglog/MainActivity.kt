package com.example.mycleaninglog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycleaninglog.dto.myRoom
import com.example.mycleaninglog.dto.User
import com.example.mycleaninglog.ui.theme.MyCleaningLogTheme
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.ext.android.viewModel




class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModel<MainViewModel>()


    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            firebaseUser?.let {
                val user = User(it.uid, "")
                viewModel.user = user
                viewModel.listenToMyRooms()
            }
            val user by viewModel.users.observeAsState(initial = emptyList())
            val myRooms by viewModel.myRooms.observeAsState(initial = emptyList())
            val myCleaningTasks by viewModel.cleaningTasks.observeAsState(initial = emptyList())


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

                            //call a header with title and logo

                        // Login button
                        Button(
                            onClick = {
                                signIn()
                            }
                        ) {
                            Text(
                                text = "Login",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                            ExpandableCard(title = "Rooms", myRooms = myRooms, viewModel = viewModel,c = this@MainActivity, myCleaningTasks = myCleaningTasks)
                            ExpandableCard(title = "Common Tasks", viewModel = viewModel, c = this@MainActivity, myCleaningTasks = myCleaningTasks)
                            ExpandableCard(title = "Upcoming Tasks", viewModel = viewModel, c = this@MainActivity, myCleaningTasks = myCleaningTasks)

                    }
                }
            }
        }
    }
    fun signIn() {
        var providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        val signinIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signinIntent)
    }

    var firebaseUser: FirebaseUser? = null

    private val signInLauncher = registerForActivityResult (
        FirebaseAuthUIActivityResultContract()
    ) {
            res -> this.signInResult(res)
    }

    private fun signInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            firebaseUser = FirebaseAuth.getInstance().currentUser
            firebaseUser?.let {
                val user = User(it.uid, it.displayName)
                viewModel.user = user
                viewModel.saveUser()
            }

        } else {
            Log.e("MainActivity.kt", "Error logging in " + response?.error?.errorCode)

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


