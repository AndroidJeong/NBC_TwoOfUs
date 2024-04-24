package com.nbc.two_of_us.presentation.viewpager

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.presentation.contact.ContactListFragment
import com.nbc.two_of_us.presentation.contact_detail.ContactDetailFragment
import com.nbc.two_of_us.presentation.model.TabType

class ContactViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = TabType.entries.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TabType.CONTACT.position -> ContactListFragment()
            TabType.MY_PAGE.position -> ContactDetailFragment().apply {
                // TODO : tempUserContactInfo가 아닌 사용자 정보를 반환하도록 수정해야 합니다.
                //  현재 ContactManager에 사용자 정보를 가져오는 함수가 없어서 임의로 설정했습니다.
                arguments = Bundle().apply {
                    putParcelable(ContactDetailFragment.BUNDLE_KEY_FOR_CONTACT_INFO, tempUserContactInfo)
                }
            }

            else -> throw IllegalArgumentException("Not implemented yet")
        }
    }

    companion object {
        val tempUserContactInfo = ContactInfo(
            rawContactId = 0,
            name = "",
            thumbnail = Uri.parse("R.drawable.sample_danielle"),
            phone = "",
            email = "",
            memo = "",
            like = false,
        )
    }
}