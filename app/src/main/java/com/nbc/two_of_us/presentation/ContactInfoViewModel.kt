package com.nbc.two_of_us.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.util.Event

class ContactInfoViewModel : ViewModel() {

    /**
     * @author 이준영
     * for detail fragment
     * */
    private val _contactLiveDataForEdit = MutableLiveData<Event<ContactInfo>>()
    val contactLiveDataForEdit: LiveData<Event<ContactInfo>> = _contactLiveDataForEdit
    fun setContactForEdit(contactInfo: ContactInfo) {
        _contactLiveDataForEdit.value = Event(contactInfo, count = 2)
    }


    /**
     * @author 이준영
     * for delete a contact info
     * */
    private val _contactLiveDataForDelete = MutableLiveData<Event<ContactInfo>>()
    val contactLiveDataForDelete: LiveData<Event<ContactInfo>> = _contactLiveDataForDelete
    fun setDeletedContact(contactInfo: ContactInfo) {
        _contactLiveDataForDelete.value = Event(contactInfo)
    }


    /**
     * @author 이종성
     * 새로운 연락처가 추가되었을 때를 위함
     * */
    private val _newContactInfo = MutableLiveData<Event<ContactInfo>>()
    val newContactInfo: LiveData<Event<ContactInfo>>
        get() = _newContactInfo

    fun setNewContactInfo(contactInfo: ContactInfo) {
        _newContactInfo.value = Event(contactInfo)
    }
}