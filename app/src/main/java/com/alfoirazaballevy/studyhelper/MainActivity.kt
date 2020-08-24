package com.alfoirazaballevy.studyhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        lateinit var listSubjects : ListView
        lateinit var searchSubjects : SearchView
        lateinit var adapter : ArrayAdapter<String>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listSubjects = findViewById(R.id.lst_subjects)
        searchSubjects = findViewById(R.id.searchview_subjects)

        val subjects = ArrayList<String>()

        subjects.add("Subj1")
        subjects.add("Subj2")
        subjects.add("Subj3")

        adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, subjects)
        listSubjects.adapter = adapter

        searchSubjects.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query : String) : Boolean {
        return false
    }

    override fun onQueryTextChange(newText : String) : Boolean {
        adapter.filter.filter(newText)
        return false
    }
}