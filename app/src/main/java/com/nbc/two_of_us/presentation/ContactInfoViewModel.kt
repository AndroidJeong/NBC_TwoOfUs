package com.nbc.two_of_us.presentation

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.util.Owner

class ContactInfoViewModel : ViewModel() {
    private val owner = Owner()

    private val _contactLiveData = MutableLiveData<ContactInfo>() //바뀌는 값

    val contactLiveData : LiveData<ContactInfo>
        get() = _contactLiveData

    fun update(contactInfo: ContactInfo) {
        _contactLiveData.value = contactInfo
    }
}