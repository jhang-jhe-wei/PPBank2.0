package com.homi.ppbank20

import Record
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_add_task.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private val TAG=addTaskFragment::class.java.simpleName
/**
 * A simple [Fragment] subclass.
 * Use the [addTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class addTaskFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid=FirebaseAuth.getInstance().currentUser?.uid.toString()
        Log.d(TAG,"type")
        btn_addTask.setOnClickListener {
            val key=FirebaseDatabase.getInstance().reference.child("tasks").child(uid).push().key
            val record = Record(
                key.toString(),
                uid,
                SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(Calendar.getInstance(TimeZone.getTimeZone("GMT+8")).time),
                ed_add_task_name.text.toString(),
                ed_add_task_content.text.toString(),
                ed_add_task_money.text.toString(),
                arguments?.getString("type").toString(),
                "0"
            )
            FirebaseDatabase.getInstance().reference.child("tasks").child(uid).child(key.toString()).setValue(record)



        }
    }
}
