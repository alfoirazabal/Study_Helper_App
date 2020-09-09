package com.alfoirazaballevy.studyhelper.topic.question.textual

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.db.DbHelper

class AddTextualQuestion(

) : AppCompatActivity() {

    companion object {
        private lateinit var txtTopic : TextView
        private lateinit var etxtQuestion : EditText
        private lateinit var etxtAnswer : EditText
        private lateinit var etxtScore : EditText
        private lateinit var btnAddQuestion : Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_textual_add)

        txtTopic = findViewById(R.id.txt_topic_name)
        etxtQuestion = findViewById(R.id.etxt_question)
        etxtAnswer = findViewById(R.id.etxt_answer)
        etxtScore = findViewById(R.id.etxt_score)
        btnAddQuestion = findViewById(R.id.btn_add_question)

        val bundle = this.intent.extras
        val topicId = bundle!!.getLong("TOPICID")
        val topicName = bundle.getString("TOPICNAME")

        txtTopic.text = topicName

        btnAddQuestion.setOnClickListener {
            val DbHlp = DbHelper(applicationContext)
            val questionTitle = etxtQuestion.text.toString()
            val questionAnswer = etxtAnswer.text.toString()
            val score = etxtScore.text.toString().toFloat()
            DbHlp.addTextualQuestion(topicId, questionTitle, questionAnswer, score)
            finish()
        }

    }

}