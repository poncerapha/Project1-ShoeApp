package com.udacity.shoestore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailsBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.viewmodel.ShoeViewModel

class ShoeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailsBinding
    private lateinit var viewModel: ShoeViewModel
    var shoe : Shoe? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (arguments?.get("shoe")!=null) {
            shoe = requireArguments().get("shoe") as Shoe?
        }

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_details,
            container,
            false
        )

        if (shoe != null){
            binding.model = shoe
            binding.shoeNameEditText.isEnabled = false
        }

        viewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)

        binding.addButton.setOnClickListener {
            viewModel.saveShoe(
                Shoe(name = binding.shoeNameEditText.text.toString(),
                    size = binding.shoeSizeEditText.text.toString().toDouble(),
                    company = binding.companyNameEditText.text.toString(),
                    description = binding.shoeDescriptionEditText.text.toString(),
                    images = arrayListOf("", "")
                )
            )
            viewModel.modifyShoeEnabled = false
            findNavController().popBackStack()
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_shoeDetailsFragment_to_shoeListFragment)
        }

        return binding.root
    }
}