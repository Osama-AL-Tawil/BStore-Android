package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.Validation
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentForgetPasswordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ForgetPasswordFragment : Fragment() {
lateinit var binding:FragmentForgetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentForgetPasswordBinding.inflate(inflater, container, false)
        val activity=(activity as Navigation3Activity)

        binding.btnLogin.setOnClickListener {
            val email=binding.edEmail.text.toString()
            val params=HashMap<String,Any>()

            if (Validation().validateEmail(email)){
               params["email"]=email

                CoroutineScope(Dispatchers.Main).launch {
                   val status= ApiRepository(requireActivity()).forgetPassword("ForgetPassword",params)

                    if (status){
                        activity.startFragment(activity.loginFragment)
                    }

                }
            }


        }



        return binding.root
    }

}