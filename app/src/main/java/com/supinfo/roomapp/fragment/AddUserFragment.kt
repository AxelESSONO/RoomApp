package com.supinfo.roomapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.supinfo.roomapp.ClickOnItemListener
import com.supinfo.roomapp.R
import com.supinfo.roomapp.database.AppDatabase
import com.supinfo.roomapp.model.UserEntity

class AddUserFragment : DialogFragment() {

    private lateinit var editFirstname: EditText
    private lateinit var editLastname: EditText
    private lateinit var buttonAddUser: android.widget.Button
    private lateinit var buttonCancel: android.widget.Button
    private lateinit var clickOnItemListener: ClickOnItemListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_user, container, false)

        bindView(rootView)

        buttonAddUser.setOnClickListener {
            insertNewUser()
        }

        buttonCancel.setOnClickListener {
            dismiss()
        }
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickOnItemListener) {
            this.clickOnItemListener = context
        }
    }

    private fun insertNewUser() {
        if (editFirstname.text.toString().trim().isEmpty()
            || editLastname.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(requireContext(), "Saisissez le prénom et le nom", Toast.LENGTH_SHORT)
                .show()
        } else {
            val user = UserEntity(
                0,
                editFirstname.text.toString().trim(),
                editLastname.text.toString().trim()
            )

            val thread = Thread {

                val db = Room.databaseBuilder(
                    requireContext(),
                    AppDatabase::class.java, "database-name"
                ).build()

                val dao = db.userEntityDao()
                dao.insertAll(user)

                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Nouvel utilisateur inséré avec succès", Toast.LENGTH_SHORT
                    ).show()
                    editFirstname.text.clear()
                    editLastname.text.clear()
                }
            }
            thread.start()
            clickOnItemListener.onUserInserted(user)
        }
    }

    private fun bindView(rootView: View) {
        editFirstname = rootView.findViewById(R.id.editFirstname)
        editLastname = rootView.findViewById(R.id.editLastname)
        buttonAddUser = rootView.findViewById(R.id.buttonAddUser)
        buttonCancel = rootView.findViewById(R.id.buttonCancel)
    }

    companion object {
        const val ADD_NEW_USER = "ADD_NEW_USER"
    }
}