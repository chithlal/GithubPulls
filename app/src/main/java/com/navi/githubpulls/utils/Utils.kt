package com.navi.githubpulls.utils

import java.text.SimpleDateFormat
import java.util.Date

class ResponseState<out T>(
    val state: State,
    val message: String?,
    val data: T?
){
    companion object {
        fun <T> success(data:T?): ResponseState<T>{
            return ResponseState(State.SUCCESS, null,data)
        }

        fun <T> error(msg:String, data:T?): ResponseState<T>{
            return ResponseState(State.ERROR, msg, data)
        }

        fun <T> loading(data:T?): ResponseState<T>{
            return ResponseState(State.LOADING, null,data)
        }
    }
}

enum class State{
    SUCCESS, ERROR, LOADING
}

fun getDateText(dateText: String): String{
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateText)
    val outPutDateFormat = SimpleDateFormat("E LLLL yyyy HH:mm")

   return outPutDateFormat.format(date).toString()
}