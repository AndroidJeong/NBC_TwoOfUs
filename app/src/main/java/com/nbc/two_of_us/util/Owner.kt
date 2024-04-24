package com.nbc.two_of_us.util

import androidx.lifecycle.MutableLiveData
import com.nbc.two_of_us.data.ContactInfo

object Owner {
    val contactLiveData = MutableLiveData<ContactInfo>()
}