package com.nbc.two_of_us.permission

import android.content.Context
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.core.net.toUri
import com.nbc.two_of_us.data.ContactInfo
import kotlin.concurrent.thread

class ContactDatasource(context: Context) {

    val contactInfoList = mutableListOf<ContactInfo>()

    init { getAllContacts() }

    private val phoneProjection = arrayOf(
        Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,
        Phone.STARRED, Phone.PHOTO_URI
    )
    private val emailProjection = arrayOf(Email.CONTACT_ID, Email.ADDRESS)
    private val noteProjection = arrayOf(Note.CONTACT_ID, Note.NOTE)

    private val resolver = context.contentResolver

    /**
     * 수정 중 ~
     * */
    fun addContact(contactInfo: ContactInfo) {
        contactInfoList.add(contactInfo)
    }

    private fun getAllContacts() {

        thread(start = true) {

            val phoneCursor = resolver.query(
                Phone.CONTENT_URI,
                phoneProjection,
                null,
                null,
                null
            )

            phoneCursor?.let { phoneCursorNonNull ->
                with(phoneCursorNonNull) {
                    while (moveToNext()) {

                        var id = ""
                        var name = ""
                        var number = ""
                        var starred = ""
                        var photoUri = ""
                        var address = ""
                        var note = ""

                        val idIndex = getColumnIndex(phoneProjection[0])
                        val nameIndex = getColumnIndex(phoneProjection[1])
                        val numberIndex = getColumnIndex(phoneProjection[2])
                        val starredIndex = getColumnIndex(phoneProjection[3])
                        val photoUriIndex = getColumnIndex(phoneProjection[4])

                        id = getString(idIndex)
                        name = getString(nameIndex) ?: ""
                        number = getString(numberIndex) ?: ""
                        starred = getString(starredIndex)
                        photoUri = getString(photoUriIndex) ?: ""

                        val emailCursor = resolver.query(
                            Email.CONTENT_URI,
                            null,
                            Email.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )

                        emailCursor?.let { emailCursorNonNull ->
                            while (emailCursorNonNull.moveToNext()) {
                                val eIdIndex = emailCursorNonNull.getColumnIndex(emailProjection[0])
                                val eAddressIndex =
                                    emailCursorNonNull.getColumnIndex(emailProjection[1])
                                val eId = emailCursorNonNull.getString(eIdIndex)
                                address = emailCursorNonNull.getString(eAddressIndex) ?: ""
                            }
                        }

                        val noteCursor = resolver.query(
                            ContactsContract.Data.CONTENT_URI,
                            null,
                            Note.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )

                        noteCursor?.let { noteCursorNonNull ->
                            while (noteCursorNonNull.moveToNext()) {
                                val nIdIndex = noteCursorNonNull.getColumnIndex(noteProjection[0])
                                val nNoteIndex = noteCursorNonNull.getColumnIndex(noteProjection[1])
                                val nId = noteCursorNonNull.getString(nIdIndex)
                                note = noteCursorNonNull.getString(nNoteIndex)?:""
                            }
                        }

                        contactInfoList.add(
                            ContactInfo(
                                rawContactId = id.toInt(),
                                name = name,
                                thumbnail = photoUri.toUri(),
                                phone = number,
                                email = address,
                                memo = note,
                                like = starred == "1"
                            )
                        )
                    }
                }
            }
        }
    }
}