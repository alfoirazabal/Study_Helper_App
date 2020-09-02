package com.alfoirazaballevy.studyhelper.domain

import java.util.*

class Topic(
    override val id: Long, override val name: String, override val lastAccess: Date,
    val subjectId : Long
) : ListableTypeOne()