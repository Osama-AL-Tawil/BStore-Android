package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.databinding.FragmentConfirmationBinding


class ConfirmationFragment : Fragment() {

    lateinit var binding: FragmentConfirmationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConfirmationBinding.inflate(inflater, container, false)

        (requireActivity() as Navigation2Activity).supportActionBar!!.hide()

        binding.btnBackHome.setOnClickListener {
            requireActivity().finish()
        }

        //on backPressed Clicked-------------------------------------------------------------------
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

        return binding.root
    }

}