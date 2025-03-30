package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DonationDao {
    @Insert
    suspend fun insert(donation: DonationEntity)

    @Query("SELECT * FROM donations")
    fun getAllDonations(): Flow<List<DonationEntity>> // Change suspend to Flow

    @Query("DELETE FROM donations")
    suspend fun deleteAll()
}
