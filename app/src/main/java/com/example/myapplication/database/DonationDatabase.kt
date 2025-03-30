package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DonationEntity::class], version = 1, exportSchema = false)
abstract class DonationDatabase : RoomDatabase() {
    abstract fun donationDao(): DonationDao

    companion object {
        @Volatile
        private var INSTANCE: DonationDatabase? = null

        fun getDatabase(context: Context): DonationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DonationDatabase::class.java,
                    "donation_database" // Added database name
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
