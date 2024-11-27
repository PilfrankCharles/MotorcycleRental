package com.example.motorcyclerental

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object BookingManager {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Local state list for holding booking records
    private val _bookingRecords: SnapshotStateList<BookingRecord> = mutableStateListOf()
    val bookingRecords: SnapshotStateList<BookingRecord> get() = _bookingRecords

    // Add a booking to Firestore and update local state
    fun addBooking(bikeName: String, rateType: String, totalCost: String, onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        val timestamp = System.currentTimeMillis()
        val newBooking = BookingRecord(bikeName, rateType, totalCost, timestamp)

        // Check if user is logged in
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onFailure(Exception("User not logged in"))
            return
        }

        // Save to Firestore
        db.collection("users").document(userId).collection("bookings")
            .add(newBooking)
            .addOnSuccessListener {
                // Update local state
                _bookingRecords.add(newBooking)
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Fetch all bookings from Firestore and update the local state list
    fun fetchAllBookings(onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onFailure(Exception("User not logged in"))
            return
        }

        db.collection("users").document(userId).collection("bookings")
            .get()
            .addOnSuccessListener { result ->
                // Map Firestore documents to BookingRecord objects
                val bookings = result.documents.mapNotNull { it.toObject(BookingRecord::class.java) }

                // Update local state
                _bookingRecords.clear()
                _bookingRecords.addAll(bookings)

                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Clear all bookings from Firestore and local state list
    fun clearAllBookings(onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onFailure(Exception("User not logged in"))
            return
        }

        db.collection("users").document(userId).collection("bookings")
            .get()
            .addOnSuccessListener { result ->
                val batch = db.batch()

                // Delete all documents in the collection
                result.documents.forEach { document ->
                    batch.delete(document.reference)
                }

                batch.commit()
                    .addOnSuccessListener {
                        // Clear local state
                        _bookingRecords.clear()
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception)
                    }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
