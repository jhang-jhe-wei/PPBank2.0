package com.homi.ppbank20

import Record
import User
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_apply.*
import kotlinx.android.synthetic.main.fragment_transfetmoneyragment.*
import kotlinx.android.synthetic.main.m_appliy_normal_layout.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private val TAG = ApplyFragment::class.java.simpleName

/**
 * A simple [Fragment] subclass.
 * Use the [ApplyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApplyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var user: User? = User()
    private val EXPENSE_STATE = 0
    private val PRIZE_STATE = 1
    private val INIT_STATE = 0
    private var type: Int = EXPENSE_STATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as? User
        expenseApplystate()
        btn_expense_apply.setOnClickListener {
            expenseApplystate()
        }
        btn_prize_apply.setOnClickListener {
            prizeApplystate()
        }
        apply_prove.setOnClickListener {
            Log.d(TAG,"check")
            checkPermission()
//            val permission = context?.let { it1 ->
//                ContextCompat.checkSelfPermission(
//                    it1,
//                    android.Manifest.permission.CAMERA
//                )
//            }
//            if (permission == PackageManager.PERMISSION_GRANTED)
//                takePhoto()
//            else{
//                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.CAMERA),
//                    REQUEST_EXTERNAL_STORAGE)
//            }
        }
        btn_apply_commit.setOnClickListener {
            if (!apply_money.text.isEmpty()) {
                AlertDialog.Builder(context).setTitle("提醒")
                    .setMessage(getString(R.string.finish)).setNegativeButton("好的",null).create().show()
                val key =
                    FirebaseDatabase.getInstance().reference.child("applys").child(user?.uid.toString())
                        .push().key
                val record = Record(
                    key.toString(),
                    user?.uid.toString(),
                    SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(
                        Calendar.getInstance(
                            TimeZone.getTimeZone(
                                "GMT+8"
                            )
                        ).time
                    ),
                    apply_spinner.selectedItem.toString(),
                    apply_content.text.toString(),
                    apply_money.text.toString(),
                    type.toString(),
                    INIT_STATE.toString()
                )
                Log.d(TAG, record.toString())
                FirebaseDatabase.getInstance().reference.child("applys").child(user?.parent.toString())
                    .child(key.toString())
                    .setValue(record)
                apply_content.text.clear()
                apply_money.text.clear()
            }
            else{
                AlertDialog.Builder(context).setTitle("提醒")
                    .setMessage("輸入錯誤!").setNegativeButton("重新輸入", null).create()
                    .show()
            }
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    fun expenseApplystate() {
        type = EXPENSE_STATE
        val apply_spinnerArrayAdapter: ArrayAdapter<String>? = context?.let {
            ArrayAdapter<String>(
                it,
                R.layout.spinner_item,
                resources.getStringArray(R.array.expense_array).toMutableList()
            )
        }
        apply_spinnerArrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
        apply_spinnerArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        apply_spinner.adapter = apply_spinnerArrayAdapter
    }

    fun prizeApplystate() {
        type = PRIZE_STATE
        val apply_spinnerArrayAdapter: ArrayAdapter<String>? = context?.let {
            ArrayAdapter<String>(
                it,
                R.layout.spinner_item,
                resources.getStringArray(R.array.income_array).toMutableList()
            )
        }
        apply_spinnerArrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
        apply_spinnerArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        apply_spinner.adapter = apply_spinnerArrayAdapter
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                }
            }
        }
    }

    private fun checkPermission() {
        val permission = context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                android.Manifest.permission.CAMERA
            )
        }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_EXTERNAL_STORAGE
            )
        } else {
            takePhoto()
        }
    }

    companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 200
    }
}
