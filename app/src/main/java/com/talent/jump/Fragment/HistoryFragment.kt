package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talent.jump.Events.HistoryEvent
import com.talent.jump.Events.SingleHistoryEvent
import com.talent.jump.Events.SingleTradeJsonEvent
import com.talent.jump.R
import com.talent.jump.data.DataHistory
import com.talent.jump.databinding.HistoryFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment:BaseFragment() {

    private var _binding: HistoryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)


    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onHistoryEvent(event: HistoryEvent?) {

        var adapter=historyListAdapter(event!!.getHistory().data)
        val linearLayoutManager= LinearLayoutManager(context)
        binding.historyListRecycler.layoutManager = linearLayoutManager
        binding.historyListRecycler.adapter=adapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiclient.getHistory("","0","20")

    }

    inner class historyListAdapter(var data:DataHistory): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_history_list, parent, false)
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var holder=holder as ViewHolder

           data.response[position].comment
            when(data.response[position].status)
            {
                1->  holder.history_name.text="已建立"
                2-> holder.history_name.text = "已指派"
               3-> holder.history_name.text ="出款處理中"
               4-> holder.history_name.text ="入款處理確認中"
                5-> holder.history_name.text ="成功"
                6-> holder.history_name.text ="失敗"
                7-> holder.history_name.text ="已過期"
            }
            val sdf = SimpleDateFormat("yy/MM/dd HH:mm:ss")
            val historyDate = Date(data.response[position].create_at)
            holder.history_date.text=sdf.format(historyDate)

            holder.btn_history_list.setOnClickListener {

                val historyInfoDialog = HistoryInfoFragment(data.response[position])
                activity?.let { it1 -> historyInfoDialog.show(it1.supportFragmentManager,"") }
            }


        }

        override fun getItemCount(): Int {
            return data.response.size
        }

        inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            val btn_history_list=itemView.findViewById<ConstraintLayout>(R.id.btn_history_list)
            val history_date=itemView.findViewById<TextView>(R.id.history_date)
            val history_name=itemView.findViewById<TextView>(R.id.history_name)
            }


    }

}