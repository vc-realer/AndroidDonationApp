// DonationViewModel.kt
package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DonationViewModel : ViewModel() {
    private val _donations = mutableStateListOf<Donation>()
    val donations: List<Donation> get() = _donations

    private val _totalDonated = MutableStateFlow(0)
    val totalDonated: StateFlow<Int> = _totalDonated.asStateFlow()

    fun addDonation(amount: Int, method: String) {
        _donations.add(Donation(amount, method))
        _totalDonated.value += amount
    }
}

data class Donation(
    val amount: Int,
    val method: String
)