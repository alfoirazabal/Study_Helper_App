package com.alfoirazaballevy.studyhelper.topic.question.mo.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.R

class AddMOQuestion(

) : AppCompatActivity() {

    private var topicId : Long = -1
    private var topicName : String = ""

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_mo_add)
    }

}