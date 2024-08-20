package com.example.wirelesscontroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import androidx.navigation.ui.AppBarConfiguration
import com.example.wirelesscontroller.databinding.ActivityWifiBinding
import com.example.wirelesscontroller.helperBT.WiFiActivityWrapper
import com.jackandphantom.joystickview.JoyStickView

class WifiActivity : WiFiActivityWrapper() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityWifiBinding
    private var lastMessageSentTime: Long = 0

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWifiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Menyembunyikan ActionBar
        supportActionBar?.hide()

        connectAddress(binding.addressText.text.toString())

        binding.btnConnect.setOnClickListener {
            connectAddress(binding.addressText.text.toString())
        }

        //Pergerakkan Joystick
        val joyStickView = findViewById<JoyStickView>(R.id.joy)
        joyStickView.setOnMoveListener(object : JoyStickView.OnMoveListener {
            override fun onMove(angle: Double, strength: Float) {
                // Simpan arah dan kekuatan
                var message = String.format("Joystick, %.1f, %.1f", angle, strength)
                sendWiFiMessage(message)
                // Tampilkan ke Logcat
                Log.d("JoyStick", "${message}")

            }
        })
        // Pendengar sentuhan untuk tombol R
        val buttonR = findViewById<Button>(R.id.buttonRWifi)
        buttonR.setOnTouchListener { _, event ->
            handleButtonTouch(event, "R")
            true // Mengembalikan true agar sentuhan tidak diteruskan ke komponen lain
        }
        // Implementasi pendengar sentuhan untuk tombol L,
        val buttonL = findViewById<Button>(R.id.buttonLWifi)
        buttonL.setOnTouchListener { _, event ->
            handleButtonTouch(event, "L")
            true
        }
        // Implementasi pendengar sentuhan untuk tombol A, B, X, dan Y secara serupa
        val buttonA = findViewById<Button>(R.id.buttonA)
        buttonA.setOnTouchListener { _, event ->
            handleButtonTouch(event, "A")
            true
        }

        val buttonB = findViewById<Button>(R.id.buttonB)
        buttonB.setOnTouchListener { _, event ->
            handleButtonTouch(event, "B")
            true
        }

        val buttonX = findViewById<Button>(R.id.buttonX)
        buttonX.setOnTouchListener { _, event ->
            handleButtonTouch(event, "X")
            true
        }

        val buttonY = findViewById<Button>(R.id.buttonY)
        buttonY.setOnTouchListener { _, event ->
            handleButtonTouch(event, "Y")
            true
        }
        // Implementasi pendengar klik untuk btnDevelopment
        val btnDevelopment = findViewById<Button>(R.id.btnDevelopment)
        val developmentText = findViewById<EditText>(R.id.developmentText)

        btnDevelopment.setOnClickListener {
            val text = developmentText.text.toString()
            sendWiFiMessage(text)
            Log.d("Development", "Message sent: $text")
            developmentText.text.clear()
        }
    }
    private fun handleButtonTouch(event: MotionEvent, button: String) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Ketika tombol ditekan
                Log.d("Button", "Button pressed: $button")
                val messageButtonPressed = "Button, $button, Pressed"
                sendWiFiMessage(messageButtonPressed)
            }
            MotionEvent.ACTION_UP -> {
                // Ketika tombol dilepas yang sesuai
                val messageButtonRelease = "Button, $button, Release"
                sendWiFiMessage(messageButtonRelease)
                Log.d("Button", "Button released: $button")
            }
        }
    }


}
