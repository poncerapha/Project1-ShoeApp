package com.udacity.shoestore.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.MainActivity
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailsBinding
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ItemShoeBinding
import com.udacity.shoestore.viewmodel.ShoeViewModel

class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    private lateinit var viewModel: ShoeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_list,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)

        viewModel.shoesLiveData.observe(viewLifecycleOwner, Observer { shoes ->
            binding.shoeList.removeAllViews()
            shoes.forEach { shoe ->
                val bindingItem: ItemShoeBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_shoe, binding.shoeList, false)
                bindingItem.model = shoe
                bindingItem.root.setOnClickListener {
                    viewModel.modifyShoeEnabled = true
                    val direction = ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailsFragment(shoe)
                    findNavController().navigate(direction)
                }
                binding.shoeList.addView(bindingItem.root)
            }
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_shoeListFragment_to_shoeDetailsFragment)
        }

        (requireActivity() as MainActivity).hideNavigationArrow()
        setHasOptionsMenu(true)

        viewModel.getShoeList()
        return binding.root
    }
}