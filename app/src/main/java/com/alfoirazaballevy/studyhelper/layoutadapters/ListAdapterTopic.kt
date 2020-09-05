package com.alfoirazaballevy.studyhelper.layoutadapters

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.domain.ListableTypeOne
import com.alfoirazaballevy.studyhelper.domain.Topic
import com.alfoirazaballevy.studyhelper.topic.dialogs.ListDialog

class ListAdapterTopic(
    var ctx : Context,
    var topics : ArrayList<Topic>
) : ListAdapter(ctx, convertToSOLTOne(topics)) {

    override fun onContainerLongClick(listableObject: ListableTypeOne, position: Int): Boolean {
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

}

fun convertToSOLTOne(topics: ArrayList<Topic>): java.util.ArrayList<ListableTypeOne> {
    val ltone = java.util.ArrayList<ListableTypeOne>()
    ltone.addAll(topics)
    return ltone
}