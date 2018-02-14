package com.example.ansh.modernnewsapp.androidmanagers

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ansh on 13/02/18.
 */

@Singleton
class NetManager @Inject constructor(var applicationContext: Context) {

    // get() means custom getter
    val isConnectedToInternet: Boolean?
        get() {
            val connManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

            val ni = connManager.activeNetworkInfo
            return ni != null && ni.isConnected
        }
}