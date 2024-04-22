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
     * 실제 연락처 정보를 가져오기 전에 개발을 위해 더미데이터를 생성하는 함수입니다.
     * 연락처 정보 권한이 개발 완료되면 이 함수를 사용하면 안 되며, 삭제해야 합니다.
     */
    fun createDummy() {
        val dummy = listOf(
            ContactInfo(
                1_5678_0001,
                "danielle",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_danielle"),
                "010-5678-0001",
                "danielle@newjeans.com",
                "",
            ),
            ContactInfo(
                1_5678_0002,
                "Hanni",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_hanni"),
                "010-5678-0002",
                "hanni@newjeans.com",
                "",
            ),
            ContactInfo(
                1_5678_0003,
                "Hyein",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_hyein"),
                "010-5678-0003",
                "hyein@newjeans.com",
                "",
            ),
            ContactInfo(
                1_5678_0004,
                "Minji",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_minji"),
                "010-5678-0004",
                "minji@newjeans.com",
                "",
            ),
            ContactInfo(
                1_1234_0001,
                "Jin",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_jin"),
                "010-1234-0001",
                "jin@bts.com",
                "",
            ),
            ContactInfo(
                1_1234_0002,
                "RM",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_rm"),
                "010-1234-0002",
                "rm@bts.com",
                "",
            ),
            ContactInfo(
                1_1234_0003,
                "Sugar",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_sugar"),
                "010-1234-0003",
                "sugar@bts.com",
                "",
            ),
            ContactInfo(
                1_1234_0004,
                "Sugar",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_sugar"),
                "010-1234-0004",
                "sugar@bts.com",
                "",
            ),
            ContactInfo(
                1_1234_0005,
                "V",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_v"),
                "010-1234-0005",
                "v@bts.com",
                "",
            ),
        )
        for (contact in dummy) {
            add(contact)
        }
    }

    fun createDummy() {
        val dummy = listOf(
            ContactInfo(
                1_5678_0001,
                "danielle",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_danielle"),
                "010-5678-0001",
                "danielle@newjeans.com",
                "",
                false
            ),
            ContactInfo(
                1_5678_0002,
                "Hanni",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_hanni"),
                "010-5678-0002",
                "hanni@newjeans.com",
                "",
                false
            ),
            ContactInfo(
                1_5678_0003,
                "Hyein",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_hyein"),
                "010-5678-0003",
                "hyein@newjeans.com",
                "",
                false
            ),
            ContactInfo(
                1_5678_0004,
                "Minji",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_minji"),
                "010-5678-0004",
                "minji@newjeans.com",
                "",
                false
            ),
            ContactInfo(
                1_1234_0001,
                "Jin",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_jin"),
                "010-1234-0001",
                "jin@bts.com",
                "",
                false
            ),
            ContactInfo(
                1_1234_0002,
                "RM",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_rm"),
                "010-1234-0002",
                "rm@bts.com",
                "",
                false
            ),
            ContactInfo(
                1_1234_0003,
                "Sugar",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_sugar"),
                "010-1234-0003",
                "sugar@bts.com",
                "",
                false
            ),
            ContactInfo(
                1_1234_0005,
                "V",
                Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_v"),
                "010-1234-0005",
                "v@bts.com",
                "",
                false
            ),
        )
        for (contact in dummy) {
            add(contact)
        }
    }



}