package com.nbc.two_of_us.permission

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

/**
 * 권한 요청을 처리하는 클래스 입니다.
 * 권한 요청을 위해 context 등이 필요한 경우 클래스를 수정해주세요.
 */
class PermissionManager private constructor(
    private val activity: AppCompatActivity
) {

    private var requestPermissionLauncher:  ActivityResultLauncher<String>? = null

    /**
     * 권한을 신청하는 함수입니다.
     * @param permission 권한을 입력해 주시면 됩니다. ex) Manifest.permission.READ_CONTACTS
     * */
    fun getPermission(permission: String) = requestPermissionLauncher?.launch(permission)

    /**
     * Permission 처리 결과에 대한 동작을 하는 구현부입니다.
     * Register는 Activity Lifecycle Stated 이전에 실행되어야 합니다.
     * @param onGranted 사용자가 권한 허가를 클릭하면 호출됩니다.
     * @param onDenied 사용자가 권한 거부를 클릭하면 호출됩니다.
     * */
    fun registerForPermissionResult(
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        requestPermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if(isGranted) onGranted()
            else onDenied()
        }
    }

    companion object {
        private var INSTANCE : PermissionManager? = null
        fun getInstance(activity: AppCompatActivity): PermissionManager {
            return synchronized(PermissionManager::class) {
                val newInstance = INSTANCE ?: PermissionManager(activity)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}

