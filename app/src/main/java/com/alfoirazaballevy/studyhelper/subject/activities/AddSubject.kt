package com.alfoirazaballevy.studyhelper.subject.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.MainActivity
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.db.DbHelper

class AddSubject : AppCompatActivity() {

    companion object {
        private lateinit var txtSubjectName : EditText
        private lateinit var btnAddSubject : Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subject_add)

        txtSubjectName = findViewById(R.id.txt_subli_name)
        btnAddSubject = findViewById(R.id.btn_add_subject)

        txtSubjectName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                btnAddSubject.isEnabled = !txtSubjectName.text.toString().equals("")
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })

        btnAddSubject.setOnClickListener {
            val subjectName = txtSubjectName.text.toString()
            var dbHlp = DbHelper(applicationContext)
            dbHlp.addSubject(subjectName)

            val actIntSubj = Intent(this@AddSubject, MainActivity::class.java)
            startActivity(actIntSubj)
            finish()
        }
    }

}