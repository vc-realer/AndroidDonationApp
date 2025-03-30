package com.example.myapplication

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DonationViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
