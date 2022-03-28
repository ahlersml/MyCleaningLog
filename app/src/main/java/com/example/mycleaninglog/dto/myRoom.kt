package com.example.mycleaninglog.dto

import com.google.firebase.firestore.Exclude

data class myRoom(var myRoomName: String = "", var myRoomID:String = "", var uniqueID:String = "", var expanded: Boolean = false ){


    override fun toString(): String {
        return "$myRoomName"
    }
}
