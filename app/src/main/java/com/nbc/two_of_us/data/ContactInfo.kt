package com.nbc.two_of_us.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**

연락처 정보를 나타내는 클래스 입니다.
[이 줄과 아래 내용은 구현 후에 삭제해주세요.]
해당 데이터를 수정해야 하는 경우, var로 변경해서 수정하지 마시고, data class의 copy를 이용해주세요.
 */
@Parcelize
data class ContactInfo(
    val rawContactId: Int, // 연락처 정보를 구분하는 고유 값
    val name: String, // 이름
    val thumbnail: Uri, // 사진
    val phone: String, // 전화번호
    val email: String, // 이메일
    val memo: String, // 메모
    val like: Boolean, // 좋아요
): Parcelable