package com.os_tec.store.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.Adapters.CheckOutAdapter
import com.os_tec.store.Adapters.MyOrderAdapter
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.ConnectivityReceiver
import com.os_tec.store.Fragments.Cart.CartViewModel
import com.os_tec.store.Model.CartModel
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentCheckoutBinding


class CheckoutFragment : Fragment() {
    lateinit var binding: FragmentCheckoutBinding
    lateinit var viewModel:CartViewModel
    private val cartIdsArray:ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        binding.recyclerCheckOut.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        val activity= (activity as Navigation2Activity)
        activity.backPressedNV=1

        //get arguments
        // val idsArray=requireArguments().getSerializable("idsArray")
        // val totalPrice=requireArguments().getInt("totalPrice")

        viewModel= CartViewModel()
        observe()//get data



        binding.btnBuy.setOnClickListener {

            if (ConnectivityReceiver().checkInternet(requireActivity())) {
                val params =HashMap<String,Any>()
                params["cart_ids"]= cartIdsArray
                ApiRepository(requireActivity()).payment("paymentFragment",params)// payment

                activity.startFragment(activity.confirmationFragment)
            }

        }




        return binding.root
    }




    private fun observe() {
        viewModel.getCart(requireActivity()).observe(viewLifecycleOwner, {
            var totalPrice = 0

            //GET AND SET Cart_ids in array
            for (index in it.data) {
                //get total price
                cartIdsArray.add(index.cart_id.toString()) //ids Array ->CartIdsArray
                totalPrice += index.price  //total price
            }

            binding.checkoutLayout.visibility = View.VISIBLE
            binding.textTotal.text = "$$totalPrice"
            binding.textSubtotal.text = "$$totalPrice"

            //set adapter and data
            setAdapter(it.data)

        })
    }



    private fun setAdapter(data: ArrayList<CartModel>) {
        //set recyclerView height WRAP_CONTENT  if array size ==1
        if (data.size == 1) {
            binding.recyclerCheckOut.layoutParams =LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        val adapterItems = CheckOutAdapter(requireActivity(), data)
        binding.recyclerCheckOut.adapter = adapterItems
    }

}