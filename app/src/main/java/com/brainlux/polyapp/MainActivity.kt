package com.brainlux.polyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.brainlux.polyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isAttached = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initNavigation()
    }

    private fun initNavigation() {
        if (!isAttached) {
            val navController = findNavController(R.id.navHost)

            val navInflater = navController.navInflater
            val mainGraph = navInflater.inflate(R.navigation.main_nav_graph)
            navController.graph = mainGraph
            isAttached = true
        }
    }
}