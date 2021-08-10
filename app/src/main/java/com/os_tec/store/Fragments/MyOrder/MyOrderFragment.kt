package com.os_tec.store.Fragments.MyOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Adapters.MyOrderAdapter
import com.os_tec.store.Model.MyOrderModel
import com.os_tec.store.databinding.FragmentMyOrderBinding


class MyOrderFragment : Fragment() {
lateinit var binding: FragmentMyOrderBinding
lateinit var viewModel:MyOrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentMyOrderBinding.inflate(inflater,container,false)

        binding.rcMyOrder.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        viewModel= MyOrderViewModel()
        observe()//getData


        return binding.root
    }

    fun observe() {
        viewModel.getMyOrder(requireActivity()).observe(viewLifecycleOwner, {
            setAdapter(it.data)
        })
    }

    fun setAdapter(data:ArrayList<MyOrderModel>){
        val adapterItems= MyOrderAdapter(requireActivity(),data)
        binding.rcMyOrder.adapter=adapterItems
    }

}