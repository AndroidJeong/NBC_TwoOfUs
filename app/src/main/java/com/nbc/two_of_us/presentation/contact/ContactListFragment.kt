package com.nbc.two_of_us.presentation.contact

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbc.two_of_us.R
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.databinding.FragmentContactListBinding
import com.nbc.two_of_us.presentation.contact_detail.ContactDetailFragment
import com.nbc.two_of_us.presentation.contact_detail.ContactDetailFragment.Companion.BUNDLE_KEY_FOR_CONTACT_INFO
import com.nbc.two_of_us.presentation.dialog.AddContactDialogFragment


class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null
    private val binding: FragmentContactListBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)

        //데이터 생성
        ContactManager.createDummy()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recyclerView 그리기
        val adapter = ContactAdapter(ContactManager.getAll())
        binding.apply {
            fragmentListListRecyclerView.adapter = adapter
            fragmentListListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        //아이템 클릭 이벤트
        adapter.itemClick = object : ContactAdapter.ItemClick {
            override fun onClick(contactInfo : ContactInfo) {
                //데이터를 번들로 넘겨줌
                val bundle = Bundle().apply {
                    putParcelable(BUNDLE_KEY_FOR_CONTACT_INFO, contactInfo)
                }

                val fragmentDetail = ContactDetailFragment()
                fragmentDetail.arguments = bundle
                Log.d("여기는 리스트프래그먼트", "${bundle}")

//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment, fragmentDetail)
//                    .addToBackStack(null)
//                    .commit()
            }
        }

        //FAB 클릭 이벤트
        binding.fragmentListAddButtonFab.setOnClickListener {
            Log.d("여기는 리스트프래그먼트", "FAB 버튼 이벤트 처리")

            val fragmentAddDialog = AddContactDialogFragment()
//            fragmentAddDialog.show(parentFragmentManager, "add_contact_dialog")
        }

    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}