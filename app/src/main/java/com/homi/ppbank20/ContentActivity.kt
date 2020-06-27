package com.homi.ppbank20

import LoadingDialog
import Record
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
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.fragment_m_accout.*


private val TAG = ContentActivity::class.java.simpleName

class ContentActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {
    private val PARENT_TYPE: String = "1"
    private val CHILD_TYPE: String = "0"
    private val REQUESTCODE: Int = 13654
    private var user: User? = null
    private val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
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
        var uid = "123"
        if (auth != null) {
            Log.d(TAG, "auth!=null")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dsp: DataSnapshot) {
                    user = dsp.child("users").child(auth.uid.toString()).getValue(User::class.java)
                    dsp.child("incomerecords").child(auth.uid.toString()).children.forEach { it ->
                        val record = it.getValue(Record::class.java)
                        record?.let { it1 -> user?.incomeRecords?.add(it1) }
                    }
                    dsp.child("expenserecords").child(auth.uid.toString()).children.forEach { it ->
                        val record = it.getValue(Record::class.java)
                        record?.let { it1 -> user?.expenseRecords?.add(it1) }
                    }

                    if (user?.parent == "") uid = user?.uid.toString()
                    else uid = user?.parent.toString()
                    Log.d(TAG, "uid=$uid")
                    ref.child("applys").child(uid)
                        .addChildEventListener(object : ChildEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                            }

                            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                            }

                            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                                val record = p0.getValue(Record::class.java)
                                Log.d(TAG, record.toString())
                                if (!user?.applys?.contains(record)!!)
                                    record?.let { user?.applys?.add(it) }
                                bundle.putSerializable("user", user)
                            }

                            override fun onChildRemoved(p0: DataSnapshot) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }
                        })
                    ref.child("tasks").child(uid)
                        .addChildEventListener(object : ChildEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                            }

                            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                            }

                            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                                val record = p0.getValue(Record::class.java)
                                if (!user?.tasks?.contains(record)!!)
                                    record?.let { user?.tasks?.add(it) }

                                Log.d(TAG, record.toString())
                            }

                            override fun onChildRemoved(p0: DataSnapshot) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }
                        })
                    ref.child("incomerecords").child(auth.uid.toString())
                        .addChildEventListener(object : ChildEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                            }

                            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                            }

                            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                                val record = p0.getValue(Record::class.java)
                                Log.d(TAG, record.toString())
                                if (!user?.incomeRecords?.contains(record)!!)
                                    record?.let { user?.incomeRecords?.add(it) }
                                bundle.putSerializable("user", user)
                            }

                            override fun onChildRemoved(p0: DataSnapshot) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }
                        })
                    ref.child("expenserecords").child(auth.uid.toString())
                        .addChildEventListener(object : ChildEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                            }

                            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                            }

                            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                                val record = p0.getValue(Record::class.java)
                                Log.d(TAG + "here", record.toString())
                                if (!user?.expenseRecords?.contains(record)!!)
                                    record?.let { user?.expenseRecords?.add(it) }
                                bundle.putSerializable("user", user)
                            }

                            override fun onChildRemoved(p0: DataSnapshot) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }
                        })
                    bundle.putSerializable("user", user)
                    updata()
                }
            })
        } else {
            startActivityForResult(Intent(this, LoginActivity::class.java), REQUESTCODE)
        }
    }

    fun updata() {

        if (user?.type.equals(PARENT_TYPE)) {
            findNavController(R.id.navfragment).navigate(
                R.id.m_accountFragment,
                bundle
            )
            bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.menu_account -> {

                        findNavController(R.id.navfragment).navigate(
                            R.id.m_accountFragment,
                            bundle
                        )

                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.menu_apply -> {
                        findNavController(R.id.navfragment).navigate(
                            R.id.m_applyFragment,
                            bundle
                        )
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.menu_task -> {
                        findNavController(R.id.navfragment).navigate(
                            R.id.m_taskFragment,
                            bundle
                        )
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.menu_more -> {
                        findNavController(R.id.navfragment).navigate(
                            R.id.moreFragment,
                            bundle
                        )
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                false
            }
        } else {
            findNavController(R.id.navfragment).navigate(
                R.id.accountFragment,
                bundle
            )
            bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.menu_account -> {
                        findNavController(R.id.navfragment).navigate(
                            R.id.accountFragment,
                            bundle
                        )

                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.menu_apply -> {
                        findNavController(R.id.navfragment).navigate(
                            R.id.applyFragment,
                            bundle
                        )
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.menu_task -> {
                        findNavController(R.id.navfragment).navigate(
                            R.id.taskFragment,
                            bundle
                        )
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.menu_more -> {
                        findNavController(R.id.navfragment).navigate(
                            R.id.moreFragment,
                            bundle
                        )
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                false
            }
        }
    }
}




