package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.os_tec.store.Classes.SharedPreferences
import com.os_tec.store.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
   lateinit var binding:FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        val data=SharedPreferences().getUserData().data
        binding.edEmail.setText(data.email)
        binding.edName.setText(data.email)
        binding.edPhoneNumber.setText(data.mobile)
        binding.edCity.setText(data.address)

        return binding.root
    }

}