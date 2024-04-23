package com.nbc.two_of_us.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager

class ContactViewModel : ViewModel() {

    private val _contactInfoLiveData = MutableLiveData<List<ContactInfo>>()

    init {
        loadContactInfo()
    }

    fun loadContactInfo() {
        _contactInfoLiveData.value = ContactManager.getAll()
    }
}