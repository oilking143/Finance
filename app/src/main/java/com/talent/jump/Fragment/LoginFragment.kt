package com.talent.jump.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.gson.JsonObject
import com.talent.jump.Activity.MainActivity
import com.talent.jump.Events.LoginEvent
import com.talent.jump.Events.errorEvent
import com.talent.jump.GlobalData
import com.talent.jump.databinding.LoginFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoginFragment: BaseFragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

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

        binding.loginRequest.setOnClickListener {
            binding.loginProgress.visibility=View.VISIBLE
            var LoginJson=JsonObject()

            LoginJson.addProperty("username","${binding.userEdit.text}")
            LoginJson.addProperty("password","${binding.pwdEdit.text}")
            LoginJson.addProperty("grant_type","password")
            LoginJson.addProperty("refresh_token","")

            apiloginclient.Login(LoginJson)

        }


    }

    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event: LoginEvent?) {

        if(event!!.GetLoginData().status)
        {
            val data=event!!.GetLoginData().data
            GlobalData.loginToken=data
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

    }


    @SuppressLint("SuspiciousIndentation")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onErrorEvent(event: errorEvent?) {

        binding.loginProgress.visibility=View.GONE
        AlertDialog.Builder(requireContext())
        .setMessage(event!!.getMsg().errorMsg)
        .setTitle(event!!.getMsg().errorCode)
        .setNegativeButton("了解")
        { _, _ -> requireActivity().supportFragmentManager.popBackStack() }
        .show()

    }


    override fun onDestroy() {
        super.onDestroy()
    }
}