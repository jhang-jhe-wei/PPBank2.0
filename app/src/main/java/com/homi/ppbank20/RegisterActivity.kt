package com.homi.ppbank20

import User
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()
    val ref = FirebaseDatabase.getInstance().reference.child("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btn_register.setOnClickListener {
            FirebaseDatabase.getInstance().reference.child("users")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (!register_parent_uid.text.toString().equals("")) {
                            if (!p0.hasChild(register_parent_uid.text.toString())) {
                                AlertDialog.Builder(this@RegisterActivity)
                                    .setTitle("message")
                                    .setPositiveButton("OK", null)
                                    .setMessage("haven't this parent uid").show()
                                return
                            }
                            auth.createUserWithEmailAndPassword(
                                register_email.text.toString(),
                                register_password.text.toString()
                            ).addOnCompleteListener(
                                this@RegisterActivity,
                                OnCompleteListener<AuthResult> { task ->
                                    if (task.isSuccessful) {
                                        var user = User(
                                            auth.uid.toString(),
                                            register_name.text.toString(),
                                            register_parent_uid.text.toString(),
                                            "0",
                                            "0"
                                        )
                                        ref.child(register_parent_uid.text.toString())
                                            .child("children").child(auth.uid.toString())
                                            .setValue(register_name.text.toString())
                                        user.incomes.put("prize", "0")
                                        user.incomes.put("apply", "0")
                                        user.incomes.put("task", "0")
                                        user.incomes.put("pocketmoney", "0")
                                        user.expenses.put("food", "0")
                                        user.expenses.put("dailysupplies", "0")
                                        user.expenses.put("entertainment", "0")
                                        user.expenses.put("other", "0")
                                        ref.child(auth.uid.toString()).setValue(user)
                                        AlertDialog.Builder(this@RegisterActivity)
                                            .setTitle("Finish!")
                                            .setPositiveButton("Ok", { dialog, which -> finish() })
                                            .setMessage("${auth.uid}").show()

                                    } else {
                                        AlertDialog.Builder(this@RegisterActivity)
                                            .setTitle("message")
                                            .setMessage("${task.exception?.message}").show()

                                    }
                                })
                        } else {
                            auth.createUserWithEmailAndPassword(
                                register_email.text.toString(),
                                register_password.text.toString()
                            ).addOnCompleteListener(
                                this@RegisterActivity,
                                OnCompleteListener<AuthResult> { task ->
                                    if (task.isSuccessful) {
                                        var user = User(
                                            auth.uid.toString(),
                                            register_name.text.toString(),
                                            register_parent_uid.text.toString(),
                                            "0",
                                            "1"
                                        )
                                        user.incomes.put("prize", "0")
                                        user.incomes.put("apply", "0")
                                        user.incomes.put("task", "0")
                                        user.incomes.put("pocketmoney", "0")
                                        user.expenses.put("food", "0")
                                        user.expenses.put("dailysupplies", "0")
                                        user.expenses.put("entertainment", "0")
                                        user.expenses.put("other", "0")
                                        ref.child(auth.uid.toString()).setValue(user)
                                        AlertDialog.Builder(this@RegisterActivity)
                                            .setTitle("Finish!")
                                            .setPositiveButton("Ok", { dialog, which -> finish() })
                                            .setMessage("${auth.uid}").show()

                                    } else {
                                        AlertDialog.Builder(this@RegisterActivity)
                                            .setTitle("message")
                                            .setMessage("${task.exception?.message}").show()

                                    }
                                })
                        }
                    }
                })
        }
        btn_privacy_policy.setOnClickListener {
            AlertDialog.Builder(this).setTitle("privacy policy")
                .setMessage(getString(R.string.privacy_policy)).setPositiveButton("Ok", null).show()
        }
    }
}
