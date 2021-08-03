package com.os_tec.store.Fragments.Registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentWelcomeBinding.inflate(inflater,container,false)

        val activity=(requireActivity() as Navigation3Activity)
        activity.supportActionBar!!.hide()


        binding.btnSignUp.setOnClickListener {
            activity.startFragment(activity.signupFragment)

        }
        binding.btnLogin.setOnClickListener {
            activity.startFragment(activity.loginFragment)

        }
        return binding.root

    }

}

