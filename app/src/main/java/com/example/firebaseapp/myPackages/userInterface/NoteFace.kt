package com.example.firebaseapp.myPackages.userInterface

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.FragmentNoteFaceBinding


class NoteFace : Fragment(R.layout.fragment_note_face) {
    private lateinit var binding: FragmentNoteFaceBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteFaceBinding.bind(view)
        navController = Navigation.findNavController(view)

        val activity = activity as MainActivity
        activity.supportActionBar?.hide()

        //For the quality of the pictures
        Glide.with(requireContext()).load(R.drawable.stars).into(binding.image2)
        Glide.with(requireContext()).load(R.drawable.notephoto).into(binding.image1)
        binding.signup.setOnClickListener()
        {
            navController.navigate(R.id.action_noteFace_to_signUp)
        }
        binding.signin.setOnClickListener()
        {
            navController.navigate(R.id.action_noteFace_to_signIn)
        }
    }
}