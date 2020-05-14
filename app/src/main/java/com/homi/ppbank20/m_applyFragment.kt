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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_m_apply.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private val TAG=m_applyFragment::class.java.simpleName
/**
 * A simple [Fragment] subclass.
 * Use the [m_applyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class m_applyFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_m_apply, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as? User
        Log.d(TAG,user?.applys.toString())
        val recyclerView=applyAdapter()
        recyclerView.setList(user?.applys)
        m_apply_recycleview.layoutManager=LinearLayoutManager(context)
        m_apply_recycleview.setHasFixedSize(true)
        m_apply_recycleview.adapter=recyclerView
    }

    inner class applyAdapter : RecyclerView.Adapter<applyAdapter.PagerViewHolder>() {
        var data = mutableListOf<Record>()
        private val TITLE = 0
        private val CONTENT = 1
        private val SETTING = 2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            var itemView: View
            when (viewType) {
                TITLE -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.m_apply_title_layout, parent, false)
                CONTENT -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.m_appliy_normal_layout, parent, false)
                SETTING -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.m_apply_radio_layout, parent, false)
                else -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.m_appliy_normal_layout, parent, false)
            }

            return PagerViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            when (holder.itemViewType) {
                TITLE -> holder.bindTitle(position)
                CONTENT -> holder.bindContent(position)
                SETTING -> holder.bindSetting(position)
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (position == 0 || position == data.size + 1) return TITLE
            else if (position <= data.size) return CONTENT
            else return SETTING
        }

        fun setList(list: MutableList<Record>?) {
            if (list != null) {
                data = list
            }
            Log.d(TAG,data.toString())
        }

        override fun getItemCount(): Int {
            return data.size + 4
        }

        //	ViewHolder需要繼承RecycleView.ViewHolder
        inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val bundle=Bundle()

            fun bindTitle(i: Int) {
                val title: TextView = itemView.findViewById(R.id.m_apply_title)
                if (i == 0) title.text = "Recently applied"
                else title.text = "Function Setting"
            }

            fun bindContent(i: Int) {
                Log.d(TAG,"OnCall")
                val index = i - 1
                val content: TextView = itemView.findViewById(R.id.txv_m_apply_content)
                val money: TextView = itemView.findViewById(R.id.txv_m_apply_money)
                content.text=data.get(index).name
                money.text=data.get(index).money
                itemView.setOnClickListener {
                    bundle.putSerializable("data",data.get(index))
                    itemView.setOnClickListener { findNavController().navigate(R.id.applyDetailFragment, bundle) }
                }
            }

            fun bindSetting(i: Int) {
                val index=i-data.size
                val title:TextView=itemView.findViewById(R.id.radioview_title)
                val content:TextView=itemView.findViewById(R.id.radioview_content)
                if(index==1) {
                    title.text = "expense"
                    content.text="Up to 300 for a single apply"
                }
                else {
                    title.text = "prize"
                    content.text="Up to 3000 for a single apply"
                }
            }

        }
    }
}
