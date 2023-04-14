package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import com.talent.jump.Events.GetPaymentListEvent
import com.talent.jump.Events.LoadEvent
import com.talent.jump.R
import com.talent.jump.Utility.simpleArrayAdapter
import com.talent.jump.data.SingleTradeResponse
import com.talent.jump.databinding.OrderDialogFragmentBinding
import com.talent.jump.model.ApiTokenClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

class OrderInfoFragment(var data: SingleTradeResponse): DialogFragment() {

    private var _binding: OrderDialogFragmentBinding? = null
    private val binding get() = _binding!!
    var apiclient=ApiTokenClient()
    var paymentID=""
    override fun onStart() {
        super.onStart()

        val win: Window? = dialog!!.window

        win!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params: WindowManager.LayoutParams = win.attributes
        params.gravity = Gravity.CENTER
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        win.attributes = params
        EventBus.getDefault().register(this)

    }


    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OrderDialogFragmentBinding.inflate(inflater, container, false)


        binding.orderNumberContent.text=data.data.response.merchant_order_no

        binding.bankNumContent.text= data.data.transaction_payment.account_number
        binding.bankNameContent.text= data.data.transaction_payment.bank_name
        binding.bankAccountContent.text= data.data.transaction_payment.account_name

   if(data.data.response.type==1)
   {
       apiclient.getPayMentList("0","-1","-1")
   }



        val sdf = SimpleDateFormat("yy/MM/dd HH:mm:ss")

        val startDate = data.data.response.create_at
        binding.outTimeContent.text=sdf.format(startDate)

        val endDate =data.data.response.expire_at
        binding.deadTimeContent.text=sdf.format(endDate)

        binding.confirmOrder.setOnClickListener {

            Log.d("event_Data",data.data.response.id+"_"+paymentID+"_"+data.data.response.type)
            EventBus.getDefault().post(LoadEvent(data.data.response.id
            ,paymentID
            ,data.data.response.type))
            dismiss()

        }


        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetPayMentListEvent(event: GetPaymentListEvent?) {
        binding.paymentSelector.visibility=View.VISIBLE
        var listData=event!!.getPayment().data.response


        val payments=arrayOfNulls<String>(listData.size+1)
        for(i in listData.indices)
        {
            payments[i] = listData[i].account_name
        }
        val adapter = simpleArrayAdapter(
            requireContext(),
            R.layout.channel_spinner_item,
            payments
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.paymentSpinner.adapter = adapter

        binding.paymentSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                  paymentID=listData[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
    }

}