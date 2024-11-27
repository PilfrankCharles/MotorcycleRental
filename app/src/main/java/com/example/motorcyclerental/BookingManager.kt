package com.example.motorcyclerental

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.*

object BookingManager {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Local state list for holding booking records (this will still be used for UI updates)
    private val bookingRecords: SnapshotStateList<BookingRecord> = mutableStateListOf()

    // Add booking to both Firestore and local state
    fun addBooking(bikeName: String, rateType: String, totalCost: String) {
        val timestamp = System.currentTimeMillis()
        val newBooking = BookingRecord(bikeName, rateType, totalCost, timestamp)

        // Save to Firestore
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val bookingRef = db.collection("users").document(userId).collection("bookings")
            bookingRef.add(newBooking)
        }

        // Add to local state list (for UI updates)
        bookingRecords.add(newBooking)
    }

    // Get all bookings from Firestore and update local state list
    fun getAllBookings(): List<BookingRecord> {
        val bookings = mutableListOf<BookingRecord>()
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId).collection("bookings")
                .get()
                .addOnSuccessListener { result ->
                    bookings.clear()  // Clear any existing bookings in the list
                    for (document in result.documents) {
                        val booking = document.toObject(BookingRecord::class.java)
                        if (booking != null) {
                            bookings.add(booking)
                        }
                    }
                    bookingRecords.clear()
                    bookingRecords.addAll(bookings)  // Update local state list
                }
        }
        return bookings
    }

    // Clear all bookings from Firestore and local state list
    fun clearAllBookings() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val bookingRef = db.collection("users").document(userId).collection("bookings")
            bookingRef.get().addOnSuccessListener { result ->
                for (document in result.documents) {
                    document.reference.delete()
                }
            }
        }
        // Clear local state list
        bookingRecords.clear()
    }

    // Accessor for the local state list
    fun getLocalBookings(): List<BookingRecord> = bookingRecords
}
