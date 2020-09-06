package com.alfoirazaballevy.studyhelper.topic.adapters

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.alfoirazaballevy.studyhelper.db.DbHelper
import com.alfoirazaballevy.studyhelper.domain.ListableTypeOne
import com.alfoirazaballevy.studyhelper.domain.Topic
import com.alfoirazaballevy.studyhelper.layoutadapters.ListAdapter
import com.alfoirazaballevy.studyhelper.topic.activities.TopicPanel
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

    override fun onContainerClick(listableObject: ListableTypeOne, position: Int): Boolean {
        val dbHlp = DbHelper(context)
        dbHlp.updateTopicLastAccess(listableObject.id)
        val viewTopicPanel = Intent(context, TopicPanel::class.java)
        viewTopicPanel.putExtra("TOPICID", listableObject.id)
        viewTopicPanel.putExtra("TOPICNAME", listableObject.name)
        context.startActivity(viewTopicPanel)
        return true
    }

}

fun convertToSOLTOne(topics: ArrayList<Topic>): java.util.ArrayList<ListableTypeOne> {
    val ltone = java.util.ArrayList<ListableTypeOne>()
    ltone.addAll(topics)
    return ltone
}