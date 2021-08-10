package com.os_tec.store.Fragments.Registration

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.Validation
import com.os_tec.store.databinding.FragmentActivationCodeBinding


class ActivationCodeFragment : Fragment() {
    lateinit var binding:FragmentActivationCodeBinding
    private val logKey="ActivationFragment.OS"
    private var code=""
    private var email=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentActivationCodeBinding.inflate(inflater,container,false)
        (activity as Navigation3Activity).supportActionBar!!.hide() //hide ActionBar
         email=requireArguments().getString("email").toString()//get Email


        //show keyboard
        val otpView=binding.otpView
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        otpView.requestFocus()



        //get and set codeText
        otpView.otpListener = (object : OTPListener {
            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {
                code=otp
                //Toast.makeText(requireContext(), otp, Toast.LENGTH_LONG).show()
            }
        })



        binding.btnVerify.setOnClickListener {

            if (Validation().validateEditText(code)){//validate If code not null
                //set data
                val params = HashMap<String, Any>()
                params["email"] = email
                params["code"] = code

                //send Activation Code
                ApiRepository(requireActivity()).sendActivationCode(logKey,params)
            }

        }



        return binding.root

    }




}