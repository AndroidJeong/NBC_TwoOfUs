package com.nbc.two_of_us.util

import com.nbc.two_of_us.data.ContactInfo

interface Observer {
    fun updateData(contactInfo: ContactInfo)
}