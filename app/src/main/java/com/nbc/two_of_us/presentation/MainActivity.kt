package com.nbc.two_of_us.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.nbc.two_of_us.databinding.ActivityMainBinding
import com.nbc.two_of_us.presentation.model.TabType

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter: ContactViewPagerAdapter = ContactViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setViewPager()
        setTabLayout()
    }

    private fun setViewPager() = with(binding) {
        vp.adapter = adapter
    }

    private fun setTabLayout() = with(binding) {
        TabLayoutMediator(tabLayout, vp) { tab, position ->
            val tabType = TabType.from(position)
            tab.text = getString(tabType.tabName)
        }.attach()
    }
}