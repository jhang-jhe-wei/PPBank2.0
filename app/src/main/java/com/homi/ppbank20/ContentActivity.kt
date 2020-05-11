package com.homi.ppbank20

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_content.*


private val TAG = ContentActivity::class.simpleName

class ContentActivity : AppCompatActivity() {


    private val REQUESTCODE: Int=13654
    private val login: Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        if (!login) {
            startActivityForResult(Intent(this, LoginActivity::class.java), REQUESTCODE)
        } else {

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
}
