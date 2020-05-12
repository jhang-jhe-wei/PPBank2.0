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




class ContentActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {
    private val TAG = ContentActivity::class.simpleName
    private val PARENT_TYPE: String = "1"
    private val CHILD_TYPE: String = "0"
    private val REQUESTCODE: Int = 13654
    private var user: User? = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUESTCODE) {
            if (resultCode == Activity.RESULT_OK) {
                user = data?.extras?.getSerializable("user")as User
                Log.d("TAG",user?.toString())
                if (user?.type.equals(PARENT_TYPE)) {
                    findNavController(R.id.navfragment).navigate(R.id.m_accountFragment)
                    bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
                        when (item.itemId) {
                            R.id.menu_account -> {
                                findNavController(R.id.navfragment).navigate(
                                    R.id.m_accountFragment,                                    
                                    intent.getBundleExtra("user")
                                )

                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.menu_apply -> {
                                findNavController(R.id.navfragment).navigate(
                                    R.id.applyFragment,
                                    intent.getBundleExtra("user")
                                )
                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.menu_task -> {
                                findNavController(R.id.navfragment).navigate(
                                    R.id.taskFragment,
                                    intent.getBundleExtra("user")
                                )
                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.menu_more -> {
                                findNavController(R.id.navfragment).navigate(
                                    R.id.moreFragment,
                                    intent.getBundleExtra("user")
                                )
                                return@setOnNavigationItemSelectedListener true
                            }
                        }
                        false
                    }
                } else {
                    findNavController(R.id.navfragment).navigate(R.id.accountFragment)
                    bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
                        when (item.itemId) {
                            R.id.menu_account -> {
                                findNavController(R.id.navfragment).navigate(
                                    R.id.accountFragment,
                                    intent.getBundleExtra("user")
                                )

                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.menu_apply -> {
                                findNavController(R.id.navfragment).navigate(
                                    R.id.applyFragment,
                                    intent.getBundleExtra("user")
                                )
                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.menu_task -> {
                                findNavController(R.id.navfragment).navigate(
                                    R.id.taskFragment,
                                    intent.getBundleExtra("user")
                                )
                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.menu_more -> {
                                findNavController(R.id.navfragment).navigate(
                                    R.id.moreFragment,
                                    intent.getBundleExtra("user")
                                )
                                return@setOnNavigationItemSelectedListener true
                            }
                        }
                        false
                    }
                }
            }
        } else {
            Log.d(TAG, "REQUESTCODE ERROR!")
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
        var auth = p0.currentUser
        if (auth == null) {
            startActivityForResult(Intent(this, LoginActivity::class.java), REQUESTCODE)
        } else {
            androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Finish!")
                .setPositiveButton("sign out",
                    { dialog, which -> FirebaseAuth.getInstance().signOut() })
                .setMessage("${auth.uid}").show()

        }
    }
}


