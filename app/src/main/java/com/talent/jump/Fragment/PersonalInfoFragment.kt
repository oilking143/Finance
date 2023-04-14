package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talent.jump.Events.GetMeEvent
import com.talent.jump.Events.GetPaymentListEvent
import com.talent.jump.R
import com.talent.jump.data.DataPayMents
import com.talent.jump.databinding.PersonalInfoFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PersonalInfoFragment:BaseFragment() {

    private var _binding: PersonalInfoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PersonalInfoFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        apiclient.getMe()

        binding.cardTotalOwn.setOnClickListener {

            if(binding.cardListFrame.visibility==View.GONE)
            {
                binding.cardListFrame.visibility=View.VISIBLE
            }
            else
            {
                binding.cardListFrame.visibility=View.GONE
            }


        }

        binding.createPayment.setOnClickListener {

            val action = PersonalInfoFragmentDirections.actionPersonalInfoFragmentToCreatePaymentFragment()
            findNavController().navigate(action)

        }


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
    fun onGetMeEvent(event: GetMeEvent?) {

        apiclient.getPayMentList("0","-1","-1")
        binding.totalFundContent.text="${event!!.getMe().data.month_income}"
        binding.incomeFundTotalContent.text="${event!!.getMe().data.transaction_report.deposit_total_amount}"
        binding.incomeFundOrderContent.text="${event!!.getMe().data.transaction_report.deposit_count}"
        binding.outFundOrderContent.text="${event!!.getMe().data.transaction_report.withdraw_count}"
        binding.outFundTotalContent.text="${event!!.getMe().data.transaction_report.withdraw_total_amount}"
        binding.quotaContent.text="${event!!.getMe().data.quota}"
    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetPayMentListEvent(event: GetPaymentListEvent?) {

        binding.cardValue.text="${event!!.getPayment().data.response.size}"
        var adapter=cardListAdapter(event!!.getPayment().data)
        val linearLayoutManager= LinearLayoutManager(context)
        binding.cardListRecycler.layoutManager = linearLayoutManager
        binding.cardListRecycler.adapter=adapter
    }

    inner class cardListAdapter(var data:DataPayMents): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_card_list, parent, false)
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var holder=holder as ViewHolder
            holder.btn_look_up.setOnClickListener {

                val cardInfoDialog = CardInfoFragment(data.response[position])
                activity?.let { it1 -> cardInfoDialog.show(it1.supportFragmentManager,"") }

            }
            holder.card_name.text=data.response[position].bank_name

        }

        override fun getItemCount(): Int {
          return data.response.size
        }

        inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            val btn_look_up=itemView.findViewById<LinearLayout>(R.id.btn_look_up)
            val card_name=itemView.findViewById<TextView>(R.id.card_name)
        }


    }

}