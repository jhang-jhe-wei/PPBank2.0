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
import kotlinx.android.synthetic.main.fragment_task.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private val TAG=taskFragment::class.java.simpleName
/**
 * A simple [Fragment] subclass.
 * Use the [taskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class taskFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as? User
        Log.d(TAG,user.toString())
        val taskAdapter=TaskAdapter()
        user?.tasks?.let { taskAdapter.setData(it) }
        task_recycleview.layoutManager= LinearLayoutManager(context)
        task_recycleview.adapter=taskAdapter
    }

    inner class TaskAdapter : RecyclerView.Adapter<TaskAdapter.PagerViewHolder>() {
        var immediateTask= mutableListOf<Record>()
        var dailytask= mutableListOf<Record>()
        var tasks= mutableListOf<Record>()
        private val TITLE = 0
        private val CONTENT = 1
        private val IMMEDIATE="0"
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            var itemView: View
            when (viewType) {
                TITLE -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.m_apply_title_layout, parent, false)
                CONTENT -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_layout, parent, false)
                else -> itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_layout, parent, false)
            }

            return PagerViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            when (holder.itemViewType) {
                TITLE -> holder.bindTitle(position)
                CONTENT -> holder.bindContent(position)
            }
        }

        override fun getItemViewType(position: Int): Int {
            if(position==0||position==immediateTask.size+1)return TITLE
            else return CONTENT
        }

        fun setData(list:MutableList<Record>) {
            tasks=list
            tasks.forEach { it->if (it.type.equals(IMMEDIATE)) immediateTask.add(it)else dailytask.add((it))}
        }

        override fun getItemCount(): Int {
            return tasks.size + 2
        }

        //	ViewHolder需要繼承RecycleView.ViewHolder
        inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bindTitle(i: Int) {
                val title: TextView = itemView.findViewById(R.id.m_apply_title)
                if (i == 0) title.text = "Immediate tasks"
                else title.text = "Daily tasks"
            }

            fun bindContent(i: Int) {
                val content: TextView = itemView.findViewById(R.id.m_task_name)
                val money: TextView = itemView.findViewById(R.id.m_task_prize)
                if(i<=immediateTask.size) {
                    val index = i - 1
                    content.text = immediateTask.get(index).content
                    money.text = immediateTask.get(index).money
                    val bundle=Bundle()
                    bundle.putSerializable("data",immediateTask.get(index))
                    itemView.setOnClickListener { findNavController().navigate(R.id.taskdetailFragment, bundle) }
                }
                else {
                    val index = i - immediateTask.size-2
                    content.text = dailytask.get(index).content
                    money.text = dailytask.get(index).money
                    itemView.setOnClickListener {  }
                }
            }


        }
    }
}
