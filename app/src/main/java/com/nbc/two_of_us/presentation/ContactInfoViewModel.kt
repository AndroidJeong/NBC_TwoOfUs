package com.nbc.two_of_us.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.util.Event

class ContactInfoViewModel : ViewModel() {

    private val _contactLiveDataForEdit = MutableLiveData<Event<ContactInfo>>()
    val contactLiveDataForEdit: LiveData<Event<ContactInfo>> = _contactLiveDataForEdit

    private val _contactLiveDataForDelete = MutableLiveData<Event<ContactInfo>>()
    val contactLiveDataForDelete: LiveData<Event<ContactInfo>> = _contactLiveDataForDelete

    private val _newContactInfo = MutableLiveData<Event<ContactInfo>>()
    val newContactInfo: LiveData<Event<ContactInfo>>
        get() = _newContactInfo

    /**
     * @author 이준영
     * for detail fragment
     * */
    fun setContactForEdit(contactInfo: ContactInfo, count: Int = 2) {
        _contactLiveDataForEdit.value = Event(contactInfo, count = count)
    }

    /**
     * @author 이준영
     * for delete a contact info
     * */
    fun setDeletedContact(contactInfo: ContactInfo) {
        _contactLiveDataForDelete.value = Event(contactInfo)
    }

    /**
     * @author 이종성
     * 새로운 연락처가 추가되었을 때를 위함
     * */
    fun setNewContactInfo(contactInfo: ContactInfo) {
        _newContactInfo.value = Event(contactInfo)
    }
}