package com.example.firebaseapp.myPackages.userInterface

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.FragmentDisplayNoteBinding


class DisplayNote : Fragment(R.layout.fragment_display_note) {
    private lateinit var binding: FragmentDisplayNoteBinding
    private lateinit var navController: NavController
    private var fontSize = 15f
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDisplayNoteBinding.bind(view)
        navController = Navigation.findNavController(view)

        val activity = activity as MainActivity
        activity.supportActionBar?.hide()
        var noteBody = arguments?.getString("note")
        var title = arguments?.getString("title")
        binding.notetext.setText(noteBody)
        binding.title.setText(title)
        binding.zoomIn.setOnClickListener()
        {
            if (fontSize < 40f) {
                fontSize += 2f
                binding.notetext.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            }
        }
        //to decrease font size
        binding.zoomOut.setOnClickListener()
        {
            if (fontSize >= 15f) {
                fontSize -= 2f
                binding.notetext.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            }
        }
        //to set image
        var images = ArrayList<Int>()
        images.add(R.drawable.image2)
        images.add(R.drawable.image3)
        images.add(R.drawable.image4)
        images.add(R.drawable.image5)
        val pos = (0..3).random()
        Glide.with(requireContext()).load(images[pos!!]).into(binding.image)
    }
}