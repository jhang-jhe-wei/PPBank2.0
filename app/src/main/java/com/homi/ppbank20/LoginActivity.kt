package com.homi.ppbank20


import User
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


private val TAG = LoginActivity::class.java.simpleName

class LoginActivity : AppCompatActivity() {


    var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tbx_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btn_login.setOnClickListener {
            //            Log.d(TAG,login_ed_username.text.toString())
//            Log.d(TAG,login_ed_password.text.toString()
            auth.signInWithEmailAndPassword(
                login_ed_username.text.toString(),
                login_ed_password.text.toString()
            ).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"Login success")
                    val ref = FirebaseDatabase.getInstance().reference
                    ref.child("users").addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            FirebaseDatabase.getInstance().reference.child("users")
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {
                                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        Log.d(TAG,"firebase reading")
                                        var user=p0.child(auth.uid.toString()).getValue(User::class.java)
                                        Log.d(TAG,user.toString())
                                        val intent=Intent()
                                        val bundle=Bundle()
                                        bundle.putSerializable("user",user)
                                        intent.putExtras(bundle)
                                        setResult(Activity.RESULT_OK, intent)
                                        finish()
                                    }
                                })
                        }

                    })

                } else {
                    AlertDialog.Builder(this).setTitle("message")
                        .setMessage("${task.exception?.message}").show()
                }
            })
        }
    }


    override fun onStart() {
        super.onStart()
    }


}
