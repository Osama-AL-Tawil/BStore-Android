package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.Validation
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentResetPasswordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResetPasswordFragment : Fragment() {
   lateinit var binding:FragmentResetPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentResetPasswordBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            val oldPassword=binding.edOldPassword.text.toString()
            val newPassword=binding.edNewPassword.text.toString()
            val confirmPassword=binding.edConfirmPassword.text.toString()
            val params=HashMap<String,Any>()

            if (Validation().validateEditText(oldPassword)
                &&Validation().validateEditText(newPassword)
                &&Validation().validateEditText(confirmPassword)){

                params["old_password"]=oldPassword
                params["password"]=newPassword
                params["password_confirmation"]=confirmPassword

                CoroutineScope(Dispatchers.Main).launch {
                    val status= ApiRepository(requireActivity()).resetPassword("ForgetPassword",params)

                    if (status){
                        requireActivity().finish()
                    }

                }
            }


        }
        return binding.root
    }

}