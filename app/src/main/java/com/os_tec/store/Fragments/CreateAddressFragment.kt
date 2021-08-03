package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.os_tec.store.databinding.FragmentCreateAddressBinding

class CreateAddressFragment : Fragment() {
lateinit var binding:FragmentCreateAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateAddressBinding.inflate(inflater, container, false)
        binding.btnCreateAddress.setOnClickListener {
            requireActivity().finish()
        }
        return binding.root
    }



}