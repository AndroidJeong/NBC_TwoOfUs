package com.nbc.two_of_us.presentation.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.nbc.two_of_us.databinding.FragmentViewPagerBinding
import com.nbc.two_of_us.presentation.model.TabType


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding: FragmentViewPagerBinding
        get() = _binding!!

    private var adapter: ContactViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackPressed()
        setViewPager()
        setTabLayout()
    }

    private fun setBackPressed() = with(binding) {
        requireActivity().onBackPressedDispatcher.addCallback(this@ViewPagerFragment) {
            if (isEnabled) {
                if (vp.currentItem == 0) {
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                } else {
                    vp.currentItem = vp.currentItem - 1
                }
            }
        }
    }

    private fun setViewPager() = with(binding) {
        adapter = ContactViewPagerAdapter(this@ViewPagerFragment.childFragmentManager, viewLifecycleOwner.lifecycle)
        vp.adapter = adapter
    }

    private fun setTabLayout() = with(binding) {
        TabLayoutMediator(tabLayout, vp) { tab, position ->
            val tabType = TabType.from(position)
            tab.text = getString(tabType.tabName)
        }.attach()
    }

    override fun onDestroyView() {
        // 이 코드를 호출하지 않으면 FragmentStateAdapter의 onDetachedFromRecyclerView가 호출되지 않음
        binding.vp.adapter = null
        _binding = null

        super.onDestroyView()
    }
}