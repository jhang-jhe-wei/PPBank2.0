package com.homi.ppbank20

import LoadingDialog
import User
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.fragment_m_accout.*


private val TAG = LoginActivity::class.java.simpleName

class ContentActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {
    private val PARENT_TYPE: String = "1"
    private val CHILD_TYPE: String = "0"
    private val REQUESTCODE: Int = 13654
    private var user: User? = null
    private var loadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        loadingDialog = LoadingDialog(this)
        loadingDialog?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUESTCODE) {
            if (resultCode == Activity.RESULT_OK) {
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
        val ref = FirebaseDatabase.getInstance().reference
        if (auth != null) {
            ref.child("users").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    user = p0.child(auth.uid.toString()).getValue(User::class.java)
                    val bundle=Bundle()
                    bundle.putSerializable("user",user)
                    intent.putExtra("user",bundle)
                    updataUI()
                    loadingDialog?.hide()
                }
            })
        } else {
            startActivityForResult(Intent(this, LoginActivity::class.java), REQUESTCODE)
        }
    }

    fun updataUI() {
        if (user?.type.equals(PARENT_TYPE)) {
            findNavController(R.id.navfragment).navigate(R.id.m_accountFragment,intent.getBundleExtra("user"))
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
            findNavController(R.id.navfragment).navigate(R.id.accountFragment,intent.getBundleExtra("user"))
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
}





