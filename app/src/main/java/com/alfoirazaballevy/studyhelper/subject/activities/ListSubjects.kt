package com.alfoirazaballevy.studyhelper.subject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.db.DbHelper
import com.alfoirazaballevy.studyhelper.domain.Subject
import com.alfoirazaballevy.studyhelper.domain.ListableTypeOne
import com.alfoirazaballevy.studyhelper.layoutadapters.ListAdapter
import com.alfoirazaballevy.studyhelper.subject.adapters.ListAdapterSubject
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.collections.ArrayList

class ListSubjects : AppCompatActivity() {

    companion object {
        private lateinit var recyclerView: RecyclerView
        private lateinit var layoutManager : RecyclerView.LayoutManager
        private lateinit var adapterRecyclerLinear : ListAdapter
        private lateinit var listSubjects : ArrayList<Subject>
        private lateinit var faBtnAddSubject : FloatingActionButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subject_view)

        listSubjects = loadSubjects()

        recyclerView = findViewById(
            R.id.recview_subjects
        )
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager =
            layoutManager

        adapterRecyclerLinear =
            ListAdapterSubject(
                this,
                listSubjects
            )
        recyclerView.adapter =
            adapterRecyclerLinear

        faBtnAddSubject = findViewById(
            R.id.fa_button_add_subject
        )

        faBtnAddSubject.setOnClickListener {
            val addSubjAcInt = Intent(this@ListSubjects, AddSubject::class.java)
            startActivity(addSubjAcInt)
        }

    }

    override fun onResume() {
        reloadList()
        super.onResume()
    }

    private fun reloadList() {
        val lstObj = ArrayList<ListableTypeOne>()
        lstObj.addAll(loadSubjects())
        adapterRecyclerLinear.lstObjects = lstObj
        adapterRecyclerLinear.updateDataset()
    }

    private fun loadSubjects() : ArrayList<Subject> {
        return DbHelper(this).getSubjects()
    }

}