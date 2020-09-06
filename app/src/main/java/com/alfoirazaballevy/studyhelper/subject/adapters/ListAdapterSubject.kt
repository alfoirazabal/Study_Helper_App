package com.alfoirazaballevy.studyhelper.subject.adapters

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.db.DbHelper
import com.alfoirazaballevy.studyhelper.domain.ListableTypeOne
import com.alfoirazaballevy.studyhelper.domain.Subject
import com.alfoirazaballevy.studyhelper.layoutadapters.ListAdapter
import com.alfoirazaballevy.studyhelper.subject.dialogs.ListDialog
import com.alfoirazaballevy.studyhelper.topic.activities.ListTopics
import java.util.ArrayList

class ListAdapterSubject(
    var ctx : Context,
    var subjects : ArrayList<Subject>
) : ListAdapter(ctx, convertToSOLTOne(subjects)) {

    override fun onContainerLongClick(listableObject : ListableTypeOne, position: Int) : Boolean {
        ListDialog(
            listableObject.id,
            listableObject.name,
            position,
            this
        ).show(
            (ctx as AppCompatActivity).supportFragmentManager, "DisplayListFragment"
        )
        return true
    }

    override fun onContainerClick(listableObject: ListableTypeOne, position: Int): Boolean {
        val dbHlp = DbHelper(context)
        dbHlp.updateSubjectLastAccess(listableObject.id)
        val viewTopics = Intent(context, ListTopics::class.java)
        viewTopics.putExtra("SUBJID", listableObject.id)
        viewTopics.putExtra("SUBJNAME", listableObject.name)
        context.startActivity(viewTopics)
        return true
    }

}

fun convertToSOLTOne(subjects: ArrayList<Subject>): ArrayList<ListableTypeOne> {
    val ltone = ArrayList<ListableTypeOne>()
    ltone.addAll(subjects)
    return ltone
}
