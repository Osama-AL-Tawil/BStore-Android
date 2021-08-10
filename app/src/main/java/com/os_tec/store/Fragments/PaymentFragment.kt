package com.os_tec.store.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.os_tec.store.Activities.Navigation2Activity
import com.os_tec.store.Adapters.ViewPagerAdapter
import com.os_tec.store.Api.ApiRepository
import com.os_tec.store.Classes.SharedPreferences
import com.os_tec.store.Model.CardDataModel
import com.os_tec.store.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {
    lateinit var binding: FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        (activity as Navigation2Activity).backPressedNV=2


        val creditCardArray=ArrayList<CardDataModel>()
        creditCardArray.add(CardDataModel("4533  8664  214  46","25/6"," Mr OSAMA F O ALTAWIL"))

        setViewPagerAdapter(creditCardArray) //set adapter






        binding.btnCheckout.setOnClickListener {
            //get ids from shared preferences and add in Map
            val params =HashMap<String,Any>()
            params["cart_ids"]=SharedPreferences().getIdsArray() as ArrayList<*>

            ApiRepository(requireActivity()).payment("paymentFragment",params)// payment

            SharedPreferences().removeIds() // remove card_ids from SharedPreferences

            (activity as Navigation2Activity).startFragment((activity as Navigation2Activity).checkOutFragment)
        }






        return binding.root
    }

private fun setViewPagerAdapter(array:ArrayList<CardDataModel>){
    // create view pager and set images and adapter
    val  adapter: PagerAdapter = ViewPagerAdapter(requireContext(), array)
    val viewPager=binding.ViewPager

    viewPager.currentItem = 1
    viewPager.setPadding(50,0,50,0)
    viewPager.adapter=adapter

    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            //Toast.makeText(requireContext(),creditCardArray[position].cardNo,Toast.LENGTH_SHORT).show()
        }

        override fun onPageSelected(position: Int) {
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

    })
}
}