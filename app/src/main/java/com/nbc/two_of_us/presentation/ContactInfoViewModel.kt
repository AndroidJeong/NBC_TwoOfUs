package com.nbc.two_of_us.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbc.two_of_us.data.ContactInfo

class ContactInfoViewModel : ViewModel() {
    private val _contactLiveData = MutableLiveData<ContactInfo>() //바뀌는 값
    val contactLiveData : LiveData<ContactInfo>
        get() = _contactLiveData

    private val _contactLiveDataList = MutableLiveData<List<ContactInfo>>()
    val contactLiveDataList : LiveData<List<ContactInfo>>
        get() = _contactLiveDataList

    fun update(contactInfo: ContactInfo) {
        _contactLiveData.value = contactInfo
    }

    fun getContactInfo() : LiveData<List<ContactInfo>> {
        return contactLiveDataList
    }

    fun updateList(contactList: List<ContactInfo>) {
        val currentList = contactList.toMutableList()
        _contactLiveDataList.value = currentList
    }
}