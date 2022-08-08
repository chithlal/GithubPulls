package com.navi.githubpulls.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import java.text.SimpleDateFormat

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
fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false
    if (VERSION.SDK_INT >= VERSION_CODES.Q) {
        val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
        val networks = cm.allNetworks
        for (n in networks) {
            val nInfo = cm.getNetworkInfo(n)
            if (nInfo != null && nInfo.isConnected) return true
        }
    } else {
        val networks = cm.allNetworkInfo
        for (nInfo in networks) {
            if (nInfo != null && nInfo.isConnected) return true
        }
    }
    return false
}