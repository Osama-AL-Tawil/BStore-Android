package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Adapters.SettingAdapter
import com.os_tec.store.Model.SettingDataModel
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
  lateinit var binding:FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding= FragmentSettingBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        //setting item arrayList
        val settingArray=ArrayList<SettingDataModel>()
        settingArray.add(SettingDataModel(resources.getString(R.string.reset_password),R.drawable.ic_lock,"resetPassword"))

        //set adapter
        binding.recyclerView.adapter=SettingAdapter(requireActivity(),settingArray)

        return binding.root

    }


}