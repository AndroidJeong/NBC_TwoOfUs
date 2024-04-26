package com.nbc.two_of_us.permission

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

/**
 * 권한 요청을 처리하는 클래스 입니다.
 * 권한 요청을 위해 context 등이 필요한 경우 클래스를 수정해주세요.
 */
class PermissionManager(
    private val fragment: Fragment,
){
    init { registerForPermissionResult() }

    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null
    private var onGranted: () -> Unit = {}
    private var onDenied: () -> Unit = {}

    /**
     * Permission 처리 결과에 대한 동작을 하는 구현부입니다.
     * Register는 Activity Lifecycle Stated 이전에 실행되어야 합니다.
     * @param permission 권한을 입력해 주시면 됩니다. ex) Manifest.permission.READ_CONTACTS
     * @param onGranted 사용자가 권한 허가를 클릭하면 호출됩니다.
     * @param onDenied 사용자가 권한 거부를 클릭하면 호출됩니다.
     * */
    fun getPermission(
        permission: String,
        onGranted: () -> Unit,
        onDenied: () -> Unit,
    ) {
        this.onGranted = onGranted
        this.onDenied = onDenied
        requestPermissionLauncher?.launch(permission)
    }

    private fun registerForPermissionResult() {
        requestPermissionLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) onGranted()
            else onDenied()
        }
    }
}

