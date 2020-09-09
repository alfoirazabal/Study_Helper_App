package com.alfoirazaballevy.studyhelper.db

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alfoirazaballevy.studyhelper.domain.Answer
import com.alfoirazaballevy.studyhelper.domain.Subject
import com.alfoirazaballevy.studyhelper.domain.Topic
import com.alfoirazaballevy.studyhelper.domain.answertypes.AnswerMO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DbHelper(
            context: Context?
    ) : SQLiteOpenHelper(
            context,
            context!!.getExternalFilesDir(null)!!.absolutePath + "/" + DATABASE_NAME,
            null,
            DATABASE_VERSION
    ) {

    companion object {
        private val DATABASE_NAME = "studyHelper.db"
        private val DATABASE_VERSION = 1
    }

    override fun onOpen(db: SQLiteDatabase?) {
        db!!.execSQL("PRAGMA foreign_keys = ON")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val tablesCreator = DBTablesCreator(db)
        tablesCreator.generateTables()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE ${DBTrueOrFalse.TABLE_TITLE.identName}")
        db?.execSQL("DROP TABLE ${DBMultOpc.TABLE_TITLE.identName}")
        db?.execSQL("DROP TABLE ${DBTextualQuestion.TABLE_TITLE.identName}")
        db?.execSQL("DROP TABLE ${DBTopicAnswer.TABLE_TITLE.identName}")
        db?.execSQL("DROP TABLE ${DBResultAnswer.TABLE_TITLE.identName}")
        db?.execSQL("DROP TABLE ${DBResult.TABLE_TITLE.identName}")
        db?.execSQL("DROP TABLE ${DBTopic.TABLE_TITLE.identName}")
        db?.execSQL("DROP TABLE ${DBSubject.TABLE_TITLE.identName}")
        onCreate(db)
    }

    // Subjects
    fun getSubjects() : ArrayList<Subject> {
        val lstSubjects = ArrayList<Subject>()
        val db = this.readableDatabase
        val cols = arrayOf(
            DBSubject.COL_ID.identName,
            DBSubject.COL_NAME.identName,
            DBSubject.COL_LASTACCESS.identName
        )
        val cursor = db.query(
            DBSubject.TABLE_TITLE.identName,
            cols,
            null,
            null,
            null,
            null,
            "${DBSubject.COL_LASTACCESS.identName} DESC",
            null
        )

        val iId = cursor.getColumnIndex(cols[0])
        val iName = cursor.getColumnIndex(cols[1])
        val iLastAccess = cursor.getColumnIndex(cols[2])

        try {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(iId)
                val name = cursor.getString(iName)
                var lastAccess : Date
                try {
                    lastAccess = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        .parse(cursor.getString(iLastAccess))
                } catch (parsEx : java.text.ParseException) {
                    updateSubjectLastAccess(id)
                    lastAccess = Date()
                }
                val newSubj = Subject(id, name, lastAccess)
                lstSubjects.add(newSubj)
            }
        } finally {
            cursor.close()
        }
        db.close()
        return lstSubjects

    }

    fun addSubject(subjectName : String) : Long {
        val db = this.writableDatabase
        val conVals = ContentValues()
        conVals.put(DBSubject.COL_NAME.identName, subjectName)
        val insId = db.insert(DBSubject.TABLE_TITLE.identName, null, conVals)
        db.close()
        return insId
    }

    fun deleteSubject(subjectId : Long) {
        val db = this.writableDatabase
        db.delete(
            DBSubject.TABLE_TITLE.identName,
            "${DBSubject.COL_ID.identName} = ?",
            Array<String>(1){subjectId.toString()}
        )
        db.close()
    }

    fun updateSubjectLastAccess(subjectId : Long) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        val currDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date())
        contentValues.put(DBSubject.COL_LASTACCESS.identName, currDate);
        db.update(
            DBSubject.TABLE_TITLE.identName,
            contentValues,
            "${DBSubject.COL_ID.identName} = ?",
            arrayOf(subjectId.toString())
        )
        db.close()
    }

    // Topics
    fun getTopics(subjectId : Long) : ArrayList<Topic> {
        val lstTopics = ArrayList<Topic>()
        val db = this.readableDatabase
        val cols = arrayOf(
            DBTopic.COL_ID.identName,
            DBTopic.COL_NAME.identName,
            DBTopic.COL_SUBJECT.identName,
            DBTopic.COL_LASTACCESS.identName
        )
        val cursor = db.query(
            DBTopic.TABLE_TITLE.identName,
            cols,
            "${DBTopic.COL_SUBJECT.identName} = ?",
            arrayOf(subjectId.toString()),
            null,
            null,
            "${DBTopic.COL_LASTACCESS.identName} DESC",
            null
        )

        val iId = cursor.getColumnIndex(DBTopic.COL_ID.identName)
        val idName = cursor.getColumnIndex(DBTopic.COL_NAME.identName)
        val idLastAccess = cursor.getColumnIndex(DBTopic.COL_LASTACCESS.identName)

        try {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(iId)
                val name = cursor.getString(idName)
                val lastAccess = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        .parse(cursor.getString(idLastAccess))

                val newTopic = Topic(id, name, lastAccess, subjectId)

                lstTopics.add(newTopic)
            }
        } finally {
            cursor.close()
        }
        db.close()

        return lstTopics

    }

    fun addTopic(subjectId : Long, topicName : String) : Long {
        val db = this.writableDatabase
        val conVals = ContentValues()
        conVals.put(DBTopic.COL_SUBJECT.identName, subjectId)
        conVals.put(DBTopic.COL_NAME.identName, topicName)
        val insId = db.insert(DBTopic.TABLE_TITLE.identName, null, conVals)
        db.close()
        return insId
    }

    fun deleteTopic(topicId : Long) {
        val db = this.writableDatabase
        db.delete(
            DBTopic.TABLE_TITLE.identName,
            "${DBTopic.COL_ID.identName} = ?",
            Array<String>(1){topicId.toString()}
        )
        db.close()
    }

    fun countTopicQuestions(topicId : Long) : Long { // TopicQuestions or Answers
        val db = this.readableDatabase
        val count = DatabaseUtils.queryNumEntries(
            db, DBTopicAnswer.TABLE_TITLE.identName,
            "${DBTopicAnswer.COL_TOPIC_ID.identName} = ?",
            arrayOf(topicId.toString())
        )
        db.close()
        return count
    }

    fun updateTopicLastAccess(topicId: Long) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        val currDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date())
        contentValues.put(DBTopic.COL_LASTACCESS.identName, currDate)
        db.update(
            DBTopic.TABLE_TITLE.identName,
            contentValues,
            "${DBTopic.COL_ID.identName} = ?",
            arrayOf(topicId.toString())
        )
        db.close()
    }

    // Questions

    private fun addTopicQuestion(topicId: Long, questionText : String) : Long {
        val db = this.writableDatabase
        val conVals = ContentValues()
        conVals.put(DBTopicAnswer.COL_TOPIC_ID.identName, topicId)
        conVals.put(DBTopicAnswer.COL_QUESTION_TEXT.identName, questionText)
        val insId = db.insert(DBTopicAnswer.TABLE_TITLE.identName, null, conVals)
        db.close()
        return insId
    }

    // MO Questions

    fun addMOQuestion(topicId: Long, questionTitle: String, answers: ArrayList<AnswerMO>) {
        // 'val db'... must be after 'val newAnswerId'... so as NOT TO OPEN 2 DB TWICE!
        val newAnswerId = addTopicQuestion(topicId, questionTitle)
        val db = this.writableDatabase
        val itAnswers = answers.iterator()
        while(itAnswers.hasNext()) {
            val currAnswer = itAnswers.next()
            val conVals = ContentValues()
            conVals.put(DBMultOpc.COL_ANSWER_ID.identName, newAnswerId)
            conVals.put(DBMultOpc.COL_ANSWER_TEXT.identName, currAnswer.answerText)
            conVals.put(DBMultOpc.COL_SCORE.identName, currAnswer.score)
            db.insert(DBMultOpc.TABLE_TITLE.identName, null, conVals)
        }
        db.close()
    }

    // TF Questions

    fun addTFQuestion(
        topicId: Long,
        questionTitle: String,
        answerT: String,
        answerF: String,
        score : Float
    ) {
        val newAnswerId = addTopicQuestion(topicId, questionTitle)
        val db = this.writableDatabase
        val conVals = ContentValues()
        conVals.put(DBTrueOrFalse.COL_ANSWER_ID.identName, newAnswerId)
        conVals.put(DBTrueOrFalse.COL_QUESTION_POSITIVE.identName, answerT)
        conVals.put(DBTrueOrFalse.COL_QUESTION_NEGATIVE.identName, answerF)
        conVals.put(DBTrueOrFalse.COL_SCORE.identName, score)
        db.insert(DBTrueOrFalse.TABLE_TITLE.identName, null, conVals)
        db.close()
    }

    // Textual Questions

    fun addTextualQuestion(
        topicId : Long,
        questionTitle: String,
        questionAnswer: String,
        score: Float
    ) {
        val newAnswerId = addTopicQuestion(topicId, questionTitle)
        val db = this.writableDatabase
        val conVals = ContentValues()
        conVals.put(DBTextualQuestion.COL_ANSWER_ID.identName, newAnswerId)
        conVals.put(DBTextualQuestion.COL_ANSWER_TEXT.identName, questionAnswer)
        conVals.put(DBTextualQuestion.COL_SCORE.identName, score)
        db.insert(DBTextualQuestion.TABLE_TITLE.identName, null, conVals)
        db.close()
    }

}