package com.homi.ppbank20

import Record
import User
import android.graphics.Color
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
import kotlinx.android.synthetic.main.fragment_account.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private val TAG = AccountFragment::class.java.simpleName

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getSerializable("user") as? User
        Log.d(TAG, user.toString())
        account_balance.text=user?.money
        btn_account_right.setOnClickListener {
            findNavController().navigate(
                R.id.mobliepayFragment,
                arguments
            )
        }
        if (user != null) {
            incomeState()
            btn_income.setOnClickListener { incomeState() }
            btn_expense.setOnClickListener { expenseState() }
        }

    }

    fun incomeState() {
        val bundle = Bundle()
        bundle.putSerializable("user", user)
        bundle.putString("type","income")
        btn_more.setOnClickListener { findNavController().navigate(R.id.recordFragment, bundle) }
        //設定圓餅圖右方敘述
        tbx_pie_detail01.setText("prize")
        tbx_pie_detail02.setText("apply")
        tbx_pie_detail03.setText("task")
        tbx_pie_detail04.setText("pocket money")
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
        pie_chart.setPieChartData(pieChartData)

        val recordAdapter = RecordAdapter()
        recordAdapter.setList(user?.incomeRecords)
        account_RecyclerView.layoutManager=LinearLayoutManager(context)
        account_RecyclerView.adapter = RecordAdapter()
    }

    fun expenseState() {
        val bundle = Bundle()
        bundle.putSerializable("user", user)
        bundle.putString("type","expense")
        btn_more.setOnClickListener { findNavController().navigate(R.id.recordFragment, bundle) }
        //設定圓餅圖右方敘述
        tbx_pie_detail01.setText("food")
        tbx_pie_detail02.setText("daily supplies")
        tbx_pie_detail03.setText("entertainment")
        tbx_pie_detail04.setText("other")
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
        pie_chart.setPieChartData(pieChartData)

        val recordAdapter = RecordAdapter()
        recordAdapter.setList(user?.expenseRecords)
        Log.d(TAG,user?.expenseRecords.toString())
        account_RecyclerView.layoutManager=LinearLayoutManager(context)
        account_RecyclerView.adapter = RecordAdapter()
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
            val date: TextView = itemView.findViewById(R.id.txv_record_date)
            val name: TextView = itemView.findViewById(R.id.txv_record_name)
            val money: TextView = itemView.findViewById(R.id.txv_record_money)

            fun bindData(i: Int) {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val time = simpleDateFormat.parse(data.get(i).date)
                Log.d(TAG,data.get(i).toString())
                date.text = "${time.month}/${time.day}"
                name.text = "${data.get(i).uid} ${data.get(i).name}"
                money.text = "$${data.get(i).money}"
            }
        }
    }
}
