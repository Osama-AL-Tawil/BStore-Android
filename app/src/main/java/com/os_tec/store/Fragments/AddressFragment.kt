package com.os_tec.store.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Adapters.AddressAdapter
import com.os_tec.store.Model.AddressDataModel
import com.os_tec.store.databinding.FragmentAddressBinding

class AddressFragment : Fragment() {
    lateinit var binding: FragmentAddressBinding
    val addressArray=ArrayList<AddressDataModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(inflater, container, false)


        addressArray.add(AddressDataModel("street1","fe229","187","checked"))
        addressArray.add(AddressDataModel("street2","D229","178",""))
        addressArray.add(AddressDataModel("street3","G229","168",""))


        binding.recyclerAddress.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        val adapter= AddressAdapter(requireActivity(),addressArray)
        binding.recyclerAddress.adapter=adapter


        binding.btnContinueToPayment.setOnClickListener {
            (activity as Navigation2Activity).startFragment((activity as Navigation2Activity).paymentFragment)

        }
        binding.btnAddAddress.setOnClickListener {
            val i=Intent(requireContext(),Navigation3Activity::class.java)
            i.putExtra("nv","addAddress")
            startActivity(i)
        }


        //on backPressed Clicked-------------------------------------------------------------------
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as Navigation2Activity).startFragment((activity as Navigation2Activity).cartFragment)
                    //Toast.makeText(requireActivity(),"Address Fragment", Toast.LENGTH_SHORT).show()

                }
            })

        return binding.root
    }


}