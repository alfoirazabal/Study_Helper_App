package com.alfoirazaballevy.studyhelper.domain

import java.util.*

class Subject(subjId : Long, subjName : String, lstAcc : Date) {

    val id : Long = subjId
    val name : String = subjName
    var lastAccess : Date = lstAcc

}