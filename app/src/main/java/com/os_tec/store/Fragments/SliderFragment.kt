package com.os_tec.store.Fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Adapters.SliderAdapter
import com.os_tec.store.Classes.ConnectivityReceiver
import com.os_tec.store.Model.SliderModel
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentSliderBinding


class SliderFragment : Fragment() {
lateinit var binding:FragmentSliderBinding
lateinit var mActivity: Navigation3Activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentSliderBinding.inflate(inflater, container, false)
        mActivity=activity as Navigation3Activity

        val data =requireArguments().getSerializable("slider")
        val array=data as ArrayList<SliderModel> //array come from ApiRepository -> Navigation3Activity

       setViewPager(array) //set adapter

        binding.btnSkip.setOnClickListener {
            mActivity.startFragment(mActivity.welcomeFragment)
        }

        return binding.root
    }


   private fun setViewPager(data:ArrayList<SliderModel>){
       val viewPager=binding.viewPager
       viewPager.adapter=SliderAdapter(requireContext(), data)
       binding.indicator.viewPager=viewPager

       viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
           override fun onPageScrolled(
               position: Int,
               positionOffset: Float,
               positionOffsetPixels: Int
           ) {
               if (position==data.lastIndex){ //last index
                   binding.btnStart.text=resources.getString(R.string.start_now)
                   binding.btnStart.setOnClickListener {
                       mActivity.startFragment(mActivity.welcomeFragment)
                   }
               }else{
                   binding.btnStart.text=resources.getString(R.string.next)
                   binding.btnStart.setOnClickListener {
                       viewPager.setCurrentItem(position+1,true)
                   }
               }
           }

           override fun onPageSelected(position: Int) {

           }

           override fun onPageScrollStateChanged(state: Int) {
           }


       })
   }


}