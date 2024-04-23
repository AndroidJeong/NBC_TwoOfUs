package com.nbc.two_of_us.presentation.contact_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.databinding.FragmentContactDetailBinding
import com.nbc.two_of_us.util.ObserverOwner

class ContactDetailFragment : Fragment() {

    private var _binding: FragmentContactDetailBinding? = null
    private val binding: FragmentContactDetailBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val someRandomContactInfo = ContactManager.getAll().random()
        val updatedInfo = someRandomContactInfo.copy(like = someRandomContactInfo.like.not())

        ContactManager.update(updatedInfo)
        ObserverOwner.notifyUpdate(updatedInfo)
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    companion object {
        // ContactListFragment에서 연락처 정보를 Bundle로 넘기기 위한 Key 값입니다.
        const val BUNDLE_KEY_FOR_CONTACT_INFO = "BUNDLE_KEY_FOR_CONTACT_INFO_ContactDetailFragment"
    }
}