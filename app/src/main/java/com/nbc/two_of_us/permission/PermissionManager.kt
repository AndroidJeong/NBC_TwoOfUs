package com.nbc.two_of_us.permission

/**
 * 권한 요청을 처리하는 클래스 입니다.
 * 권한 요청을 위해 context 등이 필요한 경우 클래스를 수정해주세요.
 */
class PermissionManager private constructor() {




    companion object {
        private var INSTANCE : PermissionManager? = null
        fun getPermissionManager(): PermissionManager {
            return synchronized(PermissionManager::class) {
                val newInstance = INSTANCE ?: PermissionManager()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}