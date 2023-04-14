package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aotter.aotter_suprone_android.utils.RealPathUtil
import com.bumptech.glide.Glide
import com.talent.jump.Events.*
import com.talent.jump.R
import com.talent.jump.Utility.PickSinglePhotoContract
import com.talent.jump.Utility.simpleArrayAdapter
import com.talent.jump.databinding.TradeResultFragmentBinding
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File


class TradeResultFragment:BaseFragment() {

    private var _binding: TradeResultFragmentBinding? = null
    private val binding get() = _binding!!
    val args by navArgs<TradeResultFragmentArgs>()
    var statusCode=""
    var statusMsg=""
    var paymentCode=""
    var isSuccess=""
    var treadCode=""
    var treadType=0
    var filePath=""
    lateinit var photoBit:Bitmap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TradeResultFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        statusCode = args.statesCode
        statusMsg = args.statesMsg
        paymentCode=args.paymentCode
        treadCode=args.tradeCode
        treadType=args.type
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       if (statusCode.contains("失敗"))
       {
           binding.checkboxFail.isChecked=true
           binding.failHint.visibility=View.VISIBLE
           if(treadType==1)
           {
               binding.btnSendName.text="回首頁"
               binding.tradeTitle.text="接單失敗"
               binding.failHint.visibility=View.GONE
           }
           isSuccess="2"
       }
        else
       {
           binding.checkboxSuccess.isChecked=true
           isSuccess="1"
       }

        if(treadType==2)
        {
            apiclient.getPayMentList("0","-1","-1")
            binding.checkboxSuccess.isEnabled = true
            binding.checkboxFail.isEnabled = true

            binding.checkboxSuccess.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked)
                {
                    binding.checkboxFail.isChecked=false
                    isSuccess="1"
                }
            }

            binding.checkboxFail.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked)
                {
                    binding.checkboxSuccess.isChecked=false
                    isSuccess="2"
                }
            }

        }
        binding.btnSendResult.setOnClickListener {

            if(treadType==1)
            {
                if (statusCode.contains("失敗"))
                {
                    view?.findNavController()?.popBackStack(R.id.mainFragment,false)
                }
                else
                {
                    var uploadFile=File(filePath)

                        binding.sendOutProgress.visibility=View.VISIBLE
                        binding.btnSendResult.setBackgroundResource(R.drawable.order_disable_btn_frame)
                        val requestFile = uploadFile.asRequestBody("image/jpeg".toMediaType())
                        val paymentCode: RequestBody =
                            paymentCode.toRequestBody("text/plain".toMediaTypeOrNull())
                        val isSuccess: RequestBody =
                            isSuccess.toRequestBody("text/plain".toMediaTypeOrNull())
                        val statusMsg: RequestBody =
                            "${binding.failTextBox.text}".toRequestBody("text/plain".toMediaTypeOrNull())
                        apiclient.ReportTrade(treadCode,statusMsg,isSuccess,paymentCode)
                }

            }
            else
            {
                    binding.sendOutProgress.visibility=View.VISIBLE
                    binding.btnSendResult.setBackgroundResource(R.drawable.order_disable_btn_frame)
                    val paymentCode: RequestBody =
                        paymentCode.toRequestBody("text/plain".toMediaTypeOrNull())
                    val isSuccess: RequestBody =
                        isSuccess.toRequestBody("text/plain".toMediaTypeOrNull())
                    val statusMsg: RequestBody =
                        "${binding.failTextBox.text}".toRequestBody("text/plain".toMediaTypeOrNull())
                    apiclient.ReportTrade(treadCode,statusMsg,isSuccess,paymentCode)

            }


        }

        when(statusMsg)
        {
            "1"-> binding.failTextBox.setText("已建立")
            "2"-> binding.failTextBox.setText("已指派")
            "3"-> binding.failTextBox.setText("出款處理中")
            "4"-> binding.failTextBox.setText("入款處理確認中")
            "5"-> binding.failTextBox.setText("成功")
            "6"-> binding.failTextBox.setText("失敗")
            "7"-> binding.failTextBox.setText("已過期")
        }

    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReportTradeEvent(event: ReportTradeEvent?) {

        val action = TradeResultFragmentDirections.actionTradeResultFragmentToReportFragment()

        findNavController().navigate(action)
    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReportFailEvent(event: ReportFailEvent?) {

        AlertDialog.Builder(requireContext())
            .setMessage(event!!.getMsg().errorMsg)
            .setTitle(event!!.getMsg().errorCode)
            .setNegativeButton("了解"
            ) { _, _ ->   view?.findNavController()?.popBackStack(R.id.mainFragment,false) }
            .show()
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
                    paymentCode=listData[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

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




}