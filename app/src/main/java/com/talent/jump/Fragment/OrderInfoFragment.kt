package com.talent.jump.Fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.talent.jump.Events.testEvent
import com.talent.jump.databinding.OrderDialogFragmentBinding
import com.talent.jump.model.ApiTokenClient
import org.greenrobot.eventbus.EventBus

class OrderInfoFragment: DialogFragment() {

    private var _binding: OrderDialogFragmentBinding? = null
    private val binding get() = _binding!!
    var apiclient=ApiTokenClient()

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
        _binding = OrderDialogFragmentBinding.inflate(inflater, container, false)


        binding.confirmOrder.setOnClickListener {

            EventBus.getDefault().post(testEvent())
            dismiss()

        }

        return binding.root
    }

}