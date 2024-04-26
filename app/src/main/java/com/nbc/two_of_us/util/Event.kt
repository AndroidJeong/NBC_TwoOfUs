package com.nbc.two_of_us.util

open class Event<out T>(private val content: T, private var count: Int = 1) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        // LiveData를 두 곳에서 동시에 접근하는 경우에 count 처리를 정확하게 하기 위해 synchronized 막음
        return synchronized(this) {
            if (hasBeenHandled) {
                null
            } else {
                if (--count <= 0) {
                    hasBeenHandled = true
                }
                content
            }
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
