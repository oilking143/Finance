package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.gson.JsonObject
import com.skydoves.powerspinner.*
import com.talent.jump.Events.CreatePaymentEvent
import com.talent.jump.databinding.CreatepaymentFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.talent.jump.R
import com.talent.jump.Utility.simpleArrayAdapter

class CreatePaymentFragment:BaseFragment() {
    private var _binding: CreatepaymentFragmentBinding? = null
    private val binding get() = _binding!!
    var selectedPosition=0
    var isWithDraw=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreatepaymentFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = simpleArrayAdapter(
            requireContext(),
            R.layout.channel_spinner_item,
            resources.getStringArray(R.array.channel_spinner_name)
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //載入介面卡
        binding.channelSpinner.adapter = adapter
        binding.channelSpinner.setSelection(
            resources.getStringArray(R.array.channel_spinner_name).size - 1,
            true
        );
        binding.channelSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedPosition = position
                    when(position)
                    {
                       0->{
                           binding.accountNameContent.visibility=View.VISIBLE
                           binding.bankNameContent.visibility=View.VISIBLE
                       }
                        else->{
                            binding.accountNameContent.visibility=View.GONE
                            binding.bankNameContent.visibility=View.GONE
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        binding.withdrawToggle.setOnCheckedChangeListener { _, isChecked ->
            isWithDraw = isChecked
        }



//        {
//            "account_name": "test_oil",
//            "account_number": "abc1234567",
//            "bank_name": "oil_UTSDTEST",
//            "create_at": 0,
//            "current_quota": 0,
//            "delete_at": 0,
//            "id": "oilking012345",
//            "is_withdraw": false,
//            "payment_channel_id": 2,
//            "trader_id": "tdcg4jov0sfqtiq60qeplg",
//            "update_at": 0
//        }

        binding.createBtn.setOnClickListener {
            val payMentObject=JsonObject()



            if("${binding.accountNameTxt.text}".isEmpty())
            {
               Toast.makeText(context,"請輸入戶名",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

             if("${binding.accountNumberTxt.text}".isEmpty())
            {
                Toast.makeText(context,"請輸入銀行帳號",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

             if("${binding.bankNameTxt.text}".isEmpty())
            {
                Toast.makeText(context,"請輸入銀行名稱",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            payMentObject.addProperty("account_name","${binding.accountNameTxt.text}")
            payMentObject.addProperty("account_number","${binding.accountNumberTxt.text}")
            payMentObject.addProperty("bank_name","${binding.bankNameTxt.text}")
            payMentObject.addProperty("is_withdraw",isWithDraw)
            payMentObject.addProperty("payment_channel_id",selectedPosition+1)
            payMentObject.addProperty("create_at",0)
            payMentObject.addProperty("current_quota",0)
            payMentObject.addProperty("delete_at",0)
            payMentObject.addProperty("id","")
            payMentObject.addProperty("trader_id","")
            payMentObject.addProperty("update_at",0)


                   apiclient.CretePayment(payMentObject)

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
    fun onCreatePaymentEvent(event: CreatePaymentEvent?) {

       if(event!!.getCreateResponse().status)
       {
           view?.findNavController()?.popBackStack()
       }

    }


}