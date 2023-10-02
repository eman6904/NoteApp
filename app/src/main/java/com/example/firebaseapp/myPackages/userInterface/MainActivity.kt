package com.example.firebaseapp.myPackages.userInterface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}