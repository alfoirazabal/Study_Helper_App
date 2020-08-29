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
import java.util.*
import kotlin.collections.ArrayList

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

        listSubjects.add(Subject(1, "Coco1", Date()))
        listSubjects.add(Subject(2, "Coco2", Date()))
        listSubjects.add(Subject(3, "Coco3", Date()))
        listSubjects.add(Subject(4, "Coco4", Date()))
        listSubjects.add(Subject(5, "Cocasf5", Date()))
        listSubjects.add(Subject(6, "Coco6", Date()))
        listSubjects.add(Subject(7, "Coco7", Date()))
        listSubjects.add(Subject(8, "Coco8", Date()))
        listSubjects.add(Subject(9, "Coco9", Date()))
        listSubjects.add(Subject(10, "Coco10", Date()))
        listSubjects.add(Subject(11, "Cocoasf11", Date()))
        listSubjects.add(Subject(12, "Coco12", Date()))
        listSubjects.add(Subject(13, "Coco13", Date()))
        listSubjects.add(Subject(14, "Coco14", Date()))

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