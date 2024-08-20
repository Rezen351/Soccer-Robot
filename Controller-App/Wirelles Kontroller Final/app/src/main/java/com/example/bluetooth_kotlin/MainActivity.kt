package com.example.wirelesscontroller

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.wirelesscontroller.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBluetooth.setOnClickListener {
            val intent = Intent(this, BluetoothActivity::class.java)
            startActivity(intent)

        }
        binding.buttonWifi.setOnClickListener {
            val intent = Intent(this, WifiActivity::class.java)
            startActivity(intent)
        }
    }
}