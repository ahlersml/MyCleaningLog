package com.example.mycleaninglog.dto

import com.google.firebase.firestore.Exclude

data class MyRoom(var myRoomName: String = "", var myRoomId:String = ""){

    override fun toString(): String {
        return "$myRoomName"
    }
}
