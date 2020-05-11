package com.homi.ppbank20


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


private val TAG =LoginActivity::class.java.simpleName

class LoginActivity : AppCompatActivity() {

    var auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tbx_register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        btn_login.setOnClickListener {
//            Log.d(TAG,login_ed_username.text.toString())
//            Log.d(TAG,login_ed_password.text.toString())
            auth.signInWithEmailAndPassword(login_ed_username.text.toString(),login_ed_password.text.toString()).addOnCompleteListener(this,OnCompleteListener<AuthResult>{
                    task ->  if(task.isSuccessful){
                val intent=Intent()
                intent.putExtra("uid",auth.uid)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            else{
                AlertDialog.Builder(this).setTitle("message").setMessage("${task.exception?.message}").show()
            }
            })
        }
    }



    override fun onStart() {
        super.onStart()
    }


}
