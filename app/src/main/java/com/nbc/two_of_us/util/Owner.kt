package com.nbc.two_of_us.util

import com.nbc.two_of_us.data.ContactInfo

object Owner {
    val observerList = mutableListOf<Observer>()

    fun register(observer: Observer) {
        observerList.add(observer)
    }

    fun notifyUpdate(contactInfo: ContactInfo) {
        for (observer in observerList) {
            observer.updateData(contactInfo)
        }
    }

    fun unRegister(observer: Observer) {
        observerList.remove(observer)
    }
}