package com.example.firebaseapp.myPackages.userInterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.FragmentSignInBinding
import com.example.firebaseapp.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class SignIn : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var navController: NavController
    var Auth: FirebaseAuth? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        navController = Navigation.findNavController(view)

        val activity = activity as MainActivity
        activity.supportActionBar?.hide()
        //to create object
        Auth = FirebaseAuth.getInstance()
        //for login
        binding.login.setOnClickListener()
        {
          signIn()
        }
    }
//***************************************************************************************
    private fun signIn() {
        if (binding.email.text.isNotEmpty() && binding.password.text.isNotEmpty()) {
            binding.emailrequired.isVisible = false
            binding.passwordrequired.isVisible = false
            binding.progressBarSignup.isVisible = true
            Auth?.signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
                ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(p0: Task<AuthResult>) {
                        if (p0.isSuccessful) {
                            //This account already exists
                            verifyEmailAddress()
                            binding.progressBarSignup.isVisible = false
                        } else {
                            //this account is not found
                            Toast.makeText(
                                requireContext(),
                                p0.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            binding.progressBarSignup.isVisible = false
                        }
                    }
                })
        } else {
            if (binding.email.text.isEmpty())
                binding.emailrequired.isVisible = true
            if (binding.password.text.isEmpty())
                binding.passwordrequired.isVisible = true
        }
    }
    //*********************************************************************************
    private fun verifyEmailAddress()
    {
        //for verify email
        if(Auth?.currentUser!!.isEmailVerified)
        {
            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_LONG)
                .show()
            var bundle = bundleOf(
                "key1" to binding.email.text.toString(),
                "key2" to binding.password.text.toString()
            )
            navController.navigate(R.id.action_signIn_to_dealingWithNote, bundle)
        }else{
            Toast.makeText(requireContext(), "Please verify your account..", Toast.LENGTH_LONG).show()
        }
    }
}