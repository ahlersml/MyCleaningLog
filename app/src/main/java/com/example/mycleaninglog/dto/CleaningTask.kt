package com.example.mycleaninglog.dto



data class CleaningTask(var CleaningTaskName:String = "", var CleaningTaskId:String = ""){

    override fun toString(): String {
        return "$CleaningTaskName"
    }
}
