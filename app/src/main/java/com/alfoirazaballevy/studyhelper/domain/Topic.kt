package com.alfoirazaballevy.studyhelper.domain

import java.util.*

class Topic(
    theTopicId : Long,
    theTopicName : String,
    theSubjectId : Long,
    theLastAccess : Date
) {

    val topicId = theTopicId
    val topicName = theTopicName
    val subjectId = theSubjectId
    val theLastAccess = theLastAccess

}