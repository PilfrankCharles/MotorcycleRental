package com.example.motorcyclerental

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object BookingManager {

    private val bookingRecords: SnapshotStateList<BookingRecord> = mutableStateListOf()

    fun addBooking(bikeName: String, rateType: String, totalCost: String) {
        val timestamp = System.currentTimeMillis()
        val newBooking = BookingRecord(bikeName, rateType, totalCost, timestamp)
        bookingRecords.add(newBooking)
    }

    fun getAllBookings(): List<BookingRecord> = bookingRecords

    fun clearAllBookings() {
        bookingRecords.clear()

    }
}

