package com.homi.ppbank20

import Record
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_apply_detail.*
import kotlinx.android.synthetic.main.fragment_taskdetail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [taskdetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class applyDetailFragment : Fragment() {

    private var record = Record()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        record = arguments?.getSerializable("data") as Record
        txv_applyDetail_name.text = record.name
        txv_applyDetail_prize.text = record.money
        txv_applyDetail_content.text = record.content
        val ref=FirebaseDatabase.getInstance().reference.child("tasks").child(record.uid).child(record.id)

        btn_applyDetail_fin.setOnClickListener {
            when (record.state) {
                "0" -> {
                    btn_applyDetail_fin.background = resources.getDrawable(R.drawable.grey_frame)
                    record.state="1"
                    record.name="apply"
                    ref.setValue(record)
                    Transfetmoneyragment.transfermoney(record,FirebaseAuth.getInstance().uid.toString())
                }
            } }

        when (record.state) {
            "0" -> {
                btn_applyDetail_fin.background = resources.getDrawable(R.drawable.red_frame)
            }
            "1" -> {
                
                btn_applyDetail_fin.background = resources.getDrawable(R.drawable.grey_frame)                
            }
        }
    }
}
