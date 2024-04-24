package com.nbc.two_of_us.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nbc.two_of_us.databinding.ActivityMainBinding
import com.nbc.two_of_us.util.Owner

class MainActivity : AppCompatActivity() {

    private val owner = Owner()
    //activity viewModel

    private val viewmodel : ContactInfoViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}