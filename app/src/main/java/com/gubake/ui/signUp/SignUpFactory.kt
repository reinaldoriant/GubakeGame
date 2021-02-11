package com.gubake.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gubake.data.remote.ApiService

@Suppress("UNCHECKED_CAST")
class SignUpFactory(private val service: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel(service) as T
    }
}