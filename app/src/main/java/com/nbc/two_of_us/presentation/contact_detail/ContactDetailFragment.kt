package com.nbc.two_of_us.presentation.contact_detail

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.nbc.two_of_us.R
import com.nbc.two_of_us.data.ContactInfo
import androidx.fragment.app.activityViewModels
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.databinding.FragmentContactDetailBinding
import com.nbc.two_of_us.presentation.dialog.AddContactDialogFragment
import com.nbc.two_of_us.presentation.ContactInfoViewModel
import com.nbc.two_of_us.util.DEFAULT_THUMBNAIL_URI
import com.nbc.two_of_us.util.MY_RAW_CONTACT_ID

class ContactDetailFragment : Fragment() {

    private var _binding: FragmentContactDetailBinding? = null
    private val binding: FragmentContactDetailBinding
        get() = _binding!!

    private val viewmodel : ContactInfoViewModel by activityViewModels()
    private var detailInfo: ContactInfo? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        detailInfo = arguments?.getParcelable(BUNDLE_KEY_FOR_CONTACT_INFO)

        // 하단에 MY PAGE를 눌러서 진입했는지 확인
        detailInfo?.let {
            updateUI(it)
        } ?: run {
            detailLikeLikebutton.visibility = View.GONE
            detailBackBackbutton.visibility = View.GONE
            detailDeleteButton.visibility = View.GONE

            ContactManager.getUserInfo()?.let {
                detailInfo = it
                val uri = if (it.thumbnail == Uri.EMPTY) {
                    Uri.parse(DEFAULT_THUMBNAIL_URI)
                } else {
                    it.thumbnail
                }
                detailImageView.setImageURI(uri)
                detailNameTextview.text = it.name
                detailPhonenumTextview.text = it.phone
                detailEmailTextview.text = "이메일: ${it.email}"
            }
        }

        //프레그먼트 종료코드?
        detailBackBackbutton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        detailCallImagebutton.setOnClickListener{
            val phoneNum = detailPhonenumTextview.text.toString()
            val myUri = Uri.parse("tel:${phoneNum}")
            val dialIntent = Intent(Intent.ACTION_DIAL, myUri)
            startActivity(dialIntent)
        }
        detailTextImagebutton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            val phoneNum = detailPhonenumTextview.text.toString()
            intent.data = Uri.parse("smsto:${phoneNum} ")
            startActivity(intent)
        }
        detailEmailImagebutton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            val email = detailEmailTextview.text.toString()
            intent.data = Uri.parse("mailto:${email}")
            startActivity(intent)
        }

        detailLikeLikebutton.setOnClickListener {
            detailInfo?.let { detailInfoNonNull ->
                val edited = detailInfoNonNull.copy(like = !detailInfoNonNull.like)
                detailInfo = edited
                viewmodel.setContactForEdit(edited)
                detailLikeLikebutton.setImageResource(getLikeButtonImageRes(edited.like))
            }
        }

        viewmodel.contactLiveDataForEdit.observe(viewLifecycleOwner) {
//            with(it.peekContent()) {
//                detailNameTextview.text = name
//                detailPhonenumTextview.text = phone
//                detailImageView.setImageURI(thumbnail)
//            }
            it.getContentIfNotHandled()?.let { contactInfo ->
                detailInfo = contactInfo

                val uri = if (contactInfo.thumbnail == Uri.EMPTY) {
                    Uri.parse(DEFAULT_THUMBNAIL_URI)
                } else {
                    contactInfo.thumbnail
                }
                detailNameTextview.text = contactInfo.name
                detailEmailTextview.text = contactInfo.email
                detailPhonenumTextview.text = contactInfo.phone
                detailLikeLikebutton.setImageResource(getLikeButtonImageRes(contactInfo.like))
                detailImageView.setImageURI(uri)
            }
        }

        binding.detailEditButton.setOnClickListener {
            val fragmentAddDialog = AddContactDialogFragment(detailInfo)
            fragmentAddDialog.show(parentFragmentManager, "add_contact_dialog")
            //AddContactDialogFragment 열고 데이터 받아오기(수정)
        }

        binding.detailDeleteButton.setOnClickListener{
            detailInfo?.let {
                DeleteConfirmationDialogFragment(it).show(
                    childFragmentManager,
                    "DeleteConfirmationDialogFragment"
                )
            }
        }
    }

    private fun updateUI(contactInfo: ContactInfo) = with(binding) {
        val uri = if (contactInfo.thumbnail == Uri.EMPTY) {
            Uri.parse(DEFAULT_THUMBNAIL_URI)
        } else {
            contactInfo.thumbnail
        }
        detailImageView.setImageURI(uri)
        detailNameTextview.text = contactInfo.name
        detailPhonenumTextview.text = contactInfo.phone
        detailEmailTextview.text = "이메일: ${contactInfo.email}"
        detailLikeLikebutton.setImageResource(getLikeButtonImageRes(contactInfo.like))

        if (contactInfo.rawContactId == MY_RAW_CONTACT_ID) {
            detailDeleteButton.visibility = View.GONE
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val restoredInfo: ContactInfo? = savedInstanceState?.getParcelable(BUNDLE_KEY_FOR_RECOVERING_AFTER_ROTATION)
        // 화면 회전에 의해서 이전 데이터가 복원 처리된 경우에는 UI를 화면 회전 데이터로 변경함
        if (restoredInfo != null) {
            updateUI(restoredInfo)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(BUNDLE_KEY_FOR_RECOVERING_AFTER_ROTATION, detailInfo)
    }

    private fun getLikeButtonImageRes(like: Boolean): Int {
        return if(like) R.drawable.ic_favorite else R.drawable.ic_favorite_border
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        // ContactListFragment로 데이터 넘겨주기?

    }

    companion object {
        // ContactListFragment에서 연락처 정보를 Bundle로 넘기기 위한 Key 값입니다.
        const val BUNDLE_KEY_FOR_CONTACT_INFO = "BUNDLE_KEY_FOR_CONTACT_INFO_ContactDetailFragment"
        const val BUNDLE_KEY_FOR_RECOVERING_AFTER_ROTATION = "BUNDLE_KEY_FOR_RECOVERING_AFTER_ROTATION_BUNDLE_KEY_FOR_CONTACT_INFO_ContactDetailFragment"
    }

    class DeleteConfirmationDialogFragment(
        private val target: ContactInfo
    ) : DialogFragment() {
        private val viewmodel : ContactInfoViewModel by activityViewModels()
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.detail_fragment_delete_dialog_message_text)
                .setPositiveButton("삭제하기") { _,_ ->
                    ContactManager.remove(target)
                    viewmodel.setDeletedContact(target)
                    requireActivity().supportFragmentManager.popBackStack()
                } // onPositive Lambda
                .setNegativeButton("뒤로가기") { _,_ -> } // onNegative Lambda
                .create()
    }
}