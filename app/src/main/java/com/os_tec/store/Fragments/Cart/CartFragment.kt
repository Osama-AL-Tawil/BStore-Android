package com.os_tec.store.Fragments.Cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.Adapters.CartAdapter
import com.os_tec.store.Classes.ConnectivityReceiver
import com.os_tec.store.Classes.RecyclerViewDecoration
import com.os_tec.store.Model.CartModel
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    lateinit var viewModel: CartViewModel
    var arraySize=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.rcCart.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        //(activity as Navigation2Activity).backPressedNV=1


        viewModel= CartViewModel()
        observeData()//get data and set adapter


        binding.btnContinue.setOnClickListener {
            if (ConnectivityReceiver().checkInternet(requireActivity())&&arraySize>=1){
                val activity=(activity as Navigation2Activity)
                activity.startFragment(activity.checkOutFragment)
            }else{
                Toast.makeText(requireContext(),resources.getString(R.string.no_products_cart),Toast.LENGTH_SHORT).show()
            }
        }



        return binding.root
    }


    private fun observeData() {
        viewModel.getCart(requireActivity()).observe(viewLifecycleOwner, {
            setAdapter(it.data)
            arraySize=it.data.size
            if (arraySize<1){
                binding.messageLayout.visibility=View.VISIBLE
            }
        })
    }


    private fun setAdapter(data: ArrayList<CartModel>) {
        val adapterItems = CartAdapter(requireActivity(), data)
        binding.rcCart.addItemDecoration(RecyclerViewDecoration(35, 35, 20, 20))
        binding.rcCart.adapter = adapterItems
    }

}