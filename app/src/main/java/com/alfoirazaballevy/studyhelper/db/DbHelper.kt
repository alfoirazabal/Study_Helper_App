package com.alfoirazaballevy.studyhelper.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alfoirazaballevy.studyhelper.domain.Subject
import com.alfoirazaballevy.studyhelper.domain.Topic
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

    override fun onCreate(db: SQLiteDatabase?) {
        val tablesCreator = DBTablesCreator(db)
        tablesCreator.generateTables()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // ❌️ WONT WORK! ❌️️ IT HAS TO BE DONE STMT BY STMT, NOT RUNNING ALL WITH db!!execSQL ❌️️
        val Query_Drop = """
            DROP TABLE ${DBTrueOrFalse.TABLE_TITLE.identName};
            DROP TABLE ${DBMultOpc.TABLE_TITLE.identName};
            DROP TABLE ${DBTextualQuestion.TABLE_TITLE.identName};
            DROP TABLE ${DBTopicAnswer.TABLE_TITLE.identName};
            DROP TABLE ${DBResultAnswer.TABLE_TITLE.identName};
            DROP TABLE ${DBResult.TABLE_TITLE.identName};
            DROP TABLE ${DBTopic.TABLE_TITLE.identName};
            DROP TABLE ${DBSubject.TABLE_TITLE.identName};
        """.trimIndent()
        db!!.execSQL(Query_Drop)
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

}