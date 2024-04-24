package com.nbc.two_of_us.presentation.contact_detail

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.nbc.two_of_us.R
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.databinding.FragmentContactDetailBinding
import com.nbc.two_of_us.presentation.contact.ContactListFragment
import com.nbc.two_of_us.presentation.dialog.AddContactDialogFragment
import java.security.acl.Owner

class ContactDetailFragment : Fragment() {

    private var _binding: FragmentContactDetailBinding? = null
    private val binding: FragmentContactDetailBinding
        get() = _binding!!

    private var detailInfo: ContactInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /**
         * @author 경우님
         * */
        //visible 설정, 번들데이터 받아오기
        //val detailInfo: ContactInfo? = arguments?.getParcelable(BUNDLE_KEY_FOR_CONTACT_INFO)

        /**
         * @author 이준영
         * */
        detailInfo = arguments?.getParcelable(BUNDLE_KEY_FOR_CONTACT_INFO)


        if (detailInfo?.rawContactId == 0) {
            binding.detailLikeLikebutton.visibility = View.GONE
            binding.detailBackBackbutton.visibility = View.GONE
            binding.detailDeleteButton.visibility = View.GONE
        }

        // 데이터 받아오기
        binding.detailImageView.setImageURI(detailInfo?.thumbnail)
        binding.detailNameTextview.text = detailInfo?.name
        binding.detailPhonenumTextview.text = detailInfo?.phone
        binding.detailEmailTextview.text = "이메일: ${detailInfo?.email}"
        binding.detailMemoTextview.text = "메모: ${detailInfo?.memo}"

        //프레그먼트 종료코드?
        binding.detailBackBackbutton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.detailCallImagebutton.setOnClickListener{
            val phoneNum = binding.detailPhonenumTextview.text.toString()
            val myUri = Uri.parse("tel:${phoneNum}")
            val dialIntent = Intent(Intent.ACTION_DIAL, myUri)
            startActivity(dialIntent)
        }
        binding.detailTextImagebutton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            val phoneNum = binding.detailPhonenumTextview.text.toString()
            intent.data = Uri.parse("smsto:${phoneNum} ")
            startActivity(intent)
        }
        binding.detailEmailImagebutton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            val email = binding.detailEmailTextview.text.toString()
            intent.data = Uri.parse("mailto:${email}")
            startActivity(intent)
        }

        val likeButton = binding.detailLikeLikebutton
        var likeCheck = detailInfo?.like

        likeButton.setOnClickListener {

            if (likeCheck == true) {
                likeCheck= false
                likeButton.setImageResource(R.drawable.ic_favorite_border)
            } else if(likeCheck == false) {
                likeCheck = true
                likeButton.setImageResource(R.drawable.ic_favorite)
            }

        }

        binding.detailEditButton.setOnClickListener {

            //AddContactDialogFragment
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        // 옵저버 패턴?
        //val detailInfo = ContactInfo(1,"", "","","","",false)
        //Owner.notifyUpdate(detailInfo)





    }

    companion object {
        // ContactListFragment에서 연락처 정보를 Bundle로 넘기기 위한 Key 값입니다.
        const val BUNDLE_KEY_FOR_CONTACT_INFO = "BUNDLE_KEY_FOR_CONTACT_INFO_ContactDetailFragment"

    }

    class DeleteConfirmationDialogFragment(
        private val target: ContactInfo
    ) : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.detail_fragment_delete_dialog_message_text)
                .setPositiveButton("삭제하기") { _,_ ->
                    ContactManager.remove(target)
                } // onPositive Lambda
                .setNegativeButton("뒤로가기") { _,_ -> } // onNegative Lambda
                .create()
    }
}


/*
번들로 구분 후 invisible 설정
-> detailInfo?.rawContactId == 0 변경시 수정 필요

  편집버튼 -> 프레그먼트 띄워주기, 프레그먼트에서 데이터 받아서 수정해주기 ->
  화면 종료시 데이터 넘겨주기
  삭제 -> 완료?
*/
