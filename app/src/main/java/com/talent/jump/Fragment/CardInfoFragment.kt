package com.talent.jump.Fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.talent.jump.data.DataPayMents
import com.talent.jump.data.ResponsePayments
import com.talent.jump.databinding.CardInfoFragmentBinding

class CardInfoFragment(var data: ResponsePayments): DialogFragment() {
    private var _binding: CardInfoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()

        val win: Window? = dialog!!.window

        win!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params: WindowManager.LayoutParams = win.attributes
        params.gravity = Gravity.CENTER
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        win.attributes = params
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CardInfoFragmentBinding.inflate(inflater, container, false)

        binding.accountNameContent.text=data.account_name
        binding.bankAccountContent.text=data.account_number
        binding.fundLimitContent.text="${data.current_quota}"
        binding.bankNameContent.text=data.bank_name


        binding.btnClose.setOnClickListener {

            dismiss()

        }

        return binding.root
    }
}