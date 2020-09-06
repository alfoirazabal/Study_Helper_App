package com.alfoirazaballevy.studyhelper.topic.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.db.DbHelper

class AddTopic() : AppCompatActivity() {

    companion object {
        private lateinit var txtSubjectName : TextView
        private lateinit var etxtTopicName : EditText
        private lateinit var btnAddTopic : Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic_add)

        val bundle = intent!!.extras
        val subjectName = bundle!!.getString("SUBJECTNAME")
        val subjectId = bundle!!.getLong("SUBJECTID")

        txtSubjectName = findViewById(R.id.txt_topic_name)
        etxtTopicName = findViewById(R.id.etxt_topic_name)
        btnAddTopic = findViewById(R.id.btn_add_topic)

        txtSubjectName.text = subjectName

        etxtTopicName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                btnAddTopic.isEnabled = etxtTopicName.text.toString() != ""
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        })

        btnAddTopic.setOnClickListener {
            val topicName = etxtTopicName.text.toString()
            val dbHlp = DbHelper(applicationContext)
            dbHlp.addTopic(subjectId, topicName)

            finish()
        }

    }

}