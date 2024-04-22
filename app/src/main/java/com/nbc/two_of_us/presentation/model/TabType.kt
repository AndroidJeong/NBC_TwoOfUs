package com.nbc.two_of_us.presentation.model

import androidx.annotation.StringRes
import com.nbc.two_of_us.R

enum class TabType(val position: Int, @StringRes val tabName: Int){
    CONTACT(0, R.string.contact), MY_PAGE(1, R.string.my_page);

    companion object {
        fun from(position: Int) = entries.first { it.position == position }
    }
}
