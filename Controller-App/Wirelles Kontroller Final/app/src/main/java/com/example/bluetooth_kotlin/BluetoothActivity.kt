package com.example.wirelesscontroller

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import com.example.wirelesscontroller.databinding.ActivityBluetoothBinding
import com.example.wirelesscontroller.helperBT.BTActivityWrapper
import com.jackandphantom.joystickview.JoyStickView

class BluetoothActivity : BTActivityWrapper() {
    private lateinit var binding: ActivityBluetoothBinding
    private val directions = mutableSetOf<Pair<String, Float>>() // Menggunakan Set untuk mencegah duplikat dan lebih mudah dalam menangani multitouch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Menyembunyikan ActionBar
        supportActionBar?.hide()

        initBluetooth()

        binding.btnConnect.setOnClickListener {
            connectBluetooth(binding.inputBluetooth.text.toString())
        }

        // Inisialisasi komponen joystick
        val joyStickView = findViewById<JoyStickView>(R.id.joy)
        joyStickView.setOnMoveListener(object : JoyStickView.OnMoveListener {
            override fun onMove(angle: Double, strength: Float) {
                // Ubah sudut menjadi arah 8 arah
                val direction = getDirection(angle)
                // Simpan arah dan kekuatan
                directions.add(direction to strength)
                // Tampilkan ke Logcat
                Log.d("JoyStick", "Direction: ${direction.first}, Strength: ${strength}")
                // Kirim Bluetooth
                sendBluetoothMessage("JoyStick: ${direction.first}, Strength: ${direction.second}")
            }
        })

        // Pendengar sentuhan untuk tombol R
        val buttonR = findViewById<Button>(R.id.buttonRBluetooth)
        buttonR.setOnTouchListener { _, event ->
            handleButtonTouch(event, "R")
            true // Mengembalikan true agar sentuhan tidak diteruskan ke komponen lain
        }

        // Implementasi pendengar sentuhan untuk tombol L, A, B, X, dan Y secara serupa
        val buttonL = findViewById<Button>(R.id.buttonLBluetooth)
        buttonL.setOnTouchListener { _, event ->
            handleButtonTouch(event, "L")
            true
        }

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
    }

    // Fungsi untuk menentukan arah berdasarkan sudut joystick
    private fun getDirection(angle: Double): Pair<String, Float> {
        return when {
            angle in 22.5..67.5 -> "NE" to 1.0f
            angle in 67.5..112.5 -> "N" to 1.0f
            angle in 112.5..157.5 -> "NW" to 1.0f
            angle in 157.5..202.5 -> "W" to 1.0f
            angle in 202.5..247.5 -> "SW" to 1.0f
            angle in 247.5..292.5 -> "S" to 1.0f
            angle in 292.5..337.5 -> "SE" to 1.0f
            else -> "E" to 1.0f
        }
    }

    // Fungsi untuk menangani sentuhan tombol
    private fun handleButtonTouch(event: MotionEvent, button: String) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Ketika tombol ditekan, simpan arah dan kekuatan
                directions.add(button to 1.0f)
                // Tampilkan ke Logcat
                Log.d("Button", "Button pressed: $button")
                sendBluetoothMessage("$button Pressed")
            }
            MotionEvent.ACTION_UP -> {
                // Ketika tombol dilepas, hapus arah dan kekuatan yang sesuai
                directions.remove(button to 1.0f)
                // Tampilkan ke Logcat
                Log.d("Button", "Button released: $button")
                sendBluetoothMessage("$button Released")
            }
        }
    }

    override fun onMessageSent(message: String) {
        Log.d("BluetoothActivity", "Message sent: $message")
    }

    override fun onMessageReceived(message: String) {
        Log.d("BluetoothActivity", "Message received: $message")
    }
}
private fun <E> MutableSet<E>.add(element: Pair<E, Float>) {
    // This can be removed
}
