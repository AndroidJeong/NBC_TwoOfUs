package com.nbc.two_of_us.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AddContactDialogFragment : DialogFragment() {

    // XML을 이용한 커스텀 Dialog 생성시 이 함수가 아닌 onCreateView, onViewCreated 등을 사용해야 합니다.
    // https://developer.android.com/guide/fragments/dialogs
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = TODO()

}