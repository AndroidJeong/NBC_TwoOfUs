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


class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null
    private val binding: FragmentContactListBinding
        get() = _binding!!

    private lateinit var data : List<ContactInfo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)

        //데이터 생성
        ContactManager.createDummy()
        data = ContactManager.getAll()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recyclerView 그리기
        val adapter = ContactAdapter(data)
        binding.apply {
            fragmentListListRecyclerView.adapter = adapter
            fragmentListListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        //아이템 클릭 이벤트
        adapter.itemClick = object : ContactAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Log.d("여기는 리스트프래드먼트", "${position} 번째 아이템이 클릭됨")
            }
        }

        //FAB 클릭 이벤트
        binding.fragmentListAddButtonFab.setOnClickListener {
            Log.d("여기는 리스트프래그먼트", "FAB 버튼 이벤트 처리")
        }
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}