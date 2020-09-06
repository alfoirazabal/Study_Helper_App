package com.alfoirazaballevy.studyhelper.topic.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.db.DbHelper

class TopicPanel(

) : AppCompatActivity() {

    private var topicId : Long = -1
    private var topicName : String? = ""

    companion object {
        private lateinit var txtTopicName : TextView
        private lateinit var txtNumberQuestionsFound : TextView
        private lateinit var btnAddQuestion : Button
        private lateinit var btnEditQuestion : Button
        private lateinit var btnDeleteQuestion : Button
        private lateinit var btnStartQuiz : ImageButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic_panel)

        val dbHlp = DbHelper(applicationContext)

        txtTopicName = findViewById(R.id.txt_topic_name)
        txtNumberQuestionsFound = findViewById(R.id.txt_number_questions_found)
        btnAddQuestion = findViewById(R.id.btn_add_question)
        btnEditQuestion = findViewById(R.id.btn_edit_question)
        btnDeleteQuestion = findViewById(R.id.btn_delete_question)
        btnStartQuiz = findViewById(R.id.btn_start_quiz)

        var bundle = this.intent.extras
        topicId = bundle!!.getLong("TOPICID")
        topicName = bundle!!.getString("TOPICNAME")

        txtTopicName.text = topicName

        val questionsCount = dbHlp.countTopicQuestions(topicId)
        txtNumberQuestionsFound.text = questionsCount.toString()
        
    }

}