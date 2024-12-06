package com.ipb.remangokbabel.model.component

sealed class ButtonType {
    object Primary : ButtonType()
    object Disabled : ButtonType()
    object Outline : ButtonType()
    object Success : ButtonType()
    object Danger : ButtonType()
    object Warning : ButtonType()
}
