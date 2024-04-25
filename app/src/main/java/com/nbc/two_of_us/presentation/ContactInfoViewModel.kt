package com.nbc.two_of_us.presentation

import android.provider.ContactsContract.Contacts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbc.two_of_us.data.ContactInfo

class ContactInfoViewModel : ViewModel() {

    /**
     * @author 이준영
     * for detail fragment
     * */
    private val _contactLiveDataForEdit = MutableLiveData<ContactInfo>()
    val contactLiveDataForEdit: LiveData<ContactInfo> = _contactLiveDataForEdit
    fun setContactForEdit(contactInfo: ContactInfo) {
        _contactLiveDataForEdit.value = contactInfo
    }


    /**
     * @author 이준영
     * for delete a contact info
     * */
    private val _deletedContactLiveData = MutableLiveData<ContactInfo>()
    val deletedContactLiveData: LiveData<ContactInfo> = _deletedContactLiveData
    fun setDeletedContact(contactInfo: ContactInfo) {
        _deletedContactLiveData.value = contactInfo
    }

    /**
     * @author 이종성
     * 새로운 연락처가 추가되었을 때를 위함
     * */
    private val _newContactInfo = MutableLiveData<ContactInfo>()
    val newContactInfo: LiveData<ContactInfo>
        get() = _newContactInfo

    fun setNewContactInfo(contactInfo: ContactInfo) {
        _newContactInfo.value = contactInfo
    }


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