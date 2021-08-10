package com.os_tec.store.Fragments.Registration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.Validation
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val logKey="LoginFragment.OS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val validation=Validation()
        val activity=(activity as Navigation3Activity)
        activity.supportActionBar!!.show()




        //btn onClick
        binding.btnLogin.setOnClickListener {
            val email=binding.edEmail.text.toString()
            val password=binding.edPassword.text.toString()
            //validate text
            if (validation.validateEmail(email)&& validation.validateEditText(password)){
                //send login request
                val params=HashMap<String,Any>()
                params["email"] = email
                params["password"]=password

                //login
              ApiRepository(requireActivity()).login(logKey,params)

            }

        }

        binding.btnForgetPassword.setOnClickListener {
          activity.startFragment(activity.ForgetPasswordFragment)
        }





        binding.btnSignUp.setOnClickListener {
            activity.startFragment(activity.signupFragment)
        }



        return binding.root
    }


fun toastAndLog(log:String,toast:String){
    Toast.makeText(activity,toast,Toast.LENGTH_LONG).show()
    Log.e(logKey,log)

}

}