package com.os_tec.store.Fragments.Search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.os_tec.store.Adapters.ItemsAdapter
import com.os_tec.store.Classes.RecyclerViewDecoration
import com.os_tec.store.Model.ProductsModel
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentSearchBinding
import kotlinx.coroutines.DelicateCoroutinesApi


class SearchFragment : Fragment() {
lateinit var binding:FragmentSearchBinding
lateinit var viewModel: SearchViewModel
    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentSearchBinding.inflate(inflater,container,false)
        viewModel= SearchViewModel()


        binding.rcSearch.layoutManager=GridLayoutManager(requireContext(),2)

        binding.rcSearch.addItemDecoration(RecyclerViewDecoration(
            resources.getDimensionPixelSize(R.dimen.marginHorizontal),
            resources.getDimensionPixelSize(R.dimen.marginHorizontal),
            resources.getDimensionPixelSize(R.dimen.marginTop),
            resources.getDimensionPixelSize(R.dimen.marginBottom)))


        //observe ->get Data
       // observeData()

        //search edit text
        binding.edSearch.requestFocus()//showKeyboard


        //searching--------
        binding.edSearch.doOnTextChanged { text, _, _, _ ->
            if (text!!.count()>1){
                binding.progressBar.visibility=View.VISIBLE //show progressBar
                binding.btnClear.visibility = View.VISIBLE //show clear btn in edit text
                getData(text.toString()) //viewModel

            }
        }

        //hideKeyboard
        binding.btnClear.setOnClickListener {
            hideAndClear()
        }




        return binding.root
    }

    private fun getData(text: String){
        viewModel.search(requireActivity(), text).observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE //hide progressBar
            setAdapter(it) //set adapter and data
        })

    }

    private fun hideAndClear() {
        binding.edSearch.text.clear()
        binding.btnClear.visibility = View.GONE
        hideKeyboard()

    }

    private fun hideKeyboard(){
        binding.edSearch.clearFocus()
        val context = requireActivity() as Context
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun setAdapter(arrayList: ArrayList<ProductsModel>) {
        val adapter = ItemsAdapter(requireActivity(), arrayList)
        binding.rcSearch.adapter = adapter
    }
}