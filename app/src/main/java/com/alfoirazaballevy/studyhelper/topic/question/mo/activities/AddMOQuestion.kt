package com.alfoirazaballevy.studyhelper.topic.question.mo.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.db.DbHelper
import com.alfoirazaballevy.studyhelper.domain.answertypes.AnswerMO
import com.alfoirazaballevy.studyhelper.topic.question.mo.adapters.QuestionMOListAdapter

class AddMOQuestion(

) : AppCompatActivity() {

    private var topicId : Long = -1
    private var topicName : String = ""

    private var seekBarMultiplier : Byte = 10
    private var questionMaxScore : Int = 1
    private var currentAnswerScore : Float = questionMaxScore.toFloat()

    private var newAnswersMO = ArrayList<AnswerMO>()

    companion object {
        private lateinit var txtTopicName : TextView
        private lateinit var etxtQuestionText : EditText
        private lateinit var etxtMaxScore : EditText
        private lateinit var etxtQuestionAnswer : EditText
        private lateinit var skbAnswerScore : SeekBar
        private lateinit var txtAnswerScore : TextView
        private lateinit var btnAddAnswer : Button
        private lateinit var recviewAnswers : RecyclerView
        private lateinit var btnAddQuestion : Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_mo_add)

        val bundle = this.intent.extras
        topicId = bundle!!.getLong("TOPICID")
        topicName = bundle.getString("TOPICNAME")!!

        txtTopicName = findViewById(R.id.txt_topic_name)
        etxtQuestionText = findViewById(R.id.etxt_question_text)
        etxtMaxScore = findViewById(R.id.etxt_max_score)
        etxtQuestionAnswer = findViewById(R.id.etxt_question_answer)
        skbAnswerScore = findViewById(R.id.skb_answer_score)
        txtAnswerScore = findViewById(R.id.txt_answer_score)
        btnAddAnswer = findViewById(R.id.btn_add_answer)
        recviewAnswers = findViewById(R.id.recview_answers)
        btnAddQuestion = findViewById(R.id.btn_add_question)

        val recyclerView = findViewById<RecyclerView>(R.id.recview_answers)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapterRecyclerLinear = QuestionMOListAdapter(this, newAnswersMO)
        recyclerView.adapter = adapterRecyclerLinear

        txtTopicName.text = topicName
        etxtMaxScore.setText(questionMaxScore.toString())
        etxtMaxScore.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (etxtMaxScore.text.toString() == "") {
                    skbAnswerScore.max = questionMaxScore * seekBarMultiplier
                } else {
                    skbAnswerScore.max = etxtMaxScore.text.toString().toInt() * seekBarMultiplier
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        })

        skbAnswerScore.max = etxtMaxScore.text.toString().toInt() * seekBarMultiplier
        skbAnswerScore.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(skb: SeekBar?, progress: Int, fromUser: Boolean) {
                currentAnswerScore = progress.toFloat() / seekBarMultiplier
                txtAnswerScore.text = currentAnswerScore.toString()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) { }
            override fun onStopTrackingTouch(p0: SeekBar?) { }
        })

        btnAddAnswer.setOnClickListener {
            val score = skbAnswerScore.progress.toFloat() / seekBarMultiplier
            val answerText = etxtQuestionAnswer.text.toString()
            val answer = AnswerMO(topicId, -1, score, answerText)
            newAnswersMO.add(0, answer)
            (recyclerView.adapter as QuestionMOListAdapter).notifyDataSetChanged()
            etxtQuestionAnswer.setText("")
        }

        btnAddQuestion.setOnClickListener {
            val dbHlp = DbHelper(applicationContext)
            val questionTitle = etxtQuestionText.text.toString()
            dbHlp.addMOQuestion(topicId, questionTitle,newAnswersMO)
            finish()
        }
    }

}