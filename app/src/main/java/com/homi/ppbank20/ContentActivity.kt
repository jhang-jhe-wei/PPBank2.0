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
        if (auth != null) {
            Log.d(TAG, "auth!=null")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    user = p0.child("users").child(auth.uid.toString()).getValue(User::class.java)
                    bundle.putSerializable("user", user)
                    updataUI()
                }
            })
            ref.child("users").child(auth.uid.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        user?.money = p0.child("money").getValue().toString()
                        user?.incomes = p0.child("incomes").value as MutableMap<String, String>
                        user?.expenses = p0.child("expenses").value as MutableMap<String, String>
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
                        Log.d("ChildEventListener",record.toString())
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
                        Log.d("ChildEventListener",record.toString())
                        record?.let { user?.expenseRecords?.add(it) }
                        bundle.putSerializable("user", user)
                    }

                    override fun onChildRemoved(p0: DataSnapshot) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            var uid:String=""
            if(user?.parent=="")uid=user?.uid.toString()
            else uid=user?.parent.toString()
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
                        Log.d("ChildEventListener",record.toString())
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
                        Log.d("ChildEventListener",record.toString())
                        record?.let { user?.tasks?.add(it) }
                        bundle.putSerializable("user", user)
                    }

                    override fun onChildRemoved(p0: DataSnapshot) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })

        } else {
            startActivityForResult(Intent(this, LoginActivity::class.java), REQUESTCODE)
        }
    }

    fun updataUI() {
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
                intent.getBundleExtra("user")
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





