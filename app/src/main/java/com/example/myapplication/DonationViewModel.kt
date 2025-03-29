// DonationViewModel.kt
package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class DonationViewModel : ViewModel() {
    private val _donations = MutableStateFlow<List<Donation>>(emptyList())
    val donations: StateFlow<List<Donation>> = _donations.asStateFlow()

    private val _totalDonated = MutableStateFlow(0)
    val totalDonated: StateFlow<Int> = _totalDonated.asStateFlow()

    private val _target = 10000
    val target: Int get() = _target

    fun addDonation(amount: Int, method: String) {

        _donations.update { currentList ->
            currentList + Donation(amount, method)
        }
        _totalDonated.value += amount

    }
}

data class Donation(
    val amount: Int,
    val method: String
)