package com.nbc.two_of_us.util

import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.presentation.contact.ContactListFragment

object ObserverOwner {

    val observers = mutableListOf<Observer>()

    fun register(observer: Observer) {
        observers.add(observer)
    }

    fun notifyUpdate(contactInfo: ContactInfo) {
        for (observer in observers) {
            observer.update(contactInfo)
        }
    }

    fun unRegister(observer: Observer) {
        observers.remove(observer)
    }
}