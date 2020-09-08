package com.alfoirazaballevy.studyhelper.topic.question.tf.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.db.DbHelper

class AddTFQuestion(

) : AppCompatActivity() {

    private var topicId : Long = -1
    private var topicName : String? = ""

    companion object {
        private lateinit var txtTopicName : TextView
        private lateinit var etxtPregunta : EditText
        private lateinit var etxtRespuestaPositiva : EditText
        private lateinit var etxtRespuestaNegativa : EditText
        private lateinit var etxtRespuestaScore : EditText
        private lateinit var btnAddQuestion : Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_tf_add)

        txtTopicName = findViewById(R.id.txt_topic_name)
        etxtPregunta = findViewById(R.id.etxt_pregunta)
        etxtRespuestaPositiva = findViewById(R.id.etxt_respuesta_positiva)
        etxtRespuestaNegativa = findViewById(R.id.etxt_respuesta_negativa)
        etxtRespuestaScore = findViewById(R.id.etxt_respuesta_score)
        btnAddQuestion = findViewById(R.id.btn_add_question)

        val bundle = this.intent.extras
        topicId = bundle!!.getLong("TOPICID")
        topicName = bundle.getString("TOPICNAME")

        txtTopicName.text = topicName

        btnAddQuestion.setOnClickListener {
            val dbHlp = DbHelper(applicationContext)
            val questionTitle = etxtPregunta.text.toString()
            val responsePositive = etxtRespuestaPositiva.text.toString()
            val responseNegative = etxtRespuestaNegativa.text.toString()
            val score = etxtRespuestaScore.text.toString().toFloat()
            dbHlp.addTFQuestion(topicId, questionTitle, responsePositive, responseNegative, score)
            finish()
        }
    }

}