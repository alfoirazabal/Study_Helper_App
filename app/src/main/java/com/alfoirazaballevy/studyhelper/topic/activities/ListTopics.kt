package com.alfoirazaballevy.studyhelper.topic.activities

import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfoirazaballevy.studyhelper.layoutadapters.ListAdapter
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.db.DbHelper
import com.alfoirazaballevy.studyhelper.domain.ListableTypeOne
import com.alfoirazaballevy.studyhelper.domain.Topic
import com.alfoirazaballevy.studyhelper.layoutadapters.ListAdapterSubject
import com.alfoirazaballevy.studyhelper.layoutadapters.ListAdapterTopic
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListTopics() : AppCompatActivity() {

    companion object {
        private lateinit var txtSubjectName : TextView
        private lateinit var recyclerView : RecyclerView
        private lateinit var layoutManager : RecyclerView.LayoutManager
        private lateinit var adapterRecyclerLinear : ListAdapter
        private lateinit var listTopics : ArrayList<Topic>
        private lateinit var faBtnAddTopic : FloatingActionButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic_view)

        txtSubjectName = findViewById(R.id.txt_topic_name)

        val bundle = this.intent.extras
        val subjectId = bundle!!.getLong("SUBJID")
        val subjectName = bundle!!.getString("SUBJNAME")

        txtSubjectName.text = subjectName

        listTopics = loadTopics(subjectId)

        recyclerView = findViewById(R.id.recview_topic)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapterRecyclerLinear =
            ListAdapterTopic(
                applicationContext,
                listTopics
            )
        recyclerView.adapter = adapterRecyclerLinear

        faBtnAddTopic = findViewById(R.id.fa_button_add_topic)

    }

    private fun loadTopics(subjectId : Long) : ArrayList<Topic> {
        val dbHlp = DbHelper(applicationContext)
        return dbHlp.getTopics(subjectId)
    }

}