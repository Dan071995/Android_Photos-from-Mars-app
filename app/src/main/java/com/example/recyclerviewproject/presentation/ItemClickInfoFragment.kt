package com.example.recyclerviewproject.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.recyclerviewproject.constants.DATA_KEY
import com.example.recyclerviewproject.constants.IMAGE_URL
import com.example.recyclerviewproject.constants.ROVER_NAME_KEY
import com.example.recyclerviewproject.constants.SOL_KEY
import com.example.recyclerviewproject.databinding.FragmentItemClickInfoBinding


class ItemClickInfoFragment : Fragment() {

    private var _binding: FragmentItemClickInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemClickInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photoUrl = arguments?.getString(IMAGE_URL)?.replace("http","https")

        binding.apply {

            imageView.load(photoUrl)

            textViewPhotoDate.text = arguments?.getString(DATA_KEY)
            textViewSol.text = arguments?.getString(SOL_KEY)
            textViewRoverName.text = arguments?.getString(ROVER_NAME_KEY)
        }


        binding.buttonBack.setOnClickListener { findNavController().navigateUp() }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}