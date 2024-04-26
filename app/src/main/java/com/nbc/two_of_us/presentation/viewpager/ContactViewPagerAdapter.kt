package com.nbc.two_of_us.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nbc.two_of_us.presentation.contact.ContactListFragment
import com.nbc.two_of_us.presentation.contact_detail.ContactDetailFragment
import com.nbc.two_of_us.presentation.model.TabType

class ContactViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = TabType.entries.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TabType.CONTACT.position -> ContactListFragment()
            TabType.MY_PAGE.position -> ContactDetailFragment()

            else -> throw IllegalArgumentException("Not implemented yet")
        }
    }
}