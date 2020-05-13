package com.homi.ppbank20

import Record
import User
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_m_accout.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private val TAG = M_accountFragment::class.java.simpleName

/**
 * A simple [Fragment] subclass.
 * Use the [M_accoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class M_accountFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_m_accout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as? User
        Log.d(TAG, user.toString())
        M_account_viewpager.adapter = viewPagerAdapter()
        M_btn_account_right.setOnClickListener { findNavController().navigate(R.id.transfetmoneyragment,arguments) }
        if(user!=null){
            parentState()
            btn_parent.setOnClickListener { parentState() }
            btn_child.setOnClickListener { childState() }
        }

    }
    fun parentState(){
        val bundle=Bundle()
        bundle.putSerializable("user", user)
        bundle.putString("type","income")
        btn_m_more.setOnClickListener { findNavController().navigate(R.id.recordFragment,bundle) }
        //設定圓餅圖右方敘述
        M_tbx_pie_detail01.setText("prize")
        M_tbx_pie_detail02.setText("apply")
        M_tbx_pie_detail03.setText("task")
        M_tbx_pie_detail04.setText("pocket money")
        var pieData: MutableList<SliceValue>
        pieData = mutableListOf()

        pieData.add(
            SliceValue(
                user?.incomes?.get("prize").toString().toFloat(),
                resources.getColor(R.color.colorPieChartDark)
            )
        )
        pieData.add(
            SliceValue(
                user?.incomes?.get("apply").toString().toFloat(),
                resources.getColor(R.color.colorPieChartLight)
            )
        )
        pieData.add(
            SliceValue(
                user?.incomes?.get("task").toString().toFloat(),
                resources.getColor(R.color.colorPieChartLighter)
            )
        )
        pieData.add(
            SliceValue(
                user?.incomes?.get("pocketmoney").toString().toFloat(),
                resources.getColor(R.color.colorPieChartDarker)
            )
        )
        var sum =
            user?.incomes?.get("prize").toString().toFloat() + user?.incomes?.get("apply").toString().toFloat() + user?.incomes?.get(
                "task"
            ).toString().toFloat() + user?.incomes?.get("pocketmoney").toString().toFloat()


        //設定圓餅圖 之後要改用data塞

        //設定圓餅圖 之後要改用data塞
        val pieChartData = PieChartData(pieData)
        pieChartData.setHasCenterCircle(true).setCenterText1("Sum").setCenterText1FontSize(20)
            .centerText1Color =
            Color.parseColor("#FF3C4D")
        pieChartData.setHasCenterCircle(true).setCenterText2("$$sum").setCenterText2FontSize(20)
            .centerText2Color =
            Color.parseColor("#FF3C4D")
        M_pie_chart.setPieChartData(pieChartData)

        val recordAdapter=RecordAdapter()
        recordAdapter.setList(user?.incomeRecords)
        M_account_RecyclerView.adapter=RecordAdapter()
    }

    fun childState(){
        val bundle=Bundle()
        bundle.putSerializable("user", user)
        bundle.putString("type","income")
        btn_m_more.setOnClickListener { findNavController().navigate(R.id.recordFragment,bundle) }
        //設定圓餅圖右方敘述
        M_tbx_pie_detail01.setText("food")
        M_tbx_pie_detail02.setText("daily supplies")
        M_tbx_pie_detail03.setText("entertainment")
        M_tbx_pie_detail04.setText("other")
        var pieData: MutableList<SliceValue>
        pieData = mutableListOf()

        pieData.add(
            SliceValue(
                user?.expenses?.get("food").toString().toFloat(),
                resources.getColor(R.color.colorPieChartDark)
            )
        )
        pieData.add(
            SliceValue(
                user?.expenses?.get("dailysupplies").toString().toFloat(),
                resources.getColor(R.color.colorPieChartLight)
            )
        )
        pieData.add(
            SliceValue(
                user?.expenses?.get("entertainment").toString().toFloat(),
                resources.getColor(R.color.colorPieChartLighter)
            )
        )
        pieData.add(
            SliceValue(
                user?.expenses?.get("other").toString().toFloat(),
                resources.getColor(R.color.colorPieChartDarker)
            )
        )
        var sum =
            user?.expenses?.get("food").toString().toFloat() + user?.expenses?.get("dailysupplies").toString().toFloat() + user?.expenses?.get(
                "entertainment"
            ).toString().toFloat() + user?.expenses?.get("other").toString().toFloat()


        //設定圓餅圖 之後要改用data塞

        //設定圓餅圖 之後要改用data塞
        val pieChartData = PieChartData(pieData)
        pieChartData.setHasCenterCircle(true).setCenterText1("Sum").setCenterText1FontSize(20)
            .centerText1Color =
            Color.parseColor("#FF3C4D")
        pieChartData.setHasCenterCircle(true).setCenterText2("$$sum").setCenterText2FontSize(20)
            .centerText2Color =
            Color.parseColor("#FF3C4D")
        M_pie_chart.setPieChartData(pieChartData)

        val recordAdapter=RecordAdapter()
        recordAdapter.setList(user?.expenseRecords)
        M_account_RecyclerView.adapter=RecordAdapter()
    }

    class RecordAdapter : RecyclerView.Adapter<RecordAdapter.PagerViewHolder>() {
        var data= mutableListOf<Record>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {

            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.record_layout, parent, false)
            return PagerViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.bindData(position)
        }

        fun setList(list: MutableList<Record>?){
            if (list != null) {
                data=list
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        //	ViewHolder需要繼承RecycleView.ViewHolder
        inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txv_record_date:TextView=itemView.findViewById(R.id.txv_record_date)
            val txv_record_name:TextView=itemView.findViewById(R.id.txv_record_name)
            val txv_record_money:TextView=itemView.findViewById(R.id.txv_record_money)

            fun bindData(i: Int) {
                val simpleDateFormat=SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val date=simpleDateFormat.parse(data.get(i).date)
                txv_record_date.text="${date.month}/${date.day}"
                txv_record_name.text="${data.get(i).uid} ${data.get(i).name}"
                txv_record_money.text="$${data.get(i).money}"
            }
        }
    }

    class viewPagerAdapter : RecyclerView.Adapter<viewPagerAdapter.PagerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewpager_layout, parent, false)
            return PagerViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.bindData(position)
        }

        override fun getItemCount(): Int {
            return 2
        }

        //	ViewHolder需要繼承RecycleView.ViewHolder
        class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val txv_M_viewpager_leftbottom: TextView =
                itemView.findViewById(R.id.txv_M_viewpager_leftbottom)
            private val txv_M_viewpager_leftcenter: TextView =
                itemView.findViewById(R.id.txv_M_viewpager_leftcenter)
            private val txv_M_viewpager_lefttop: TextView =
                itemView.findViewById(R.id.txv_M_viewpager_lefttop)
            private val txv_M_viewpager_rightbottom: TextView =
                itemView.findViewById(R.id.txv_M_viewpager_rightbottom)
            private val txv_M_viewpager_rightcenter: TextView =
                itemView.findViewById(R.id.txv_M_viewpager_rightcenter)
            private val txv_M_viewpager_righttop: TextView =
                itemView.findViewById(R.id.txv_M_viewpager_righttop)

            fun bindData(i: Int) {
                if (i == 0) {
                    txv_M_viewpager_leftbottom.text = "Budget balance"
                    txv_M_viewpager_leftcenter.text = "expenditure"
                    txv_M_viewpager_lefttop.text = "budget"
                    txv_M_viewpager_rightbottom.text = "$8787"
                    txv_M_viewpager_rightcenter.text = "$8787"
                    txv_M_viewpager_righttop.text = "$8787"
                } else {
                    txv_M_viewpager_leftbottom.text = "Budget balance"
                    txv_M_viewpager_leftcenter.text = "expenditure"
                    txv_M_viewpager_lefttop.text = "budget"
                    txv_M_viewpager_rightbottom.text = "$8787"
                    txv_M_viewpager_rightcenter.text = "$8787"
                    txv_M_viewpager_righttop.text = "$8787"
                }
            }
        }
    }


}
