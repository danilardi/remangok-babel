package com.ipb.remangokbabel.di

import androidx.compose.runtime.Composable
import com.ipb.remangokbabel.data.repository.Repository

object Injection {
    @Composable
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}