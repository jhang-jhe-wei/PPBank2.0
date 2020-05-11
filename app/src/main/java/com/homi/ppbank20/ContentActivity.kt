package com.homi.ppbank20

import User
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_content.*


private val TAG = ContentActivity::class.simpleName

class ContentActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {


    private val REQUESTCODE: Int=13654
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_account -> {
                    findNavController(R.id.navfragment).navigate(R.id.accountFragment)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_apply -> {
                    findNavController(R.id.navfragment).navigate(R.id.applyFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_task -> {
                    findNavController(R.id.navfragment).navigate(R.id.taskFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_more -> {
                    findNavController(R.id.navfragment).navigate(R.id.moreFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==REQUESTCODE) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }else {
            Log.d(TAG,"REQUESTCODE ERROR!")
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
       FirebaseAuth.getInstance().addAuthStateListener(this)
    }



    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {

        var auth=p0.currentUser
        if (auth==null) {
            startActivityForResult(Intent(this, LoginActivity::class.java), REQUESTCODE)
        } else {
            androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Finish!").setMessage("${auth.uid}").show()

        }
    }
}


