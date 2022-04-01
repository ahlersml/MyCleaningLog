package com.example.mycleaninglog.dto



data class MyRoom(var myRoomName: String = "", var myRoomID:String = "", var uniqueID:String = "", var expanded: Boolean = false ){


    override fun toString(): String {
        return "$myRoomName"
    }
}
