package com.example.firebaseapp.myPackages.userInterface

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.DealingWithNoteBinding
import com.example.firebaseapp.myPackages.model.NoteContent
import com.example.firebaseapp.myPackages.model.NoteAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DealingWithNote : Fragment(R.layout.dealing_with_note) {
    private lateinit var binding: DealingWithNoteBinding
    private lateinit var navController: NavController
    var obj: DatabaseReference? = null
    var list: ArrayList<NoteContent>? = null
    var email: String? = null
    var password: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DealingWithNoteBinding.bind(view)
        navController = Navigation.findNavController(view)


        val activity = activity as MainActivity
        activity.supportActionBar?.show()
        activity.supportActionBar?.title = "My Notes"

        email = arguments?.getString("key1")
        password = arguments?.getString("key2")

        binding.progressBar.isVisible = true

        var database = Firebase.database
        obj = database.getReference("Note")
        list = ArrayList()
        binding.addNewNote.setOnClickListener() {
            saveNote(email.toString(), password.toString())
        }
        //for long click>>>to update or delete note
        binding.listview.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ): Boolean {
                //to display dialog
                val alertbuilder = AlertDialog.Builder(requireContext())
                val view = layoutInflater.inflate(R.layout.dialog2, null)
                alertbuilder.setView(view)
                val alertDialog = alertbuilder.create()
                alertDialog.show()
                val delete = view.findViewById<TextView>(R.id.delete_button)
                val update = view.findViewById<TextView>(R.id.update_button)
                val title_text = view.findViewById<TextView>(R.id.title_text)
                val note_text = view.findViewById<TextView>(R.id.note_text)
                //p2->indx in list
                val note = list?.get(p2)
                title_text.setText(note!!.title)
                note_text.setText(note!!.note)
                //to update note
                update.setOnClickListener() {
                    val child = obj?.child(note.id!!)
                    child?.setValue(
                        NoteContent(
                            note.id!!,
                            title_text.text.toString(),
                            note_text.text.toString(),
                            note!!.date!!,
                            note!!.email!!,
                            note!!.password!!
                        )
                    )
                    alertDialog.dismiss()
                }
                //to delete note
                delete.setOnClickListener() {
                    val child = obj?.child(note.id!!)?.removeValue()
                    alertDialog.dismiss()
                }
                return false
            }
        }
        //to display note
        binding.listview.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val note = list?.get(p2)
                val data = bundleOf("note" to note!!.note, "title" to note!!.title)
                navController.navigate(R.id.action_dealingWithNote_to_displayNote2, data)
            }
        }
    }

    //to read data from database
    override fun onStart() {
        super.onStart()
        obj?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list?.clear()
                for (data in snapshot.children) {
                    val note = data.getValue(NoteContent::class.java)
                    if (note!!.email == email && note!!.password == password)
                        list!!.add(0, note!!)
                }
                binding.progressBar.isVisible = false
                //we use (view!!.context)or(activity.context)instead of (require context)
                // because require context maybe return null
                //and that will cause IllegalStateException
                val adapter = NoteAdapter(view!!.getContext(), list!!)
                binding.listview.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun saveNote(email: String, password: String) {
        //to display dialog
        //we use layoutInflater class to access views of another layout in this place
        //it takes an XML file as input and builds the View objects from it
        val alertbuilder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog1, null)
        alertbuilder.setView(view)
        val alertDialog = alertbuilder.create()
        alertDialog.show()
        val add_button = view.findViewById<TextView>(R.id.add_button)
        val title_edit_text = view.findViewById<TextView>(R.id.title_edit_text)
        val note_edit_text = view.findViewById<TextView>(R.id.note_edit_text)
        //to enter and save note in database
        add_button.setOnClickListener()
        {
            val title = title_edit_text.text.toString()
            val note = note_edit_text.text.toString()
            if (title.isNotEmpty() && note.isNotEmpty()) {
                var id = obj!!.push().key
                val myNote = NoteContent(id.toString(), title, note, getDate(), email, password)
                obj!!.child(id.toString()).setValue(myNote)
                alertDialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "There is empty field", Toast.LENGTH_LONG).show()
            }
        }
    }

    //to calculate current date
    fun getDate(): String {
        val calender = Calendar.getInstance()
        val mdformat = SimpleDateFormat("EEEE hh:mm a")
        val strDate = mdformat.format(calender.time)
        return strDate
    }

    //for menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(R.id.action_dealingWithNote_to_noteFace)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
