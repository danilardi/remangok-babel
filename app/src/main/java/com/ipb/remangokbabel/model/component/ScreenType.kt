package com.ipb.remangokbabel.model.component

sealed class ScreenType {
    object Empty : ScreenType()
    object Unauthorized : ScreenType()
    object Error : ScreenType()
}