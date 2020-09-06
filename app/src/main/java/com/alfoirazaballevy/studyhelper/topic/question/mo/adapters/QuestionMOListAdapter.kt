package com.alfoirazaballevy.studyhelper.topic.question.mo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.domain.answertypes.AnswerMO

class QuestionMOListAdapter(

    private val context : Context,
    private val answersMO : ArrayList<AnswerMO>

) : RecyclerView.Adapter<QuestionMOListAdapter.MyHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ) : MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_question_mo, viewGroup, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setIsRecyclable(false)
        val currAnswer = answersMO[position]
        MyHolder.txtResponse.text = currAnswer.answerText
        MyHolder.txtPuntaje.text = currAnswer.score.toString()
        MyHolder.containerAnswer.setOnClickListener {
            // Remove answer...
        }
    }

    override fun getItemCount(): Int {
        return answersMO.size
    }

    class MyHolder(view : View) : RecyclerView.ViewHolder(view) {

        companion object {
            lateinit var txtResponse : TextView
            lateinit var txtPuntaje : TextView
            lateinit var containerAnswer : ConstraintLayout
        }

        init {
            txtResponse = view.findViewById(R.id.txt_response)
            txtPuntaje = view.findViewById(R.id.txt_puntaje)
            containerAnswer = view.findViewById(R.id.container_answer_mo)
        }

    }

}