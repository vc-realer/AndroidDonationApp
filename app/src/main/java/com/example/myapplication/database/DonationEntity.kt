package com.example.myapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.Donation

@Entity(tableName = "donations")
data class DonationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Int,
    val method: String
)

fun DonationEntity.toDonation(): Donation {
    return Donation(amount = this.amount, method = this.method)
}
