package com.example.motorcyclerental

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object BookingManager {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _bookingRecords: SnapshotStateList<BookingRecord> = mutableStateListOf()
    val bookingRecords: SnapshotStateList<BookingRecord> get() = _bookingRecords

    private val _cancelledBookingRecords: SnapshotStateList<BookingRecord> = mutableStateListOf()
    val cancelledBookingRecords: SnapshotStateList<BookingRecord> get() = _cancelledBookingRecords

    fun addBooking(
        bikeName: String,
        rateType: String,
        totalCost: String,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val timestamp = System.currentTimeMillis()
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onFailure(Exception("User is not logged in"))
            return
        }

        val newBooking = BookingRecord("", bikeName, rateType, totalCost, timestamp)

        db.collection("users")
            .document(userId)
            .collection("bookings")
            .add(newBooking.toMap())
            .addOnSuccessListener { documentRef ->
                _bookingRecords.add(newBooking.copy(id = documentRef.id))
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun addCancelledBooking(
        booking: BookingRecord,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onFailure(Exception("User is not logged in"))
            return
        }

        db.collection("users")
            .document(userId)
            .collection("cancelledBookings")
            .add(booking.toMap())
            .addOnSuccessListener { documentRef ->
                _cancelledBookingRecords.add(booking.copy(id = documentRef.id))
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun fetchAllBookings(
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onFailure(Exception("User is not logged in"))
            return
        }

        db.collection("users")
            .document(userId)
            .collection("bookings")
            .get()
            .addOnSuccessListener { result ->
                val bookings = result.documents.mapNotNull { doc ->
                    doc.data?.let { BookingRecord.fromMap(it, doc.id) }
                }
                _bookingRecords.clear()
                _bookingRecords.addAll(bookings)
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun fetchCancelledBookings(
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onFailure(Exception("User is not logged in"))
            return
        }

        db.collection("users")
            .document(userId)
            .collection("cancelledBookings")
            .get()
            .addOnSuccessListener { result ->
                val cancelledBookings = result.documents.mapNotNull { doc ->
                    doc.data?.let { BookingRecord.fromMap(it, doc.id) }
                }
                _cancelledBookingRecords.clear()
                _cancelledBookingRecords.addAll(cancelledBookings)
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun cancelBooking(
        bookingId: String,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onFailure(Exception("User is not logged in"))
            return
        }

        val bookingRef = db.collection("users")
            .document(userId)
            .collection("bookings")
            .document(bookingId)

        bookingRef.delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun clearAllBookings(
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onFailure(Exception("User is not logged in"))
            return
        }

        db.collection("users")
            .document(userId)
            .collection("bookings")
            .get()
            .addOnSuccessListener { result ->
                val batch = db.batch()
                result.documents.forEach { document ->
                    batch.delete(document.reference)
                }
                batch.commit()
                    .addOnSuccessListener {
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

data class BookingRecord(
    val id: String = "",
    val bikeName: String,
    val rateType: String,
    val totalCost: String,
    val timestamp: Long
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "bikeName" to bikeName,
            "rateType" to rateType,
            "totalCost" to totalCost,
            "timestamp" to timestamp
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any>, id: String): BookingRecord {
            return BookingRecord(
                id = id,
                bikeName = map["bikeName"] as String,
                rateType = map["rateType"] as String,
                totalCost = map["totalCost"] as String,
                timestamp = (map["timestamp"] as Long)
            )
        }
    }
}
