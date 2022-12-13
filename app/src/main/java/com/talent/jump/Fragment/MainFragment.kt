package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talent.jump.Events.*
import com.talent.jump.GlobalData
import com.talent.jump.R
import com.talent.jump.data.ResponseTradeList
import com.talent.jump.data.TradeListData
import com.talent.jump.data.TradeListResponse
import com.talent.jump.databinding.MainFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

class MainFragment: BaseFragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

     apiclient.getTeadeList("20","0","")

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
    fun onTradeListEvent(event: TradeListEvent?) {


        Log.d("TradeList",event!!.GetTradeListResponse().status.toString())

        var adapter=TradeListAdapter(event!!.GetTradeListResponse().data)
        val linearLayoutManager= LinearLayoutManager(context)
        binding.traderListRecycler.layoutManager = linearLayoutManager
        binding.traderListRecycler.adapter=adapter
    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTestEvent(event: testEvent?) {
        findNavController().navigate(R.id.action_mainFragment_to_loadingFragment)

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onErrorEvent(event: errorEvent?) {
        AlertDialog.Builder(requireContext())
            .setMessage(event!!.getMsg().errorMsg)
            .setTitle(event!!.getMsg().errorCode)
            .setNegativeButton("了解"
            ) { _, _ -> requireActivity().supportFragmentManager.popBackStack() }
            .show()
    }

    inner class TradeListAdapter(var data: TradeListData): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_teade_list, parent, false)
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var holder=holder as ViewHolder

            val sdf = SimpleDateFormat("yy/MM/dd HH:mm:ss")
            val netDate = Date(data.response[position].create_at)

            holder.create_time.text= sdf.format(netDate)
            holder.level_content.text= data.member_levels.mmcdn05tgsfqthmteppetg

            when(data.response[position].payment_channel_id)
            {
                1->{holder.payment_channel.text=data.payment_channels.first}
                2->{holder.payment_channel.text=data.payment_channels.sec}
                3->{holder.payment_channel.text=data.payment_channels.third}
            }

            holder.trade_fund_content.text="${data.response[position].amount}"

            holder.check_order.setOnClickListener {

                val orderInfoDialog = OrderInfoFragment()
                activity?.let { it1 -> orderInfoDialog.show(it1.supportFragmentManager,"") }
            }

        }

        override fun getItemCount(): Int {
          return data.response.size
        }

        inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            val create_time=itemView.findViewById<TextView>(R.id.create_time)
            val payment_channel=itemView.findViewById<TextView>(R.id.payment_channel)
            val level_content=itemView.findViewById<TextView>(R.id.level_content)
            val trade_fund_content=itemView.findViewById<TextView>(R.id.trade_fund_content)
            val check_order=itemView.findViewById<LinearLayout>(R.id.check_order)
        }

    }

}