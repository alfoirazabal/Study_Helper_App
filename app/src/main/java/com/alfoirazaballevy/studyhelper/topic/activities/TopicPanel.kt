package com.alfoirazaballevy.studyhelper.topic.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.db.DbHelper
import com.alfoirazaballevy.studyhelper.topic.question.mo.activities.AddMOQuestion

class TopicPanel(

) : AppCompatActivity() {

    private var topicId : Long = -1
    private var topicName : String? = ""

    companion object {
        private lateinit var txtTopicName : TextView
        private lateinit var txtNumberQuestionsFound : TextView
        private lateinit var btnViewStats : Button
        private lateinit var btnAddQuestion : Button
        private lateinit var btnEditQuestion : Button
        private lateinit var btnDeleteQuestion : Button
        private lateinit var btnStartQuiz : ImageButton

        private lateinit var dbHlp : DbHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic_panel)

        dbHlp = DbHelper(applicationContext)

        txtTopicName = findViewById(R.id.txt_topic_name)
        txtNumberQuestionsFound = findViewById(R.id.txt_number_questions_found)
        btnViewStats = findViewById(R.id.btn_view_stats)
        btnAddQuestion = findViewById(R.id.btn_add_question)
        btnEditQuestion = findViewById(R.id.btn_edit_question)
        btnDeleteQuestion = findViewById(R.id.btn_delete_question)
        btnStartQuiz = findViewById(R.id.btn_start_quiz)

        val bundle = this.intent.extras
        topicId = bundle!!.getLong("TOPICID")
        topicName = bundle.getString("TOPICNAME")

        txtTopicName.text = topicName

        val questionsCount = dbHlp.countTopicQuestions(topicId)
        txtNumberQuestionsFound.text = questionsCount.toString()

        // Actions

        btnAddQuestion.setOnClickListener {view : View ->
            val popupMenu = PopupMenu(applicationContext, view)
            popupMenu.inflate(R.menu.subject_add_question_of_type_menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {menuItem ->
                when (menuItem.itemId) {
                    R.id.opt_add_q_mo -> {
                        // Handle MO Question Add.
                        val newMOIntent = Intent(applicationContext, AddMOQuestion::class.java)
                        newMOIntent.putExtra("TOPICID", topicId)
                        newMOIntent.putExtra("TOPICNAME", topicName)
                        this.startActivity(newMOIntent)
                        true
                    }
                    R.id.opt_add_q_tof -> {
                        // Handle TOF Question Add.
                        true
                    }
                    R.id.opt_add_q_textual -> {
                        // Handle Textual Question Add.
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
        
    }

    override fun onResume() {
        super.onResume()
        dbHlp = DbHelper(applicationContext)
        val questionsCount = dbHlp.countTopicQuestions(topicId)
        txtNumberQuestionsFound.text = questionsCount.toString()
    }

}