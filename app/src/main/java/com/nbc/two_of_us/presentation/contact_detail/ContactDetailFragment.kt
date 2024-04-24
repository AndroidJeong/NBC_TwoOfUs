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

        //visible 설정, 번들데이터 받아오기
        /**
         * @author 경우님
         * */
        //visible 설정, 번들데이터 받아오기
        //val detailInfo: ContactInfo? = arguments?.getParcelable(BUNDLE_KEY_FOR_CONTACT_INFO)

        /**
         * @author 이준영
         * */
        detailInfo = arguments?.getParcelable(BUNDLE_KEY_FOR_CONTACT_INFO)


        if (detailInfo == null) {
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
            /**
             * Error occur
             *      : Nav Graph
             *
             * */
            Log.e("TAG", "onViewCreated: button clicked!")
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
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
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                type = "text/plain"
                data =  Uri.parse("mailto")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("example@naver.com"))
            }
            startActivity(intent)
        }

        val likeButton = binding.detailLikeLikebutton
        var likeCheck = detailInfo?.like

        likeButton.setOnClickListener {

            //하트 이미지 중복 때문에 코드 합치고 나서 drawable 보고 수정
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
//  this 타입 mismatch?

            /**
             * @author 이준영
             * AlertDialog.Builder의 input parameter는 context 입니다.
             * 현재 사용하고 계시는 this는 ContactDetailFragment이므로 오류가 생깁니다.
             * */
//                var builder = AlertDialog.Builder(this)
//                builder.setTitle("연락처 삭제하기")
//                builder.setMessage("정말로 삭제하시겠습니까?")
//                builder.setIcon(R.mipmap.ic_launcher)
//
//
//                val listener = object : DialogInterface.OnClickListener {
//                    override fun onClick(p0: DialogInterface?, p1: Int) {
//                        when (p1) {
//                            DialogInterface.BUTTON_POSITIVE ->
//                                //맞는지 확인
//                                detailInfo?.let { it1 -> ContactManager.remove(it1) }
//                            DialogInterface.BUTTON_NEGATIVE ->
//                                return
//                        }
//                    }
//                }
//
//                builder.setPositiveButton("삭제하기", listener)
//                builder.setNegativeButton("뒤로가기", listener)
//
//                builder.show()

            /**
             * @author 이준영
             * Fragment 상에서 Dialog를 사용하실 때는 DialogFragment 사용을 권장 하고 있습니다.
             * */
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

    /**
     * @author 이준영
     * Android developer site: https://developer.android.com/guide/fragments/dialogs?hl=ko
     * string을 그냥 사용하시는 것 보다 "res/strings.xml"에 구현하시고 라소스를 가져와서 쓰시도 좋을 것 같아용~
     * 메세지만 예시로 해놓을게요
     *
     * 마찬가지로 Edit을 위한 다이얼로그도 비슷하게 작업하시면 됩니다
     * */
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

뒤로 가기 버튼 -> 프레그먼트 종료시키기
번들로 구분 후 invisible 설정
이미지 이름 전화번호 이메일 메모 데이터 받아서 표시해주기

전화,메세지,이메일 버튼 -> 암시적 인텐트

좋아요 버튼 누르면 이미지 바꿔주기


  편집버튼 -> 프레그먼트 띄워주기, 프레그먼트에서 데이터 받아서 수정해주기 ->
  화면 종료시 데이터 넘겨주기



 */

/* 완료
*
* 삭제버튼 -> 다이얼로그 실행 후 컨텍트 매니저 삭제 함수 실행 ?
*
* */