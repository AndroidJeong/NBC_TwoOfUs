package com.nbc.two_of_us.presentation.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.nbc.two_of_us.databinding.FragmentViewPagerBinding
import com.nbc.two_of_us.presentation.model.TabType


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding: FragmentViewPagerBinding
        get() = _binding!!

    private lateinit var adapter: ContactViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPager()
        setTabLayout()
    }

    private fun setViewPager() = with(binding) {
        adapter = ContactViewPagerAdapter(this@ViewPagerFragment)
        vp.adapter = adapter
    }

    private fun setTabLayout() = with(binding) {
        TabLayoutMediator(tabLayout, vp) { tab, position ->
            val tabType = TabType.from(position)
            tab.text = getString(tabType.tabName)
        }.attach()
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}