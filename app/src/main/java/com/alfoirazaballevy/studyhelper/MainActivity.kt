package com.alfoirazaballevy.studyhelper

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfoirazaballevy.studyhelper.db.DbHelper
import com.alfoirazaballevy.studyhelper.domain.Subject
import com.alfoirazaballevy.studyhelper.list_items.ViewListSubjectAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var recyclerView: RecyclerView
        lateinit var layoutManager : RecyclerView.LayoutManager
        lateinit var adapterRecyclerLinear : ViewListSubjectAdapter
        lateinit var listSubjects : ArrayList<Subject>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadSubjects()

        recyclerView = findViewById(R.id.recview_subjects)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapterRecyclerLinear = ViewListSubjectAdapter(this, listSubjects)
        recyclerView.adapter = adapterRecyclerLinear

    }

    private fun loadSubjects() {
        var dbHlp = DbHelper(this)
        listSubjects = dbHlp.getSubjects()
    }

}