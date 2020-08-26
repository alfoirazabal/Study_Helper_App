package com.alfoirazaballevy.studyhelper.list_items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.domain.Subject
import java.time.ZoneId

class ViewListSubjectAdapter(ctx: Context, lstSubjs: ArrayList<Subject>) :
    RecyclerView.Adapter<ViewListSubjectAdapter.MyHolder>() {

    private var context : Context = ctx
    private var lstSubjects : ArrayList<Subject> = lstSubjs

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_subject, parent, false)

        val holder = MyHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewListSubjectAdapter.MyHolder, position: Int) {
        val subject = lstSubjects.get(position)
        MyHolder.subjectName.text = subject.name
        MyHolder.lastAccess.text = subject.lastAccess.toLocaleString()
    }

    override fun getItemCount(): Int {
        return lstSubjects.size
    }

    class MyHolder : RecyclerView.ViewHolder {

        companion object {
            lateinit var subjectName : TextView
            lateinit var lastAccess : TextView
        }

        constructor(itemView : View) : super(itemView) {
            subjectName = itemView.findViewById(R.id.txt_subject_name)
            lastAccess = itemView.findViewById(R.id.txt_ultimo_acceso)
        }

    }

}