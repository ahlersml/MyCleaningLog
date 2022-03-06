package com.example.mycleaninglog.dto



data class Room(var roomName: String = "", var roomId:String = ""){

    override fun toString(): String {
        return "$roomName"
    }
}
