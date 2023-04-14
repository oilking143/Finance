package com.talent.jump.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.talent.jump.R
import com.talent.jump.databinding.UserInitialFragmentBinding

class UserInitialFragment:BaseFragment() {
    private var _binding: UserInitialFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UserInitialFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPersonalInfo.setOnClickListener {

            findNavController().navigate(R.id.action_userInitialFragment_to_personalInfoFragment)
        }


        binding.btnPersonalHistory.setOnClickListener {

            findNavController().navigate(R.id.action_userInitialFragment_to_historyFragment)
        }

    }



}