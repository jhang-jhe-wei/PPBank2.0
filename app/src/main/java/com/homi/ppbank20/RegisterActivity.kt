package com.homi.ppbank20

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    var auth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btn_register.setOnClickListener {
            auth.createUserWithEmailAndPassword(register_email.text.toString(),register_password.text.toString()).addOnCompleteListener(this,OnCompleteListener<AuthResult>{
                    task ->  if(task.isSuccessful){
                AlertDialog.Builder(this).setTitle("Finish!").setMessage("${auth.uid}").show()
                finish()
            }
            else{
                AlertDialog.Builder(this).setTitle("message").setMessage("${task.exception?.message}").show()

            }
            })
        }
        btn_privacy_policy.setOnClickListener {
            AlertDialog.Builder(this).setTitle("privacy policy").setMessage(getString(R.string.privacy_policy)).setPositiveButton("Ok",null).show()
        }
    }
}
