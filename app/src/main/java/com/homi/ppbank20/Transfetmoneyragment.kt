package com.homi.ppbank20

import Record
import User
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_apply.*
import kotlinx.android.synthetic.main.fragment_transfetmoneyragment.*
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private val TAG=Transfetmoneyragment::class.java.simpleName
/**
 * A simple [Fragment] subclass.
 * Use the [Transfetmoneyragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Transfetmoneyragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_transfetmoneyragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as? User
        if (user != null) {
            val account_spinnerArrayAdapter: ArrayAdapter<String>? = context?.let {
                ArrayAdapter<String>(
                    it, R.layout.spinner_item, user?.children?.values?.toMutableList()!!
                )
            }
            account_spinnerArrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
            account_spinnerArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            account_spinner.adapter = account_spinnerArrayAdapter

            val type_spinnerArrayAdapter: ArrayAdapter<String>? = context?.let {
                ArrayAdapter<String>(
                    it,
                    R.layout.spinner_item,
                    resources.getStringArray(R.array.income_array).toMutableList()
                )
            }
            type_spinnerArrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
            type_spinnerArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            type_spinner.adapter = type_spinnerArrayAdapter
            btn_Transfer.setOnClickListener {
                val money=ed_transfer_money.text.toString()
                val name=type_spinner.selectedItem.toString()
                val content=ed_transfer_remark.text.toString()
                val account=account_spinner.selectedItem.toString()
                val password=ed_transfer_password.text.toString()
                val key = FirebaseDatabase.getInstance().reference.child("incomerecords")
                    .child(user?.uid.toString()).push().key
                val record = Record(
                    key.toString(),
                    user?.uid.toString(),
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                        Calendar.getInstance(
                            TimeZone.getTimeZone(
                                "GMT+8"
                            )
                        ).time
                    ),
                    name,
                    content,
                    money,
                    "", ""
                )
                val ref = FirebaseDatabase.getInstance().reference
                ref.child("incomerecords").child(user?.uid.toString()).child(key.toString())
                    .setValue(record)

                ref.child("users").child(user?.uid.toString()).child("incomes").child(record.name)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val buffer:String = p0.getValue().toString()
                            ref.child("users").child(user?.uid.toString()).child("incomes")
                                .child(record.name)
                                .setValue((money.toInt() + buffer.toInt()).toString())
                        }
                    })
                var childuid: String = ""
                user?.children?.keys?.forEach { it ->
                    if (user?.children?.getValue(it.toString()).equals(
                            account
                        )
                    ) childuid = it.toString()
                }
                ref.child("incomerecords").child(childuid).child(key.toString()).setValue(record)

                ref.child("users").child(childuid).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val buffer1 = p0.child("money").getValue()
                            val buffer=p0.child("incomes").child(record.name).getValue()

                            ref.child("users").child(childuid).child("money")
                                .setValue((buffer.toString().toInt() + money.toInt()).toString())
                            ref.child("users").child(childuid).child("incomes").child(name)
                                .setValue((buffer.toString().toInt() + money.toInt()).toString())
                        }
                    })

            }

        }

    }
}
