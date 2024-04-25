package com.nbc.two_of_us.data

import android.net.Uri

object ContactManager {
    // key: 연락처 정보 고유 값 Int
    // value: 연락처 정보 ContactInfo
    private val contacts: HashMap<Int, ContactInfo> = hashMapOf()

    /**
     * 연락처 추가 함수
     *
     * @return 이미 연락처가 존재하는 경우 추가하지 않고 false, 연락처가 추가되면 true를 반환합니다.
     */
    fun add(contact: ContactInfo): Boolean {
        if (contact.rawContactId in contacts) {
            return false
        }
        contacts[contact.rawContactId] = contact
        return true
    }

    /**
     * 연락처 제거 함수
     *
     * @return 연락처가 존재하지 않는 경우 false, 연락처가 존재하고 제거했다면 true를 반환
     */
    fun remove(contact: ContactInfo): Boolean {
        if (contact.rawContactId !in contacts) {
            return false
        }
        contacts.remove(contact.rawContactId)
        return true
    }

    /**
     * 연락처 정보를 가져오는 함수
     */
    fun getAll(): List<ContactInfo> {
        return contacts.values.toList()
    }

    /**
     * 연락처 정보 업데이트 함수
     */
    fun update(contact: ContactInfo): Boolean {
        if (contact.rawContactId !in contacts) {
            return false
        }
        contacts[contact.rawContactId] = contact
        return true
    }

    /**
     * 연락처 정보가 비어있는지 확인하는 함수
     */
    fun isEmpty(): Boolean {
        return contacts.isEmpty()
    }
}