package com.nbc.two_of_us.presentation.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.nbc.two_of_us.R
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.data.ContactManager.add
import com.nbc.two_of_us.data.ContactManager.getAll
import com.nbc.two_of_us.databinding.FragmentDialogBinding
import java.net.URI

class AddContactDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogBinding
    private var selectedImageUri: Uri? = null

    // XML을 이용한 커스텀 Dialog 생성시 이 함수가 아닌 onCreateView, onViewCreated 등을 사용해야 합니다.
    // https://developer.android.com/guide/fragments/dialogs
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? { binding = FragmentDialogBinding.inflate(inflater, container, false); return binding.root}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.imageView.setOnClickListener{
            Gallery()
        }

        binding.btnSave.setOnClickListener{
            Save()
        }
    }

    private fun Gallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    private fun Save(){
        val name = binding.editTextName.text.toString()
        val num = binding.editTextPhonenumber.text.toString()
        val address = binding.editTextEmail.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (name.isNullOrEmpty()){
            Toast.makeText(requireContext(),"이름을 입력해주세요", Toast.LENGTH_SHORT).show()
        } else if(num.isEmpty()){
            Toast.makeText(requireContext(), "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
        } else if (address.isNotEmpty() && !address.matches(emailPattern.toRegex())) {
            Toast.makeText(requireContext(), "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show()
        } else {
            selectedImageUri?.let { uri ->
                val newContact = ContactInfo(1, name, uri, num, address, "",false)
                val isAdded = add(newContact)

                if (isAdded) {
                    Toast.makeText(requireContext(), "연락처가 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "이미 존재하는 연락처입니다.", Toast.LENGTH_SHORT).show()
                }

                dismiss()
            }

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val selectedImage: Uri? = data?.data
                selectedImage?.let {
                    val imageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
                    binding.imageView.setImageBitmap(imageBitmap)

                }

            }
        }

    }

}