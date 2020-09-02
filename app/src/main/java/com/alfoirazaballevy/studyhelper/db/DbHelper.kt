package com.alfoirazaballevy.studyhelper.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alfoirazaballevy.studyhelper.domain.Subject
import com.alfoirazaballevy.studyhelper.domain.Topic
import java.sql.Date
import java.text.SimpleDateFormat

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
        val tablesCreator = DBTablesCreator()
        val tablesStr = tablesCreator.generateTables()
        db!!.execSQL(tablesStr)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
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
        val cursor = db.query("${DBSubject.TABLE_TITLE.identName}", cols, null, null, null, null, "${DBSubject.COL_LASTACCESS.identName} DESC", null)

        val iId = cursor.getColumnIndex(cols[0])
        val iName = cursor.getColumnIndex(cols[1])
        val iLastAccess = cursor.getColumnIndex(cols[2])

        try {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(iId)
                val name = cursor.getString(iName)
                var lastAccess = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        .parse(cursor.getString(iLastAccess))
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
        return db.insert(DBSubject.TABLE_TITLE.identName, null, conVals)
    }

    fun deleteSubject(subjectId : Long) {
        val db = this.writableDatabase
        db.delete(
            DBSubject.TABLE_TITLE.identName,
            "${DBSubject.COL_ID.identName} = ?",
            Array<String>(1){subjectId.toString()}
        )
    }

    // Topics
    /*fun getTopics(subjectId : Long) : ArrayList<Topic> {
        val lstTopics = ArrayList<Topic>()
        val db = this.readableDatabase
        val cols = arrayOf("TopicId", "TopicName", "SubjectId", "LastAccess")
        val cursor = db.query("Topic", cols, "SubjectId", arrayOf(subjectId.toString()), null, null, "LastAccess DESC", null)

        val iTId = cursor.getColumnIndex("TopicId", )

    }*/

}