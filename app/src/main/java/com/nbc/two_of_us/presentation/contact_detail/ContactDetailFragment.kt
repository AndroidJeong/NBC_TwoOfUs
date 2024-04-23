package com.nbc.two_of_us.presentation.contact_detail

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.nbc.two_of_us.R
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.databinding.FragmentContactDetailBinding
import com.nbc.two_of_us.presentation.contact.ContactListFragment

class ContactDetailFragment : Fragment() {

    private var _binding: FragmentContactDetailBinding? = null
    private val binding: FragmentContactDetailBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //visible 설정, 번들데이터 확인, null체크
        //if()

        // 데이터 받아오기
        binding.detailImageView.setImageResource(R.drawable.sample_jin)
        binding.detailNameTextview.text = "이름"
        binding.detailPhonenumTextview.text = "01011112222"
        binding.detailEmailTextview.text = "이메일"
        binding.detailMemoTextview.text = "메모"







        binding.detailBackBackbutton.setOnClickListener {
            //프레그먼트 종료코드? 콘테이너뷰
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.de


        val likeButton = binding.detailLikeLikebutton

        likeButton.setOnClickListener {
            //사전에 데이터 받은거 미리 변수처리 해주기
            var a = false

            if (a) {
                a = false
                //하트 이미지 중복 때문에 코드 합치고 나서 drawable 보고 수정
                //likeButton.setImageResource()
            } else {
                a = true

                //likeButton.setImageResource()
            }


        }


        binding.detailDeleteButton.setOnClickListener{

                var builder = AlertDialog.Builder(this)
                builder.setTitle("연락처 삭제하기")
                builder.setMessage("정말로 삭제하시겠습니까?")
                builder.setIcon(R.mipmap.ic_launcher)


                val listener = object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        when (p1) {
                            DialogInterface.BUTTON_POSITIVE ->
                                //번들로 받은 contactInfo 객체 만들고 넣어주기?
                                //ContactManager.remove()
                                return
                            DialogInterface.BUTTON_NEGATIVE ->
                                return
                        }
                    }
                }

                builder.setPositiveButton("삭제하기", listener)
                builder.setNegativeButton("뒤로가기", listener)

                builder.show()

        }


    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        // 옵저버 패턴?
        //val detailInfo = ContactInfo(1,"", "","","","",false)





    }

    companion object {
        // ContactListFragment에서 연락처 정보를 Bundle로 넘기기 위한 Key 값입니다.
        const val BUNDLE_KEY_FOR_CONTACT_INFO = "BUNDLE_KEY_FOR_CONTACT_INFO_ContactDetailFragment"
    }
}


/*
뒤로 가기 버튼 -> 프레그먼트 종료시키기
번들로 구분 후 invisible 설정
이미지 이름 전화번호 이메일 메모 데이터 받아서 표시해주기
좋아요 버튼 누르면 이미지 바꿔주기
전화버튼 -> 전화걸기(액티비티 시작처럼)
메세지,이메일 버튼 -> 암시적 인텐트

편집버튼 -> 프레그먼트 띄워주기, 프레그먼트에서 데이터 받아서 수정해주기 ->

삭제버튼 -> 다이얼로그 실행 후 컨텍트 매니저 삭제 함수 실행

화면 종료시 데이터 넘겨주기

 */
