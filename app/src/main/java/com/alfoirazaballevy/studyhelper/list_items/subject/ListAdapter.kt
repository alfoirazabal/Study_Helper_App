package com.alfoirazaballevy.studyhelper.list_items.subject

import android.content.Context
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.domain.Subject

class ListAdapter(ctx: Context, lstSubjs: ArrayList<Subject>) :
    RecyclerView.Adapter<ListAdapter.MyHolder>() {

    private var context : Context = ctx
    private var lstSubjects : ArrayList<Subject> = lstSubjs

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_subject, parent, false)

        val holder =
            MyHolder(
                view
            )
        return holder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder.setIsRecyclable(false)

        val subject = lstSubjects[position]
        MyHolder.subjectName.text = subject.name
        MyHolder.lastAccess.text = subject.lastAccess.toLocaleString()
        MyHolder.containerSubject.setOnLongClickListener(View.OnLongClickListener {
            ListDialog(subject.id, subject.name).show(
                (context as AppCompatActivity).supportFragmentManager, "DisplayListFragment"
            )
            true
        })

    }

    override fun getItemCount(): Int {
        return lstSubjects.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {
            lateinit var subjectName : TextView
            lateinit var lastAccess : TextView
            lateinit var containerSubject : ConstraintLayout
        }

        init {
            subjectName = itemView.findViewById(R.id.txt_subject_name)
            lastAccess = itemView.findViewById(R.id.txt_ultimo_acceso)
            containerSubject = itemView.findViewById(R.id.container_subject)
        }

    }

}