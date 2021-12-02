package com.supinfo.roomapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.supinfo.roomapp.ClickOnItemListener
import com.supinfo.roomapp.R
import com.supinfo.roomapp.UserAdapter
import com.supinfo.roomapp.database.AppDatabase
import com.supinfo.roomapp.fragment.AddUserFragment
import com.supinfo.roomapp.model.UserEntity

class MainActivity : AppCompatActivity(), ClickOnItemListener {

    private lateinit var recyclerviewUser: RecyclerView
    private lateinit var fabAddUser: FloatingActionButton
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var userAdapter: UserAdapter
    private lateinit var allUser: MutableList<UserEntity>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerviewUser = findViewById(R.id.recyclerview)
        fabAddUser = findViewById(R.id.fabAddUser)

        val thread = Thread {

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()

            val dao = db.userEntityDao()
            //val user = UserEntity(0, "firstname", "lastname")

            //dao.insertAll(user)
            allUser = dao.getAll()

            runOnUiThread {
                getAllUsers()
            }

        }
        thread.start()

        fabAddUser.setOnClickListener {
            AddUserFragment().show(
                supportFragmentManager,
                AddUserFragment.ADD_NEW_USER
            )
        }
    }

    private fun getAllUsers() {
        if (allUser.isEmpty()) {
            recyclerviewUser.visibility = View.GONE
        } else {
            recyclerviewUser.visibility = View.VISIBLE
            setRecyclerView()
        }
    }

    private fun setRecyclerView() {

        linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        userAdapter = UserAdapter(allUser, this)
        recyclerviewUser.setHasFixedSize(false)
        recyclerviewUser.layoutManager = linearLayoutManager
        recyclerviewUser.adapter = userAdapter

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemLClicked(userEntity: UserEntity) {
        val thread = Thread {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()

            val dao = db.userEntityDao()
            dao.delete(userEntity)

            runOnUiThread {
                allUser.remove(userEntity)
                userAdapter.notifyDataSetChanged()

            }

        }
        thread.start()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onUserInserted(userEntity: UserEntity) {
        allUser.add(userEntity)
        userAdapter.notifyDataSetChanged()
    }
}