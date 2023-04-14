package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.talent.jump.Events.*
import com.talent.jump.R
import com.talent.jump.data.ResponsePayments
import com.talent.jump.databinding.LoadingFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoadingFragment:BaseFragment() {
    private var _binding: LoadingFragmentBinding? = null
    private val binding get() = _binding!!
    var trade_id=""
    var trade_type=0
    var payment_id=""
    val args by navArgs<LoadingFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoadingFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        trade_id=args.id
        payment_id=args.paymentId
        trade_type=args.type
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiclient.TakeTread(trade_id,payment_id)

    }


    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTakeTradeEvent(event: TakeTradeEvent?) {

        apiclient.getConduct()

    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConductEvent(event: ConductEvent?) {

        view?.findNavController()?.popBackStack()

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFailEvent(event: TradeFailEvent?) {
        val state_code=event!!.getMsg().errorCode
        val states_msg=event!!.getMsg().errorMsg
        val action = LoadingFragmentDirections.actionLoadingFragmentToTradeResultFragment(payment_id,trade_id,state_code,states_msg,trade_type)

        findNavController().navigate(action)

    }



}