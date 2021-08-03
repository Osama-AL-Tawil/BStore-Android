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


        val creditCardArray=ArrayList<CardDataModel>()

        creditCardArray.add(CardDataModel("4533  8664  214  46","25/6"," Mr OSAMA F O ALTAWIL"))
        creditCardArray.add(CardDataModel("1546  2648  789  85","14/4","Mr SAMI G J SAMI"))
        creditCardArray.add(CardDataModel("9874  8798  654  68","24/7","Mr JAMAL A R JAMAL"))
        creditCardArray.add(CardDataModel("6547  9874  564  81","15/2","Mr HANI G J HANI"))



        // create view pager and set images and adapter
        val  adapter: PagerAdapter = ViewPagerAdapter(requireContext(), creditCardArray)
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



        binding.btnCheckout.setOnClickListener {
            (activity as Navigation2Activity).startFragment((activity as Navigation2Activity).checkOutFragment)
        }






        //on backPressed Clicked-------------------------------------------------------------------
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as Navigation2Activity).startFragment((activity as Navigation2Activity).addressFragment)
                    //Toast.makeText(requireActivity(),"Payment Fragment", Toast.LENGTH_SHORT).show()

                }
            })
        return binding.root
    }


}