package com.nbc.two_of_us.presentation.dialog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.data.ContactManager.add
import com.nbc.two_of_us.data.ContactManager.update
import com.nbc.two_of_us.databinding.FragmentDialogBinding
import com.nbc.two_of_us.presentation.ContactInfoViewModel

class AddContactDialogFragment(
    private val targetContact: ContactInfo? = null
) : DialogFragment() {
    private lateinit var binding: FragmentDialogBinding
    private var selectedImageUri: Uri? = null
    private val viewModel: ContactInfoViewModel by activityViewModels()


    // XML을 이용한 커스텀 Dialog 생성시 이 함수가 아닌 onCreateView, onViewCreated 등을 사용해야 합니다.
    // https://developer.android.com/guide/fragments/dialogs
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false); return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.imageView.setOnClickListener {
            openGallery()
        }
        binding.btnSave.setOnClickListener {
            if(targetContact == null) save() else saveForEdit()
        }
        targetOperation()
    }

    /**
     * @author 이준영
     *constructor input이 null이 아니면 default 값을 채운다.
     **/
    private fun targetOperation() {
        if(targetContact == null) return
        with(binding) {
            imageView.setImageURI(targetContact.thumbnail)
            editTextName.setText(targetContact.name)
            editTextPhonenumber.setText(targetContact.phone)
            editTextEmail.setText(targetContact.email)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }


    /**
     * @author 이준영
     * edit mode를 위한 save 함수 입니다.
     * */
    private fun saveForEdit() {

        val name = binding.editTextName.text.toString()
        val num = binding.editTextPhonenumber.text.toString()
        var address = binding.editTextEmail.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (name.isNotEmpty() && num.isNotEmpty()) {
            val editedContact = if (selectedImageUri != null) {
                selectedImageUri?.let { uri ->
                    targetContact?.copy(
                        name = name,
                        thumbnail = uri,
                        phone = num,
                        email = address
                    )
                }
            } else {
                if (address.isNotEmpty() && !address.matches(emailPattern.toRegex())) {
                    Toast.makeText(requireContext(), "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show()
                    null
                } else {
                    targetContact?.copy(
                        name = name,
                        thumbnail = Uri.EMPTY,
                        phone = num,
                        email = address
                    )
                }
            }

            editedContact?.let { contact ->
                val isUpdated = update(contact)
                if (isUpdated) {
                    viewModel.setContactForEdit(contact.copy())
                    // viewModel.updateList(ContactManager.getAll())
                    Toast.makeText(requireContext(), "연락처가 저장되었습니다", Toast.LENGTH_SHORT).show()
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "존재하지 않는 연락처입니다, 다시 한번 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "이름과 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun save() {

        val name = binding.editTextName.text.toString()
        val num = binding.editTextPhonenumber.text.toString()
        var address = binding.editTextEmail.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (name.isNotEmpty() && num.isNotEmpty()) {
            val newContact = if (selectedImageUri != null) {
                selectedImageUri?.let { uri ->
                    ContactInfo(ContactManager.getAll().size + 1, name, uri, num, address, "", false)
                }
            } else {
                if (address.isNotEmpty() && !address.matches(emailPattern.toRegex())) {
                    Toast.makeText(requireContext(), "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show()
                    null
                } else {
                    ContactInfo(ContactManager.getAll().size + 1, name, Uri.EMPTY, num, address, "", false)
                }
            }

            newContact?.let { contact ->
                val isAdded = add(contact)
                if (isAdded) {
                    viewModel.updateList(ContactManager.getAll())

                    Toast.makeText(requireContext(), "연락처가 저장되었습니다", Toast.LENGTH_SHORT).show()
                    val contactInfo = Bundle().apply {
                        putParcelable("contactInfo", contact)
                    }
                    parentFragmentManager.setFragmentResult("Contact", contactInfo)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "이미 있는 연락처입니다, 다시 한번 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "이름과 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val selectedImage: Uri? = data?.data
                selectedImage?.let {
                    val imageBitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        selectedImage
                    )
                    binding.imageView.setImageBitmap(imageBitmap)

                }
            }
        }
    }
}