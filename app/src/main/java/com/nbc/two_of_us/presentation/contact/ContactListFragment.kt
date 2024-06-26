package com.nbc.two_of_us.presentation.contact

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbc.two_of_us.R
import com.nbc.two_of_us.data.ContactInfo
import com.nbc.two_of_us.data.ContactManager
import com.nbc.two_of_us.databinding.FragmentContactListBinding
import com.nbc.two_of_us.permission.ContactDatasource
import com.nbc.two_of_us.permission.PermissionManager
import com.nbc.two_of_us.presentation.ContactInfoViewModel
import com.nbc.two_of_us.presentation.contact_detail.ContactDetailFragment
import com.nbc.two_of_us.presentation.contact_detail.ContactDetailFragment.Companion.BUNDLE_KEY_FOR_CONTACT_INFO_CONTACT_DETAIL_FRAGMENT
import com.nbc.two_of_us.presentation.dialog.AddContactDialogFragment

class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null
    private val binding: FragmentContactListBinding
        get() = _binding!!

    private val adapter = ContactAdapter()

    private val viewModel: ContactInfoViewModel by activityViewModels()

    private val permissionManager by lazy {
        PermissionManager(this)
    }
    private val contactDatasource by lazy {
        ContactDatasource(requireContext())
    }

    private var isLinearLayoutManager = true

    private val contactsPermissionDialog by lazy {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.contacts_permission_dialog_title))
            .setMessage(getString(R.string.contacts_permission_dialog_message))
            .setNegativeButton(getString(R.string.contacts_permission_dialog_negative)) { _: DialogInterface, _: Int ->
            }
            .setPositiveButton(getString(R.string.contacts_permission_dialog_positive)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:" + requireContext().packageName)
                }
                startActivity(intent)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContactManager.isEmpty()) {
            addMyInfo()
            getContactsInfo()
        } else {
            adapter.add(ContactManager.getAll())
        }
    }

    /**
     * 사용자 정보 등록
     */
    private fun addMyInfo() {
        val userInfo = ContactInfo(
            name = "이정아",
            thumbnail = Uri.parse("android.resource://com.nbc.two_of_us/drawable/sample_hanni"),
            phone = "01000000000",
            email = "two@naver.com",
            memo = "",
            like = false,
        )
        ContactManager.add(userInfo)
        adapter.add(userInfo)
    }


    private fun getContactsInfo() {
        permissionManager.getPermission(
            Manifest.permission.READ_CONTACTS,
            onGranted = {
                changePermissionViewVisibility(View.GONE)
                contactDatasource.getAllContacts {
                    for (contactInfo in it) {
                        ContactManager.add(contactInfo)
                    }
                    adapter.add(it)
                }
            },
            onDenied = {
                changePermissionViewVisibility(View.VISIBLE)
                contactsPermissionDialog.show()
            }
        )
    }

    private fun changePermissionViewVisibility(visibility: Int) = with(binding) {
        ivEmptyContact.visibility = visibility
        btnRequestPermission.visibility = visibility
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setListener()
        setObserve()
    }

    private fun setRecyclerView() = with(binding) {
        fragmentListListRecyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(BUNDLE_KEY_FOR_IS_LINEAR_LAYOUT_MANAGER, isLinearLayoutManager)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) = with(binding) {
        super.onViewStateRestored(savedInstanceState)

        isLinearLayoutManager = savedInstanceState?.getBoolean(BUNDLE_KEY_FOR_IS_LINEAR_LAYOUT_MANAGER) ?: true

        fragmentListListRecyclerView.layoutManager = if (isLinearLayoutManager) {
            LinearLayoutManager(requireContext())
        } else {
            GridLayoutManager(requireContext(), NUM_FOR_GRID_LAYOUT_MANAGER_ITEM)
        }
    }

    private fun setListener() = with(binding) {
        //아이템 클릭 이벤트
        adapter.itemClick = object : ContactAdapter.ItemClick {
            override fun onClick(contactInfo: ContactInfo) {
                val bundle = Bundle().apply {
                    putParcelable(BUNDLE_KEY_FOR_CONTACT_INFO_CONTACT_DETAIL_FRAGMENT, contactInfo)
                }

                val fragmentDetail = ContactDetailFragment()
                fragmentDetail.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container_view, fragmentDetail)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
        }

        //FAB 클릭 이벤트
        fragmentListAddButtonFab.setOnClickListener {
            val fragmentAddDialog = AddContactDialogFragment()
            fragmentAddDialog.show(parentFragmentManager, "add_contact_dialog")
        }

        //헤더 메뉴 클릭 이벤트
        binding.fragmentListMenuImageView.setOnClickListener {
            val pop = android.widget.PopupMenu(requireContext(), fragmentListMenuImageView)
            pop.menuInflater.inflate(R.drawable.popup_menu, pop.menu)
            pop.show()

            pop.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.linear -> {
                        setLayoutType(LayoutType.LIST)
                        binding.apply {
                            fragmentListListRecyclerView.layoutManager =
                                LinearLayoutManager(requireContext())
                        }
                        isLinearLayoutManager = true
                        true
                    }

                    R.id.grid -> {
                        setLayoutType(LayoutType.GRID)
                        binding.apply {
                            fragmentListListRecyclerView.layoutManager =
                                GridLayoutManager(requireContext(), NUM_FOR_GRID_LAYOUT_MANAGER_ITEM)
                        }
                        isLinearLayoutManager = false
                        true
                    }

                    else -> false
                }
            }
        }

        //권한 승인 버튼 이벤트
        btnRequestPermission.setOnClickListener {
            getContactsInfo()
        }
    }

    private fun setObserve() {
        viewModel.contactLiveDataForEdit.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                adapter.update(it)
            }
        }
        viewModel.newContactInfo.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                adapter.add(it)
            }
        }

        /**
         * @author 이준영
         * */
        viewModel.contactLiveDataForDelete.observe(viewLifecycleOwner) { contactInfo ->
            contactInfo.peekContent().let {
                adapter.remove(it)
            }
        }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val BUNDLE_KEY_FOR_IS_LINEAR_LAYOUT_MANAGER = "BUNDLE_KEY_FOR_IS_LINEAR_LAYOUT_MANAGER"
        private const val NUM_FOR_GRID_LAYOUT_MANAGER_ITEM = 4
    }
}
