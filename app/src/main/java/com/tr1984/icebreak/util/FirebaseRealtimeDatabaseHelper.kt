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
    private val ref = database.getReference("room2")

    private val childEventListener = object : ChildEventListener {

        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
            callback.invoke(dataSnapshot.getValue(Player::class.java)!!)
//            callback.invoke(dataSnapshot.value as Player)
            // ...
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//            Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")
//
//            // A comment has changed, use the key to determine if we are displaying this
//            // comment and if so displayed the changed comment.
//            val newComment = dataSnapshot.getValue<Comment>()
//            val commentKey = dataSnapshot.key

            // ...
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//            Log.d(TAG, "onChildRemoved:" + dataSnapshot.key!!)
//
//            // A comment has changed, use the key to determine if we are displaying this
//            // comment and if so remove it.
//            val commentKey = dataSnapshot.key

            // ...
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//            Log.d(TAG, "onChildMoved:" + dataSnapshot.key!!)
//
//            // A comment has changed position, use the key to determine if we are
//            // displaying this comment and if so move it.
//            val movedComment = dataSnapshot.getValue<Comment>()
//            val commentKey = dataSnapshot.key

            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
//            Log.w(TAG, "postComments:onCancelled", databaseError.toException())
//            Toast.makeText(context, "Failed to load comments.",
//                Toast.LENGTH_SHORT).show()
        }
    }

    init {
        ref.addChildEventListener(childEventListener)
    }

    fun write(player: Player) {
        ref.push().setValue(player)
    }

    fun destroy() {
        ref.removeEventListener(childEventListener)
    }
}