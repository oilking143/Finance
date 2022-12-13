package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.talent.jump.Events.TakeTradeEvent
import com.talent.jump.Events.errorEvent
import com.talent.jump.R
import com.talent.jump.databinding.LoadingFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ReportFragment:BaseFragment() {
    private var _binding: LoadingFragmentBinding? = null
    private val binding get() = _binding!!

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

        object : CountDownTimer(3000, 1000) {

            override fun onFinish() {
                findNavController().navigate(R.id.action_tradeResultFragment_to_reportFragment)
            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTakeTradeEvent(event: TakeTradeEvent?) {
        findNavController().navigate(R.id.action_mainFragment_to_loadingFragment)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onErrorEvent(event: errorEvent?) {
        val state_code=event!!.getMsg().errorCode
        val states_msg=event!!.getMsg().errorMsg
        val action = LoadingFragmentDirections.actionLoadingFragmentToTradeResultFragment(state_code,states_msg)

        findNavController().navigate(action)

    }



}