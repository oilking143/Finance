package com.talent.jump.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.talent.jump.R
import com.talent.jump.databinding.TradeResultFragmentBinding

class TradeResultFragment:BaseFragment() {

    private var _binding: TradeResultFragmentBinding? = null
    private val binding get() = _binding!!
    val args by navArgs<TradeResultFragmentArgs>()
    var statusCode=""
    var statusMsg=""
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

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       if (statusCode.contains("失敗"))
       {
           binding.checkboxFail.setBackgroundResource(R.drawable.check_box_selected)
           binding.failHint.visibility=View.VISIBLE
       }
        else
       {
           binding.checkboxSuccess.setBackgroundResource(R.drawable.check_box_selected)
       }

        binding.btnSendResult.setOnClickListener {
            view?.findNavController()?.navigate(R.id.mainFragment)
        }
    }


    override fun onStart() {
        super.onStart()
//        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
//        EventBus.getDefault().unregister(this)
    }

}