package com.os_tec.store.Fragments.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Activities.ShowItems.ShowItemsActivity
import com.os_tec.store.Adapters.CategoriseAdapter
import com.os_tec.store.Adapters.ItemsAdapter
import com.os_tec.store.Model.CategoryModel
import com.os_tec.store.Model.ProductsModel
import com.os_tec.store.databinding.FragmentHomeBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.Serializable


@DelicateCoroutinesApi
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel= HomeViewModel()

        setLayout()//set layoutManager
        loadData()//get data from api  and set adapter


        binding.categoriesSeeAll.setOnClickListener {
            showActivity("Categories", viewModel.categoryArrayList)

        }


        binding.btnFeaturedSeeAll.setOnClickListener {
            showActivity("Featured", viewModel.featuredProductsArrayList)

        }


        binding.bestSellSeeAll.setOnClickListener {
            showActivity("BestSell", viewModel.bestSellProductsArrayList)

        }




        //handle searchView clicks
        binding.searchView.setOnClickListener {
            val intent=Intent(requireContext(),Navigation3Activity::class.java)
            intent.putExtra("nv","search")
            startActivity(intent)
        }




        return binding.root
    }

    //start Activity
    private fun showActivity(nv:String,data: Serializable){
        val i = Intent(requireContext(), ShowItemsActivity::class.java)
        i.putExtra("nv", nv)
        i.putExtra("data", data)
        startActivity(i)
    }






    private fun loadData(){


        viewModel.loadData(requireActivity()).observe(viewLifecycleOwner,{
            val status=it.status
            val message=it.failureMessage

            if (status){//true -> Array NotEmpty
                binding.errorLayout.root.visibility=View.GONE //errorLayout
                binding.scrolLayout.visibility = View.VISIBLE

                //set data and adapter
                setCategoryAdapter(it.data.categories)
                setFeaturedAdapter(it.data.featured_products)
                setBestCellAdapter(it.data.best_sell_products)

            } else { //handle internet or any error by show FailureMessage layout //EmptyArray
                binding.errorLayout.root.visibility = View.VISIBLE
                binding.errorLayout.errorMessage.text=message
                binding.errorLayout.btnAction.setOnClickListener {
                    loadData()
                }
            }

        })

    }



    private fun setLayout(){
        binding.rcCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rcBestCell.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rcFeatured.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


    fun setCategoryAdapter(data: ArrayList<CategoryModel>) {
        val adapterCategory = CategoriseAdapter(requireActivity(), data)//set data
        //set data in arrays to show all products -> onclick (seeAll)
        viewModel.categoryArrayList.addAll(data)
        binding.rcCategories.adapter = adapterCategory
    }


    fun setFeaturedAdapter(data: ArrayList<ProductsModel>) {
        val adapterItems = ItemsAdapter(requireActivity(), data)//set data
        //set data in arrays to show all products -> onclick (seeAll)
        viewModel.featuredProductsArrayList.addAll(data)
        binding.rcFeatured.adapter = adapterItems
    }


    fun setBestCellAdapter(data: ArrayList<ProductsModel>) {
        val adapterItems = ItemsAdapter(requireActivity(), data)//set data
        //set data in arrays to show all products -> onclick (seeAll)
        viewModel.bestSellProductsArrayList.addAll(data)
        binding.rcBestCell.adapter = adapterItems
    }


}