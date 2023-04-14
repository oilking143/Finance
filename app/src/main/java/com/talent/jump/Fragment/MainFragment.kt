package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talent.jump.Events.*
import com.talent.jump.R
import com.talent.jump.data.TradeListData
import com.talent.jump.databinding.MainFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

class MainFragment: BaseFragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var timer: CountDownTimer
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

        apiclient.getConduct()
        binding.swipeRefresh.setOnRefreshListener {
            apiclient.getTeadeList("20","0","") }

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



        binding.mainProgress.visibility=View.GONE
        var adapter=TradeListAdapter(event!!.GetTradeListResponse().data)
        val linearLayoutManager= LinearLayoutManager(context)
        binding.traderListRecycler.layoutManager = linearLayoutManager
        binding.traderListRecycler.adapter=adapter
        binding.swipeRefresh.isRefreshing = false
        timer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {


            }

            override fun onFinish() {

                apiclient.getTeadeList("20","0","")

            }
        }.start()

    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadEvent(event: LoadEvent?) {
        val action = MainFragmentDirections.actionMainFragmentToLoadingFragment(event!!.id,event!!.paymentID
            ,event!!.tradeType)
        findNavController().navigate(action)

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSingleEvent(event: SingleTradeJsonEvent?) {

        val orderInfoDialog = OrderInfoFragment(event!!.GetTradeListResponse())
        activity?.let { it1 -> orderInfoDialog.show(it1.supportFragmentManager,"") }

    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConductEvent(event: ConductEvent?) {
        timer = object: CountDownTimer(150000, 1000) {
            override fun onTick(millisUntilFinished: Long) {


            }

            override fun onFinish() {

                apiclient.getConduct()

            }
        }.start()
        try {
            if(event!!.getConduct()
                    .get("data").asJsonObject
                    .get("response").asJsonObject.get("id").asString.isNotEmpty())
            {

                binding.swipeRefresh.visibility=View.GONE
                binding.mainProgress.visibility=View.GONE
                binding.accessingFrame.visibility=View.VISIBLE
                binding.accessingContent.text=event.getConduct().get("data").asJsonObject.get("response").asJsonObject
                    .get("merchant_order_no").asString
                binding.tradeFundContent.text=event.getConduct().get("data").asJsonObject.get("response").asJsonObject
                    .get("amount").asString
                val type=event.getConduct().get("data").asJsonObject.get("response").asJsonObject
                    .get("type").asInt

                if(type==2)
                {
                    binding.fundType.text="出款"
                    binding.fundType.setTextColor(resources.getColor(R.color.white,null))
                    binding.fundType.background=resources.getDrawable(R.drawable.trade_type_label_frame_out,null)

                }
                else
                {
                    binding.fundType.text="入款"
                    binding.fundType.setTextColor(resources.getColor(R.color.black,null))
                    binding.fundType.background=resources.getDrawable(R.drawable.trade_type_label_frame,null)
                }

                binding.accessingFrame.setOnClickListener {
                    val response=event.getConduct().get("data").asJsonObject.get("response").asJsonObject
                    if(response.get("type").asInt==2)
                    {
                        val action = MainFragmentDirections.actionMainFragmentToTradeResultFragment("",response.get("id").asString,"1",response.get("status").asString
                            ,response.get("type").asInt)

                        findNavController().navigate(action)
                    }
                    else
                    {

                        val action = MainFragmentDirections.actionMainFragmentToTradeResultFragment("",response.get("id").asString,"1",response.get("status").asString
                            ,response.get("type").asInt)

                        findNavController().navigate(action)
                    }



                }

            }
        }catch (e:Exception)
        {
            apiclient.getTeadeList("20","0","")
        }


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
            val channel_array=resources.getStringArray(R.array.channel_name)
            holder.payment_channel.text=channel_array[data.response[position].payment_channel_id]

            holder.trade_fund_content.text="${data.response[position].amount}"

            holder.check_order.setOnClickListener {

                apiclient.getSingleJsonTreade(data.response[position].id)
            }

            if(data.response[position].type==1)
            {
                holder.fund_type.text="入款"
                holder.fund_type.setTextColor(resources.getColor(R.color.black,null))
                holder.fund_type.background=resources.getDrawable(R.drawable.trade_type_label_frame,null)
            }
            else
            {
                holder.fund_type.text="出款"
                holder.fund_type.setTextColor(resources.getColor(R.color.white,null))
                holder.fund_type.background=resources.getDrawable(R.drawable.trade_type_label_frame_out,null)


            }

        }

        override fun getItemCount(): Int {
          return data.response.size
        }

        inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            val create_time=itemView.findViewById<TextView>(R.id.create_time)
            val payment_channel=itemView.findViewById<TextView>(R.id.payment_channel)
            val trade_fund_content=itemView.findViewById<TextView>(R.id.trade_fund_content)
            val check_order=itemView.findViewById<LinearLayout>(R.id.check_order)
            val fund_type=itemView.findViewById<TextView>(R.id.fund_type)
        }

    }

}