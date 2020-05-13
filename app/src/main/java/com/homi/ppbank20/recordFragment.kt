package com.homi.ppbank20

import Record
import User
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_record.*
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private val TAG=recordFragment::class.java.simpleName

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
        val user = arguments?.getSerializable("user") as User?
        val type = arguments?.getString("type")
        val recordAdapter = RecordsAdapter()
        if (type.equals("income"))
            user?.incomeRecords?.let { recordAdapter.setList(it) }
        else
            user?.expenseRecords?.let { recordAdapter.setList(it) }
        record_recycleView.layoutManager= LinearLayoutManager(context)
        record_recycleView.adapter = recordAdapter
    }

    class RecordsAdapter : RecyclerView.Adapter<RecordsAdapter.PagerViewHolder>() {
        var data = mutableListOf<Record>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {

            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.record_layout, parent, false)
            return PagerViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.bindData(position)
        }

        fun setList(list: MutableList<Record>) {
            data = list
            Log.d(TAG,data.toString())
        }

        override fun getItemCount(): Int {
            Log.d(TAG,data.size.toString())
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
                txv_record_name.text = "${data.get(i).name}"
                txv_record_money.text = "$${data.get(i).money}"
            }
        }
    }
}
