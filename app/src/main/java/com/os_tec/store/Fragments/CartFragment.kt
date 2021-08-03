package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.Adapters.CartAdapter
import com.os_tec.store.Model.CartDataModel
import com.os_tec.store.R
import com.os_tec.store.Classes.RecyclerViewDecoration
import com.os_tec.store.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    private val cartArrayList = ArrayList<CartDataModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        cartArrayList.add(CartDataModel("Female t- shirt", "Defacto", "$37.00", 2, R.drawable.img3))
        cartArrayList.add(CartDataModel("Female t- shirt", "Nike", "$78.00", 1, R.drawable.img1))
        cartArrayList.add(CartDataModel("Woman Shirt", "Adidas", "$50.00", 1, R.drawable.img2))
        cartArrayList.add(CartDataModel("Female t- shirt", "Defacto", "$37.00", 3, R.drawable.img3))
        cartArrayList.add(CartDataModel("Female t- shirt", "Nike", "$78.00", 1, R.drawable.img2))
        cartArrayList.add(CartDataModel("Woman Shirt", "Adidas", "$50.00", 1, R.drawable.img1))

        binding.rcCart.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )

        val adapterItems = CartAdapter(requireActivity(), cartArrayList)
        binding.rcCart.addItemDecoration(RecyclerViewDecoration(35,35,20,20))
        binding.rcCart.adapter = adapterItems

        binding.btnContinue.setOnClickListener {
            (activity as Navigation2Activity).startFragment((activity as Navigation2Activity).addressFragment)
        }

        //on backPressed Clicked-------------------------------------------------------------------
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity!!.finish()
                   // val sh=requireActivity().getSharedPreferences("")
                    //Toast.makeText(requireActivity(),"Cart Fragment",Toast.LENGTH_SHORT).show()
                }
            })

        return binding.root
    }


}