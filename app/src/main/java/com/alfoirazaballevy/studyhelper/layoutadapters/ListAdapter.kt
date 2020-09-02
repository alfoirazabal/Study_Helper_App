package com.alfoirazaballevy.studyhelper.layoutadapters

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alfoirazaballevy.studyhelper.R
import com.alfoirazaballevy.studyhelper.domain.ListableTypeOne
import com.alfoirazaballevy.studyhelper.subject.dialogs.ListDialog

abstract class ListAdapter(
    ctx: Context,
    lstObjects: ArrayList<ListableTypeOne>
) : RecyclerView.Adapter<ListAdapter.MyHolder>() {

    protected var context : Context = ctx
    var lstObjects : ArrayList<ListableTypeOne> = lstObjects

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_subject_topic, parent, false)

        val holder =
            MyHolder(
                view
            )
        return holder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder.setIsRecyclable(false)

        val listableObject = lstObjects[position]
        MyHolder.subjectName.text = listableObject.name
        MyHolder.lastAccess.text = listableObject.lastAccess.toLocaleString()
        MyHolder.containerSubject.setOnLongClickListener {
            onContainerLongClick(listableObject, position)
        }

        MyHolder.containerSubject.setOnClickListener {
            onContainerClick(listableObject, position)
        }

    }

    open fun onContainerLongClick(listableObject : ListableTypeOne, position: Int) : Boolean {
        return true
    }

    open fun onContainerClick(listableObject: ListableTypeOne, position: Int) : Boolean {
        return true
    }

    open fun updateDataset() {
        super.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return lstObjects.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {
            lateinit var subjectName : TextView
            lateinit var lastAccess : TextView
            lateinit var containerSubject : ConstraintLayout
        }

        init {
            subjectName = itemView.findViewById(
                R.id.etxt_subli_name
            )
            lastAccess = itemView.findViewById(
                R.id.txt_subli_ultimo_acceso
            )
            containerSubject = itemView.findViewById(
                R.id.container_subject
            )
        }

    }

}