package com.navi.githubpulls.utils

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