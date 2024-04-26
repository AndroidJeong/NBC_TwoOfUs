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
import com.nbc.two_of_us.util.DEFAULT_THUMBNAIL_URI
import com.nbc.two_of_us.util.MY_RAW_CONTACT_ID

class AddContactDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogBinding
    private var targetContact: ContactInfo? = null
    private var selectedImageUri: Uri? = null
    private val viewModel: ContactInfoViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        targetContact = arguments?.getParcelable(BUNDLE_KEY_FOR_CONTACT_INFO_ADD_CONTACT_DIALOG_FRAGMENT, ContactInfo::class.java)
        selectedImageUri = targetContact?.thumbnail
    }

    // XML을 이용한 커스텀 Dialog 생성시 이 함수가 아닌 onCreateView, onViewCreated 등을 사용해야 합니다.
    // https://developer.android.com/guide/fragments/dialogs
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        targetOperation()
    }

    private fun setListener() = with(binding) {
        btnCancel.setOnClickListener {
            dismiss()
        }
        imageView.setOnClickListener {
            openGallery()
        }
        btnSave.setOnClickListener {
            if (targetContact == null) save() else saveForEdit()
        }
    }

    /**
     * @author 이준영
     *constructor input이 null이 아니면 default 값을 채운다.
     **/
    private fun targetOperation() = with(binding) {
        targetContact?.let {
            val uri = if (it.thumbnail == Uri.EMPTY) {
                Uri.parse(DEFAULT_THUMBNAIL_URI)
            } else {
                it.thumbnail
            }
            imageView.setImageURI(uri)
            editTextName.setText(it.name)
            editTextPhonenumber.setText(it.phone)
            editTextEmail.setText(it.email)
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
        val address = binding.editTextEmail.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (address.isNotEmpty() && !address.matches(emailPattern.toRegex())) {
            Toast.makeText(requireContext(), "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show()
            return
        } else if (name.isEmpty() || num.isEmpty()) {
            Toast.makeText(requireContext(), "이름 또는 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val editedContact = targetContact?.copy(
            name = name,
            thumbnail = selectedImageUri ?: Uri.EMPTY,
            phone = num,
            email = address,
        ) ?: ContactInfo(
            name = name,
            thumbnail = selectedImageUri ?: Uri.EMPTY,
            phone = num,
            email = address,
            memo = "",
            like = false,
        )

        if (update(contact = editedContact)) {
            val maxCountOfObserving = if (editedContact.rawContactId == MY_RAW_CONTACT_ID) {
                3
            } else {
                2
            }
            viewModel.setContactForEdit(editedContact, maxCountOfObserving)
            Toast.makeText(requireContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show()
            dismiss()
        } else {
            Toast.makeText(requireContext(), "존재하지 않는 연락처입니다, 다시 한번 시도해주세요", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun save() {

        val name = binding.editTextName.text.toString()
        val num = binding.editTextPhonenumber.text.toString()
        val address = binding.editTextEmail.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (name.isEmpty() || num.isEmpty()) {
            Toast.makeText(requireContext(), "이름과 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        } else if (address.isEmpty() || !address.matches(emailPattern.toRegex())) {
            Toast.makeText(requireContext(), "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show()
            return
        } else if (ContactManager.checkExist(num)) {
            Toast.makeText(requireContext(), "이미 존재하는 연락처 번호입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val newContact = ContactInfo(
            name = name,
            thumbnail = selectedImageUri ?: Uri.EMPTY,
            phone = num,
            email = address,
            memo = "",
            like = false,
        )
        if (add(newContact)) {
            Toast.makeText(requireContext(), "연락처가 저장되었습니다", Toast.LENGTH_SHORT).show()
            viewModel.setNewContactInfo(newContact)
            dismiss()
        } else {
            Toast.makeText(
                requireContext(),
                "이미 있는 연락처입니다, 다시 한번 시도해주세요",
                Toast.LENGTH_SHORT
            ).show()
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
                    selectedImageUri = it
                }
            }
        }
    }

    companion object {
        const val BUNDLE_KEY_FOR_CONTACT_INFO_ADD_CONTACT_DIALOG_FRAGMENT = "BUNDLE_KEY_FOR_CONTACT_INFO_ADD_CONTACT_DIALOG_FRAGMENT"
    }
}