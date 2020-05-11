package com.homi.ppbank20

import android.R.attr.password
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


private val TAG =LoginActivity::class.java.simpleName

class LoginActivity : AppCompatActivity() {

    var auth=FirebaseAuth.getInstance()
    private val REQUESTCODE: Int=13654
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.setOnClickListener {
//            Log.d(TAG,login_ed_username.text.toString())
//            Log.d(TAG,login_ed_password.text.toString())
            auth.signInWithEmailAndPassword(login_ed_username.text.toString(),login_ed_password.text.toString()).addOnCompleteListener(this,OnCompleteListener<AuthResult>{
                    task ->  if(task.isSuccessful){
                setResult(Activity.RESULT_OK)
                finish()
            }
            else{
                AlertDialog.Builder(this).setTitle("message").setMessage("${task.exception?.message}").show()
            }
            }) }
    }



    override fun onStart() {
        super.onStart()
    }


}
