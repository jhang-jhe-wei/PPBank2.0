package com.homi.ppbank20

import Record
import android.app.AlertDialog
import android.content.DialogInterface
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
private val TAG = addTaskFragment::class.java.simpleName

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
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        Log.d(TAG, "type")
        btn_addTask.setOnClickListener {
            if (!ed_add_task_name.text.isEmpty()&&!ed_add_task_money.text.isEmpty()) {
                AlertDialog.Builder(context).setTitle("提醒")
                    .setMessage(getString(R.string.finish)).setNegativeButton("好的", { dialogInterface: DialogInterface, i: Int ->
                        ed_add_task_name.text.clear()
                        ed_add_task_content.text.clear()
                        ed_add_task_money.text.clear()}).create()
                    .show()
                val key = FirebaseDatabase.getInstance().reference.child("tasks").child(uid).push().key
                val record = Record(
                    key.toString(),
                    uid,
                    SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(
                        Calendar.getInstance(
                            TimeZone.getTimeZone(
                                "GMT+8"
                            )
                        ).time
                    ),
                    arguments?.getString("type").toString(),
                    "0"
                )
                FirebaseDatabase.getInstance().reference.child("tasks").child(uid).child(key.toString())
                    .setValue(record)
            }
            else{
                AlertDialog.Builder(context).setTitle("提醒")
                    .setMessage("輸入錯誤!").setNegativeButton("重新輸入", null).create()
                    .show()
                ed_add_task_name.text.clear()
                ed_add_task_content.text.clear()
                ed_add_task_money.text.clear()
            }

        }
    }
}
