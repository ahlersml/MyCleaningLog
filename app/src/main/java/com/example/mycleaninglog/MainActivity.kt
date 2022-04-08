package com.example.mycleaninglog

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mycleaninglog.dto.myRoom
import com.example.mycleaninglog.dto.User
import com.example.mycleaninglog.ui.theme.Gray
import com.example.mycleaninglog.ui.theme.MyCleaningLogTheme
import com.example.mycleaninglog.ui.theme.RegularBlue
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Contains the layout of the application along with methods for signing in/out and push notifications.
 */
class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModel<MainViewModel>()
    var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private lateinit var auth: FirebaseAuth

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]
        setContent {
            firebaseUser?.let {
                val user = User(it.uid, "")
                viewModel.user = user
                viewModel.listenToMyRooms()
            }

            val myRooms by viewModel.myRooms.observeAsState(initial = emptyList())
            val myCleaningTasks by viewModel.cleaningTasks.observeAsState(initial = emptyList())
            val montFont = FontFamily(
                Font(R.font.mont, FontWeight.Normal)
            )
            val dark = isSystemInDarkTheme()
            val color = if (dark) Color.White else Color.Black


            MyCleaningLogTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                            .padding(24.dp)
                    ) {
                        Box(
                           modifier = Modifier
                               .width(1000.dp)
                               .background(RegularBlue)
                               .height(60.dp)
                        ){
                            val image: Painter = painterResource(id = R.drawable.mycleaninglog)
                            Image(
                                painter = image,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.CenterStart) )
                            Text(
                                text = "MyCleaningLog",
                                fontSize = 25.sp,
                                modifier = Modifier.align(Alignment.Center),
                                fontFamily = montFont,
                                color = color,
                                )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        logIn()
                        Spacer(modifier = Modifier.height(2.dp))
                        if(firebaseUser != null) {
                            pushNotification()
                            Spacer(modifier = Modifier.height(7.dp))
                            ExpandableCard(title = "Rooms", myRooms = myRooms, viewModel = viewModel,c = this@MainActivity, myCleaningTasks = myCleaningTasks)
                            Spacer(modifier = Modifier.height(2.dp))
                            ExpandableCard(title = "Common Tasks", viewModel = viewModel, c = this@MainActivity, myCleaningTasks = myCleaningTasks)
                            Spacer(modifier = Modifier.height(2.dp))
                            ExpandableCard(title = "Upcoming Tasks", viewModel = viewModel, c = this@MainActivity, myCleaningTasks = myCleaningTasks)
                            Spacer(modifier = Modifier.height(7.dp))
                            logOut()
                        }
                    }
                }
            }
        }
    }

    /**
     * Allows users to sign in through a log in button
      */
    @Composable
    fun logIn(){
        val louisFont = FontFamily(
            Font(R.font.louis, FontWeight.Normal)
        )
        val dark = isSystemInDarkTheme()
        val color = if (dark) Color.White else Color.Black

        Button(
            onClick = {
                signIn()
            }
        ) {
            Text(
                text = "Login",
                fontFamily = louisFont,
                color = color,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    /**
     * Allows users to sign out through a log out button
     */
    @Composable
    fun logOut(){
        val louisFont = FontFamily(
            Font(R.font.louis, FontWeight.Normal)
        )
        val dark = isSystemInDarkTheme()
        val color = if (dark) Color.White else Color.Black

        Button(
            onClick = {
                signOut()
            }
        ) {
            Text(
                text = "Logout",
                fontFamily = louisFont,
                color = color,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    /**
     * Creates user in firebase for future reference
     */
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
                val intent = intent
                finish()
                startActivity(intent)
            }

        } else {
            Log.e("MainActivity.kt", "Error logging in " + response?.error?.errorCode)

        }
    }

    private fun signOut() {
        auth.signOut()
        val intent = intent
        finish()
        startActivity(intent)
    }

}

/**
 * Creates push notifications
 */
@Composable
fun pushNotification(){
    val context = LocalContext.current
    val channelId = "MakeitEasy"
    val notificationId = 0
    val myBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mycleaninglog)
    val louisFont = FontFamily(
        Font(R.font.louis, FontWeight.Normal)
    )
    val dark = isSystemInDarkTheme()
    val color = if (dark) Color.White else Color.Black

    LaunchedEffect(Unit) {
        createNotificationChannel(channelId, context)
    }
    Button(
        onClick = {
            largeTextWithBigIconNotification(
                context,
                channelId,
                notificationId,
                "MyCleaningLog",
                "Welcome to my cleaning log. Glad you are using the application.",
                myBitmap
            )
        }
    ) {
        Text(
            text = "Test Push Notification Here",
            color = color,
            fontFamily = louisFont,
            fontSize = 16.sp,
            modifier = Modifier.padding(5.dp)
        )
    }

}

fun createNotificationChannel(channelId: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "MakeitEasy"
        val desc = "My Channel MakeitEasy"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = desc
        }
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
fun largeTextWithBigIconNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    largeIcon: Bitmap,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.mycleaninglog)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setLargeIcon(largeIcon)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(
                    textContent
                )
        )
        .setPriority(priority)
    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}
