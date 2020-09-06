package com.alfoirazaballevy.studyhelper.domain.answertypes

import com.alfoirazaballevy.studyhelper.domain.Answer

class AnswerMO(
    private val topicId : Long,
    private val answerId : Long,
    val score : Float,
    val answerText : String
) : Answer(
    answerId,
    topicId,
    score
) { }