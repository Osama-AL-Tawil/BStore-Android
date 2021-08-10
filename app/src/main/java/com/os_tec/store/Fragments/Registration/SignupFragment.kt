package com.os_tec.store.Fragments.Registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.Validation
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private val logKey="SignupFragment.OS"
    lateinit var binding:FragmentSignupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentSignupBinding.inflate(inflater,container,false)
        val validation= Validation()
        val activity=(activity as Navigation3Activity)
        activity.supportActionBar!!.show()


        //btn onClick
        binding.btnSignUp.setOnClickListener {
            val email=binding.edEmail.text.toString()
            val name=binding.edPassword.text.toString()
            val mobile=binding.edPhoneNumber.text.toString()
            val password=binding.edPassword.text.toString()

            //validate text
            if (validation.validateEmail(email)&& validation.validateEditText(name)
                &&validation.validateEditText(password)&& validation.validatePhone(mobile)){

                val params:HashMap<String,Any> =HashMap()
                params["name"]=name
                params["email"]=email
                params["mobile"]=mobile
                params["password"]=password

                //signup
                ApiRepository(requireActivity()).signup(logKey,params)

            }

        }




        binding.btnLogin.setOnClickListener {
           activity.startFragment(activity.loginFragment)
        }



        return binding.root
    }


}