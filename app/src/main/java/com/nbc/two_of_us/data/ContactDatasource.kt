package com.nbc.two_of_us.data

import android.content.Context
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Note
import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactDatasource(context: Context) {

    private val contactInfoList = mutableListOf<ContactInfo>()

    private val phoneProjection = arrayOf(
        Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,
        Phone.STARRED, Phone.PHOTO_URI
    )
    private val emailProjection = arrayOf(Email.CONTACT_ID, Email.ADDRESS)
    private val noteProjection = arrayOf(Note.CONTACT_ID, Note.NOTE)

    private val resolver = context.contentResolver

    fun getAllContacts(callback: (List<ContactInfo>) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            val phoneCursor = resolver.query(
                Phone.CONTENT_URI,
                phoneProjection,
                null,
                null,
                null
            )

            phoneCursor?.use { phoneCursorNonNull ->
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
                                note = noteCursorNonNull.getString(nNoteIndex) ?: ""
                            }
                        }

                        contactInfoList.add(
                            ContactInfo(
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
            withContext(Dispatchers.Main) {
                callback(contactInfoList)
            }
        }
    }
}