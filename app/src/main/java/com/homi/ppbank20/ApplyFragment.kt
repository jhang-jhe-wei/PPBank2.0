package com.homi.ppbank20

import Record
import User
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_apply.*
import kotlinx.android.synthetic.main.fragment_transfetmoneyragment.*
import kotlinx.android.synthetic.main.m_appliy_normal_layout.*
import java.text.SimpleDateFormat
import java.util.*

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
        btn_apply_commit.setOnClickListener {
            AlertDialog.Builder(context).setTitle("Message")
                .setMessage(getString(R.string.finish)).create().show()
            val key = FirebaseDatabase.getInstance().reference.child("applys").child(user?.uid.toString()).push().key
            val record = Record(
                key.toString(),
                user?.uid.toString(),
                SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(Calendar.getInstance(TimeZone.getTimeZone("GMT+8")).time),
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
        }
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
}
