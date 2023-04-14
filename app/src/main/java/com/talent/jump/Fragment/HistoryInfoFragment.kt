package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.talent.jump.Events.SingleHistoryEvent
import com.talent.jump.R
import com.talent.jump.data.ResponseHistory
import com.talent.jump.databinding.HistoryInfoFragmentBinding
import com.talent.jump.model.ApiTokenClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

class HistoryInfoFragment(var historydata: ResponseHistory): DialogFragment() {
    private var _binding: HistoryInfoFragmentBinding? = null
    private val binding get() = _binding!!
    var apiclient= ApiTokenClient()
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
        _binding = HistoryInfoFragmentBinding.inflate(inflater, container, false)
        apiclient.getSingleHistory(historydata.id)
        binding.orderNumContent.text=historydata.merchant_order_no

        val sdf = SimpleDateFormat("yy/MM/dd HH:mm:ss")
        val createTime = Date(historydata.merchant_order_time)
        binding.orderCreateTimeCotent.text=sdf.format(createTime)
        binding.traderNameContent.text=historydata.merchant_member_id

        val finishTime = Date(historydata.merchant_order_time)
        binding.orderOverTimeCotent.text=sdf.format(createTime)

        var statusName=""
        when(historydata.status)
        {
            1->{
                statusName="已建立"
            }

            2->{
                statusName="已建立"
            }

            3->{
                statusName="出款處理中"
            }

            4->{
                statusName="等待使用者入款"
            }

            5->{
                statusName="入款確認中"
            }

            6->{
                statusName="失敗"
            }

            7->{
                statusName="已過期"
            }
        }

        binding.statusContent.text=statusName
        binding.tradeFundContent.text="${historydata.amount}"
        binding.successConfirmContent.setOnClickListener {

            if(  binding.repoetResultImg.visibility!=View.VISIBLE)
            {
                binding.repoetResultImg.visibility=View.VISIBLE

            }
            else
            {
                binding.repoetResultImg.visibility=View.GONE
            }

        }

        binding.btnClose.setOnClickListener {

            dismiss()

        }

        return binding.root
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSingleEvent(event: SingleHistoryEvent?) {
        Log.d("history",event!!.GetTradeListResponse().data.response.report_image)
        val img=event!!.GetTradeListResponse().data.response.report_image
        val start=img.indexOf("_")+1
        val end=img.indexOf(".jp")
        binding.successConfirmContent.text=img.substring(start,end)
        binding.noteContent.text=historydata.description
        val options: RequestOptions = RequestOptions()
            .transform(MultiTransformation(CenterCrop(), CircleCrop()))
            .placeholder(R.drawable.history_search)
            .error(R.mipmap.ic_launcher)
            .priority(Priority.NORMAL)
        context?.let {
            Glide.with(it)
                .load(historydata.report_image)
                .into(binding.repoetResultImg)
        }
    }
}