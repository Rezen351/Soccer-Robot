package com.example.wirelesscontroller.helperBT

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.wirelesscontroller.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

abstract class WiFiActivityWrapper : AppCompatActivity() {

    private val PERMISSIONS_INTERNET = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE
    )

    var address: String = "10.43.125.181";

    fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_INTERNET, 1)
        }
    }


    fun connectAddress(addressInput: String) {
        checkPermissions()
        address = addressInput
        sendHttpGetRequest(address, "angle=")
    }

    fun sendHttpGetRequest(host: String, query: String) {
        val url = "http://$host/?$query"
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000 // Set timeout to 5 seconds
                connection.readTimeout = 5000    // Read timeout
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val wifiInfo = getWifiInfo()
                    val signalStrength = wifiInfo?.rssi ?: 0 // Get signal strength in dBm
                    val signalStrengthText = "Signal Strength: $signalStrength dBm\n $response"
                    launch(Dispatchers.Main) {
//                        Toast.makeText(this@WiFiActivityWrapper, "Connected successfully", Toast.LENGTH_SHORT).show()
                        findViewById<View>(R.id.textView3).setBackgroundResource(R.drawable.circle_green)
                        findViewById<TextView>(R.id.connectionStatus).apply {
                            text = signalStrengthText
                        }
                    }
                    Log.d(TAG, "Connected successfully. Response: $response")
                } else {
                    launch(Dispatchers.Main) {
//                       Toast.makeText(this@WiFiActivityWrapper, "Connection failed with status: $responseCode", Toast.LENGTH_SHORT).show()
                        findViewById<View>(R.id.textView3).setBackgroundResource(R.drawable.circle_disabled)
                        findViewById<TextView>(R.id.connectionStatus).apply {
                            text = "Connection failed with status: $responseCode"
                        }
                    }
                    Log.d(TAG, "Connection failed with status: $responseCode")
                }
            } catch (e: Exception) { // Catching all exceptions to handle different types of errors.
                launch(Dispatchers.Main) {
                    Log.e(TAG, "Failed to connect: ${e.localizedMessage}")
//                    Toast.makeText(this@WiFiActivityWrapper, "Failed to connect: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getWifiInfo(): WifiInfo? {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        return wifiManager.connectionInfo
    }
    fun sendWiFiMessage(angle: String) {
        sendHttpGetRequest(address, "angle=$angle")
    }

}