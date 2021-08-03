package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.Adapters.MyOrderAdapter
import com.os_tec.store.Model.CartDataModel
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentCheckoutBinding


class CheckoutFragment : Fragment() {
    lateinit var binding: FragmentCheckoutBinding
    val orderArrayList = ArrayList<CartDataModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        orderArrayList.add(CartDataModel("Female t- shirt", "Nike", "$78.00", 1, R.drawable.img2))
        orderArrayList.add(CartDataModel("Woman Shirt", "Adidas", "$50.00", 1, R.drawable.img1))

        binding.recyclerCheckOut.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )

        val adapterItems = MyOrderAdapter(requireActivity(), orderArrayList)
        binding.recyclerCheckOut.adapter = adapterItems


        binding.btnBuy.setOnClickListener {
            (activity as Navigation2Activity).startFragment((activity as Navigation2Activity).confirmationFragment)

        }



        //on backPressed Clicked-------------------------------------------------------------------
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as Navigation2Activity).startFragment((activity as Navigation2Activity).paymentFragment)
                    //Toast.makeText(requireActivity(),"Checkout Fragment", Toast.LENGTH_SHORT).show()

                }
            })


        return binding.root
    }


}