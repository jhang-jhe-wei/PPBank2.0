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
import kotlinx.android.synthetic.main.fragment_m_task.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private val TAG=m_taskFragment::class.java.simpleName

/**
 * A simple [Fragment] subclass.
 * Use the [m_taskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class m_taskFragment : Fragment() {

    private var user: User? = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_m_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as? User
        val taskAdapter=TaskAdapter()
        user?.tasks?.let { taskAdapter.setData(it) }
        Log.d(TAG,user?.tasks.toString())
        M_task_recycleview.layoutManager=LinearLayoutManager(context)
        M_task_recycleview.adapter=taskAdapter
    }

    inner class TaskAdapter : RecyclerView.Adapter<TaskAdapter.PagerViewHolder>() {
        var immediateTask= mutableListOf<Record>()
        var dailytask= mutableListOf<Record>()
        var tasks= mutableListOf<Record>()
        private val TITLE = 0
        private val CONTENT = 1
        private val SETTING = 2
        private val RADIO=3
        private val IMMEDIATE="0"
        private val DAILY="1"
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            var itemView: View
            when (viewType) {
                TITLE -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.m_apply_title_layout, parent, false)
                CONTENT -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.m_task_layout, parent, false)
                RADIO-> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.m_task_radio_layout ,parent, false)
                SETTING -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_add_layout, parent, false)
                else -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.m_task_layout, parent, false)
            }

            return PagerViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            when (holder.itemViewType) {
                TITLE -> holder.bindTitle(position)
                CONTENT -> holder.bindContent(position)
                SETTING -> holder.bindSetting(position)
                RADIO->holder.bindRadio(position)
            }
        }

        override fun getItemViewType(position: Int): Int {
            if(position==0||position==immediateTask.size+2)return TITLE
            else if(position==immediateTask.size+1||position==dailytask.size+immediateTask.size+3)return SETTING
            else if(position<=immediateTask.size) return CONTENT
            else return RADIO
        }

        fun setData(list:MutableList<Record>) {
            tasks=list
            tasks.forEach { it->if (it.type==IMMEDIATE) immediateTask.add(it)else dailytask.add((it))}
        }

        override fun getItemCount(): Int {
            return tasks.size + 4
        }

        //	ViewHolder需要繼承RecycleView.ViewHolder
        inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val bundle=Bundle()

            fun bindTitle(i: Int) {
                val title: TextView = itemView.findViewById(R.id.m_apply_title)
                if (i == 0) title.text = getString(R.string.Immediatetasks)
                else title.text = getString(R.string.Dailytasks)
            }

            fun bindRadio(i: Int) {
                val content: TextView = itemView.findViewById(R.id.m_radioview_title)
                val money: TextView = itemView.findViewById(R.id.m_radioview_content)
                val index = dailytask.size-(i-immediateTask.size-3)-1
                content.text=dailytask.get(index).name
                money.text=dailytask.get(index).money
            }

            fun bindContent(i: Int) {
                val content: TextView = itemView.findViewById(R.id.m_task_name)
                val money: TextView = itemView.findViewById(R.id.m_task_prize)
                val index = immediateTask.size-(i-1)-1
                content.text=immediateTask.get(index).name
                money.text=immediateTask.get(index).money

            }

            fun bindSetting(i: Int) {
                val content:TextView=itemView.findViewById(R.id.task_add_content)
                if(i==immediateTask.size+1) {
                    content.text = getString(R.string.Addimmediatetask)
                    itemView.setOnClickListener {
                        bundle.putString("type",IMMEDIATE)
                        findNavController().navigate(R.id.addTaskFragment,bundle)
                    }
                }
                else {
                    content.text = getString(R.string.Adddailytask)
                    itemView.setOnClickListener {
                        bundle.putString("type",DAILY)
                        findNavController().navigate(R.id.addTaskFragment,bundle)
                    }
                }

            }

        }
    }
}
