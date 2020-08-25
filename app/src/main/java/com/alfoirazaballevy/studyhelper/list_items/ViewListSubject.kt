package com.alfoirazaballevy.studyhelper.list_items

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.R

class ViewListSubject : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_subject)

        val txtSubject = findViewById<TextView>(R.id.txt_subject_name)


    }


}