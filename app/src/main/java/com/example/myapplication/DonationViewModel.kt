package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.DonationDatabase
import com.example.myapplication.database.DonationEntity
import com.example.myapplication.database.toDonation
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DonationViewModel(application: Application) : AndroidViewModel(application) {

    private val donationDao = DonationDatabase.getDatabase(application).donationDao()

    // Live updates for donations
    val donations: StateFlow<List<Donation>> = donationDao.getAllDonations()
        .map { entities -> entities.map { it.toDonation() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Live total calculation
    val totalDonated: StateFlow<Int> = donationDao.getAllDonations()
        .map { donations -> donations.sumOf { it.amount } }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    private val _target = 10000
    val target: Int get() = _target

    fun addDonation(amount: Int, method: String) {
        viewModelScope.launch {
            donationDao.insert(DonationEntity(amount = amount, method = method))
        }
    }

    fun resetDonations() {
        viewModelScope.launch {
            donationDao.deleteAll()
        }
    }
}
