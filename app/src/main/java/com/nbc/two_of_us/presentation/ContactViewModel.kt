package com.nbc.two_of_us.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbc.two_of_us.data.ContactInfo

class ContactViewModel : ViewModel() {

    private val _contactInfoLiveData = MutableLiveData<List<ContactInfo>>()
    val contactInfoLiveData : MutableLiveData<List<ContactInfo>>
        get() = _contactInfoLiveData

    fun getContactInfo() : LiveData<List<ContactInfo>> {
        return contactInfoLiveData
    }

    fun addContactInfo(contactList: List<ContactInfo>) {
        val currentList = contactList.toMutableList()
        contactInfoLiveData.value = currentList
    }
}