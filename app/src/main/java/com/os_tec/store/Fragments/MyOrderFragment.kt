package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Adapters.MyOrderAdapter
import com.os_tec.store.Model.CartDataModel
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentMyOrderBinding


class MyOrderFragment : Fragment() {
lateinit var binding: FragmentMyOrderBinding
    val orderArrayList=ArrayList<CartDataModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentMyOrderBinding.inflate(inflater,container,false)

        orderArrayList.add(CartDataModel("Female t- shirt","Defacto","$37.00",2, R.drawable.img3))
        orderArrayList.add(CartDataModel("Female t- shirt","Niek","$78.00",1, R.drawable.img1))
        orderArrayList.add(CartDataModel("Woman Shirt","Adidas","$50.00",1, R.drawable.img2))
        orderArrayList.add(CartDataModel("Female t- shirt","Defacto","$37.00",3, R.drawable.img3))
        orderArrayList.add(CartDataModel("Female t- shirt","Niek","$78.00",1, R.drawable.img2))
        orderArrayList.add(CartDataModel("Woman Shirt","Adidas","$50.00",1, R.drawable.img1))

        binding.rcMyOrder.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        val adapterItems= MyOrderAdapter(requireActivity(),orderArrayList)
        binding.rcMyOrder.adapter=adapterItems

        return binding.root
    }

}