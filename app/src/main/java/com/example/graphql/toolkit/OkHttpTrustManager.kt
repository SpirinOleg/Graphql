package com.example.graphql.toolkit

import android.util.Log
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

object OkHttpTrustManager {

    val trustManager: X509TrustManager = object : X509TrustManager {

        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            Log.i(TAG, "checkClientTrusted")
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            Log.i(TAG, "checkServerTrusted")
            chain?.forEach {
                val error = try {
                    it.checkValidity()
                    null
                } catch (e: Exception) {
                    Log.e(TAG, "checkServerTrusted", e)
                    e
                }
                Log.i(TAG, "checkServerTrusted: validity: ${error == null}")
                if (error != null) throw error
            }
        }

        override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
    }

    private val sslContext: SSLContext = SSLContext.getInstance("SSL").also {
        it.init(null, arrayOf(trustManager), java.security.SecureRandom())
    }

    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

    //      TODO custom hostnameVerifier for backend server
    val hostnameVerifier: HostnameVerifier = HostnameVerifier { hostname, _ ->
        Log.i(TAG, "hostname: $hostname")
        true
    }

    private const val TAG = "OkHttpTrustManager"
}


fun ByteArray.toHexFormattedString(): String {
    val str = StringBuilder(size * 2)
    for (c in this) {
        var h = Integer.toHexString(c.toInt())
        val l = h.length
        if (l == 1) h = "0$h"
        if (l > 2) h = h.substring(l - 2, l)
        str.append(h.toUpperCase(Locale.getDefault()))
    }
    return str.toString()
}