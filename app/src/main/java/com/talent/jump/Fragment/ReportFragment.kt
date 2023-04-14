package com.talent.jump.Fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.talent.jump.R
import com.talent.jump.databinding.ReportFragmentBinding

class ReportFragment:BaseFragment() {
    private var _binding: ReportFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ReportFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        object : CountDownTimer(3000, 1000) {

            override fun onFinish() {
                view?.findNavController()?.popBackStack(R.id.mainFragment,false)

            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
    }


}