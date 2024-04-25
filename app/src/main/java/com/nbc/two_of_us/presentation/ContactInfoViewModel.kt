package com.nbc.two_of_us.presentation

import android.provider.ContactsContract.Contacts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.util.Event

class ContactInfoViewModel : ViewModel() {

    /**
     * @author 이준영
     * for detail fragment
     * */
    private val _contactLiveDataForEdit = MutableLiveData<Event<ContactInfo>>()
    val contactLiveDataForEdit: LiveData<Event<ContactInfo>> = _contactLiveDataForEdit
    fun setContactForEdit(contactInfo: ContactInfo) {
        _contactLiveDataForEdit.value = Event(contactInfo)
    }


    /**
     * @author 이준영
     * for delete a contact info
     * */
    private val _deletedContactLiveData = MutableLiveData<Event<ContactInfo>>()
    val deletedContactLiveData: LiveData<Event<ContactInfo>> = _deletedContactLiveData
    fun setDeletedContact(contactInfo: ContactInfo) {
        _deletedContactLiveData.value = Event(contactInfo)
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


    private val _contactLiveData = MutableLiveData<Event<ContactInfo>>() //바뀌는 값
    val contactLiveData : LiveData<Event<ContactInfo>>
        get() = _contactLiveData
}