package com.homi.ppbank20

import Record
import User
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_apply.*
import kotlinx.android.synthetic.main.fragment_mobliepay.*
import kotlinx.android.synthetic.main.fragment_transfetmoneyragment.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private val TAG = mobliepayFragment::class.java.simpleName

/**
 * A simple [Fragment] subclass.
 * Use the [mobliepayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class mobliepayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var user: User? = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mobliepay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as? User
        Log.d(TAG, user.toString())
        val expense_spinnerArrayAdapter: ArrayAdapter<String>? = context?.let {
            ArrayAdapter<String>(
                it,
                R.layout.spinner_item,
                resources.getStringArray(R.array.expense_array).toMutableList()
            )
        }
        expense_spinnerArrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
        expense_spinnerArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expense_spinner.adapter = expense_spinnerArrayAdapter
        btn_mobliepay.setOnClickListener {
            val key = FirebaseDatabase.getInstance().reference.child("applys").child(user?.uid.toString()).push().key
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
                expense_spinner.selectedItem.toString(),
                ed_mobliepay_content.text.toString(),
                ed_mobliepay_money.text.toString(),
                "",
                ""
            )
            Log.d(TAG, record.toString())

            val ref = FirebaseDatabase.getInstance().reference
            ref.child("expenserecords").child(user?.parent.toString()).child(key.toString())
                .setValue(record)
            ref.child("users").child(user?.parent.toString()).child("expenses").child(record.name)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val money = p0.getValue()
                        ref.child("users").child(user?.parent.toString()).child("expenses")
                            .child(record.name)
                            .setValue((money.toString().toInt() + ed_mobliepay_money.text.toString().toInt()).toString())

                    }
                })

            ref.child("expenserecords").child(user?.uid.toString()).child(key.toString()).setValue(record)

            ref.child("users").child(user?.uid.toString()).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val money = p0.child("money").getValue()
                    val buffer =
                        p0.child("expenses").child(expense_spinner.selectedItem.toString()).getValue()
                    ref.child("users").child(user?.uid.toString()).child("money")
                        .setValue((money.toString().toInt() - ed_mobliepay_money.text.toString().toInt()).toString())
                    ref.child("users").child(user?.uid.toString()).child("expenses")
                        .child(expense_spinner.selectedItem.toString())
                        .setValue((buffer.toString().toInt() + ed_mobliepay_money.text.toString().toInt()).toString())
                }
            })
        }


    }
}
