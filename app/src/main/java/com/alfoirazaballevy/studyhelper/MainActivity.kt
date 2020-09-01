package com.alfoirazaballevy.studyhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfoirazaballevy.studyhelper.db.DbHelper
import com.alfoirazaballevy.studyhelper.domain.Subject
import com.alfoirazaballevy.studyhelper.subject.activities.AddSubject
import com.alfoirazaballevy.studyhelper.subject.list_items.ListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var recyclerView: RecyclerView
        private lateinit var layoutManager : RecyclerView.LayoutManager
        private lateinit var adapterRecyclerLinear : ListAdapter
        private lateinit var listSubjects : ArrayList<Subject>
        private lateinit var faBtnAddSubject : FloatingActionButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listSubjects = loadSubjects()

        recyclerView = findViewById(R.id.recview_subjects)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapterRecyclerLinear =
            ListAdapter(
                this,
                listSubjects
            )
        recyclerView.adapter = adapterRecyclerLinear

        faBtnAddSubject = findViewById(R.id.fa_button_add_subject)

        faBtnAddSubject.setOnClickListener {
            val addSubjAcInt = Intent(this@MainActivity, AddSubject::class.java)
            startActivity(addSubjAcInt)
            finish()
        }

    }

    private fun loadSubjects() : ArrayList<Subject> {
        var dbHlp = DbHelper(this)
        return dbHlp.getSubjects()
    }

}