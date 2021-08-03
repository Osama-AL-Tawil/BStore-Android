package com.os_tec.store.Fragments.Favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.os_tec.store.Adapters.FavoriteAdapter
import com.os_tec.store.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {
   lateinit var binding:FragmentFavoriteBinding
   lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentFavoriteBinding.inflate(inflater,container,false)
        viewModel= FavoriteViewModel()

        observeFavorite()


        return binding.root
    }


    private fun observeFavorite() {
        viewModel.getFavorite() //get Data..

        viewModel.favoriteLiveData.observe(viewLifecycleOwner, {
            val adapter = FavoriteAdapter(requireActivity(), it)
            binding.rcFavorite.adapter = adapter
        })

    }
}