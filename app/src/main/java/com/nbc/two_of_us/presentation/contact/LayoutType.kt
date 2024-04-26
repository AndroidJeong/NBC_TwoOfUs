package com.nbc.two_of_us.presentation.contact

enum class LayoutType {
    LIST,
    GRID
}

var currentLayoutType: LayoutType = LayoutType.LIST

fun setLayoutType(layoutType: LayoutType) {
    currentLayoutType = layoutType
}
