package com.os_tec.store.Fragments.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Adapters.CategoriseAdapter
import com.os_tec.store.Adapters.ItemsAdapter
import com.os_tec.store.Classes.CustomAlertDialog
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentHomeBinding
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var customAlertDialog:CustomAlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel= HomeViewModel()
        customAlertDialog= CustomAlertDialog(requireActivity())

        binding.rcCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcBestCell.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcFeatured.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        loadData()//get data from api

        observeData()//observe Data and set adapter


        binding.categoriesSeeAll.setOnClickListener {
            val i = Intent(requireContext(), SeeAllActivity::class.java)
            i.putExtra("nv", "Categories")
            i.putExtra("data", viewModel.categoryArrayList)
            startActivity(i)
        }


        binding.btnFeaturedSeeAll.setOnClickListener {
            val i = Intent(requireContext(), SeeAllActivity::class.java)
            i.putExtra("nv", "Featured")
            i.putExtra("data", viewModel.featuredProductsArrayList)
            startActivity(i)
        }


        binding.bestSellSeeAll.setOnClickListener {
            val i = Intent(requireContext(), SeeAllActivity::class.java)
            i.putExtra("nv", "BestSell")
            i.putExtra("data", viewModel.bestSellProductsArrayList)

            startActivity(i)
        }




        //handle searchView clicks
        binding.searchView.setOnClickListener {
            val intent=Intent(requireContext(),Navigation3Activity::class.java)
            intent.putExtra("nv","search")
            startActivity(intent)
        }





        return binding.root
    }



   private fun loadData() {
       customAlertDialog.showDialog(resources.getString(R.string.DialogLoading))
        viewModel.loadData()

    }

    private fun observeData(){

        viewModel.categoryLiveData.observe(viewLifecycleOwner, {
            val adapterCategory = CategoriseAdapter(requireActivity(), it)
            binding.rcCategories.adapter = adapterCategory
            binding.scrolLayout.visibility=View.VISIBLE
            customAlertDialog.dismissDialog()
        })


        viewModel.featuredProductsLiveData.observe(viewLifecycleOwner, {
            val adapterItems = ItemsAdapter(requireActivity(), it)
            binding.rcFeatured.adapter = adapterItems
        })

        viewModel.bestSellProductsLiveData.observe(viewLifecycleOwner, {
            val adapterItems = ItemsAdapter(requireActivity(), it)
            binding.rcBestCell.adapter = adapterItems
        })




    }






}