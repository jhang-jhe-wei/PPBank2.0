package com.homi.ppbank20

import Record
import User
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_m_accout.*
import kotlinx.android.synthetic.main.fragment_record.*
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [recordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class recordFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recordAdapter = M_accountFragment.RecordAdapter()
        val user = arguments?.getBundle("user") as User?
        val type = arguments?.getString("type")
        if (type.equals("income"))
            recordAdapter.setList(user?.incomeRecords)
        else
            recordAdapter.setList(user?.expenseRecords)
        record_recycleView.adapter = RecordAdapter()
    }

    class RecordAdapter : RecyclerView.Adapter<RecordAdapter.PagerViewHolder>() {
        var data = mutableListOf<Record>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {

            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.record_layout, parent, false)
            return PagerViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.bindData(position)
        }

        fun setList(list: MutableList<Record>?) {
            if (list != null) {
                data = list
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        //	ViewHolder需要繼承RecycleView.ViewHolder
        inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txv_record_date: TextView = itemView.findViewById(R.id.txv_record_date)
            val txv_record_name: TextView = itemView.findViewById(R.id.txv_record_name)
            val txv_record_money: TextView = itemView.findViewById(R.id.txv_record_money)

            fun bindData(i: Int) {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val date = simpleDateFormat.parse(data.get(i).date)
                txv_record_date.text = "${date.month}/${date.day}"
                txv_record_name.text = "${data.get(i).uid} ${data.get(i).name}"
                txv_record_money.text = "$${data.get(i).money}"
            }
        }
    }
}
