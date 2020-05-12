package com.homi.ppbank20

import com.google.firebase.database.FirebaseDatabase
import kotlin.concurrent.thread

fun main() {
    thread { val ref= FirebaseDatabase.getInstance().reference
        ref.child("incomerecords").child("o6SqgtYsQOQTcYe91phoxYnAEft2").push().setValue("/date/9999999")
        ref.child("incomerecords").child("o6SqgtYsQOQTcYe91phoxYnAEft2").push().setValue("/date/1111111") }

}