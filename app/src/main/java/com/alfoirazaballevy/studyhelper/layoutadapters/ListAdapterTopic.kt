package com.alfoirazaballevy.studyhelper.layoutadapters

import android.content.Context
import com.alfoirazaballevy.studyhelper.domain.ListableTypeOne
import com.alfoirazaballevy.studyhelper.domain.Subject
import com.alfoirazaballevy.studyhelper.domain.Topic
import com.alfoirazaballevy.studyhelper.topic.activities.ListTopics

class ListAdapterTopic(
    ctx : Context,
    lstTopics: ArrayList<Topic>
) : ListAdapter(ctx, convertToSOLTOne(lstTopics)) {

    

}

fun convertToSOLTOne(topics: ArrayList<Topic>): java.util.ArrayList<ListableTypeOne> {
    val ltone = java.util.ArrayList<ListableTypeOne>()
    ltone.addAll(topics)
    return ltone
}