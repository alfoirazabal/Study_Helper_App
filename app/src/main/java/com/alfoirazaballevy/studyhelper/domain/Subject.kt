package com.alfoirazaballevy.studyhelper.domain

import java.util.*

class Subject(
    override val id: Long, override val name: String, override val lastAccess: Date
) : ListableTypeOne() { }