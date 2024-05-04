package com.nbc.two_of_us.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactInfo(
    val name: String, // 이름
    val thumbnail: Uri, // 사진
    val phone: String, // 전화번호
    val email: String, // 이메일
    val memo: String, // 메모
    val like: Boolean, // 좋아요
    val rawContactId: Int = id, // 연락처 정보를 구분하는 고유 값
) : Parcelable {

    companion object {
        var id: Int = 0
            get() = field++
            private set
    }
}
