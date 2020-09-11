package com.tr1984.icebreak.util

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tr1984.icebreak.model.Player

class FirebaseRealtimeDatabaseHelper(val callback: (Player) -> Unit) {

    private val database = Firebase.database
    private val ref = database.getReference("game1")

    private val childEventListener = object : ChildEventListener {

        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            Log.d("test", dataSnapshot.toString())
            callback.invoke(dataSnapshot.getValue(Player::class.java)!!)
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    }

    init {
        ref.addChildEventListener(childEventListener)
    }

    fun write(player: Player) {
        ref.child(player.uid).setValue(player)
    }

    fun destroy() {
        ref.removeEventListener(childEventListener)
    }
}