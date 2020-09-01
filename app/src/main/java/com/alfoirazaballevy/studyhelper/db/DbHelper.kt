package com.alfoirazaballevy.studyhelper.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alfoirazaballevy.studyhelper.domain.Subject
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
        val Query_Table = """
            CREATE TABLE Subject(
                SubjectId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                SubjectName CHAR(30) NOT NULL,
                LastAccess DATE NOT NULL DEFAULT CURRENT_TIMESTAMP
            );
            CREATE TABLE Topic(
                TopicId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                TopicName CHAR(30) NOT NULL,
                SubjectId CHAR(30) NOT NULL,
                LastAccess DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (SubjectId) REFERENCES Subject(SubjectId) ON UPDATE CASCADE ON DELETE CASCADE
            );
            CREATE TABLE TopicAnswer(
                AnswerId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                TopicId INTEGER NOT NULL,
                FOREIGN KEY (TopicId) REFERENCES Topic(TopicId) ON UPDATE CASCADE ON DELETE CASCADE
            );
            CREATE TABLE TrueOrFalse(
                AnswerId INTEGER NOT NULL,
                QuestionPositive CHAR(120) NOT NULL,
                QuestionNegative CHAR(120) NOT NULL,
                ResultPositive CHAR(1) NOT NULL,
                Score REAL(3, 1) NOT NULL DEFAULT 1,
                FOREIGN KEY (AnswerId) REFERENCES TopicAnswer(AnswerId) ON UPDATE CASCADE ON DELETE CASCADE
            );
            CREATE TABLE MultOpc(
                AnswerId INTEGER NOT NULL,
                AnswerText CHAR(120) NOT NULL,
                Score REAL(3, 1) NOT NULL DEFAULT 1,
                FOREIGN KEY (AnswerId) REFERENCES TopicAnswer(AnswerId) ON UPDATE CASCADE ON DELETE CASCADE
            );
            CREATE TABLE TextualQuestion(
                AnswerId INTEGER NOT NULL,
                Question CHAR(120) NOT NULL,
                Score REAL(3, 1) NOT NULL DEFAULT 1,
                FOREIGN KEY (AnswerId) REFERENCES TopicAnswer(AnswerId) ON UPDATE CASCADE ON DELETE CASCADE
            );
            CREATE TABLE Result(
                ResultId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                TopicId INTEGER NOT NULL,
                Description CHAR(120) NOT NULL,
                Date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (TopicId) REFERENCES Topic(TopicId) ON UPDATE CASCADE ON DELETE CASCADE
            );
            CREATE TABLE ResultAnswer(
                ResultId INTEGER NOT NULL,
                AnswerId INTEGER NOT NULL,
                Score REAL(3, 1) NOT NULL DEFAULT 0,
                FOREIGN KEY (ResultId) REFERENCES Result(ResultId) ON UPDATE CASCADE ON DELETE CASCADE,
                FOREIGN KEY (AnswerId) REFERENCES TopicAnswer(AnswerId) ON UPDATE CASCADE ON DELETE CASCADE
            );
        """.trimIndent()
        db!!.execSQL(Query_Table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val Query_Drop = """
            DROP TABLE TrueOrFalse;
            DROP TABLE MultOpc;
            DROP TABLE TextualQuestion;
            DROP TABLE TopicAnswer;
            DROP TABLE ResultAnswer;
            DROP TABLE Result;
            DROP TABLE Topic;
            DROP TABLE Subject;
        """.trimIndent()
        db!!.execSQL(Query_Drop)
        onCreate(db)
    }

    // Subjects
    fun getSubjects() : ArrayList<Subject> {
        val lstSubjects = ArrayList<Subject>()
        val db = this.readableDatabase
        val cols = arrayOf("SubjectId", "SubjectName", "LastAccess")
        println("Printing Cols...")
        println(cols[0])
        println(cols[1])
        println("End cols printing...")
        val cursor = db.query("Subject", cols, null, null, null, null, "LastAccess DESC", null)

        val iId = cursor.getColumnIndex("SubjectId")
        val iName = cursor.getColumnIndex("SubjectName")
        val iLastAccess = cursor.getColumnIndex("LastAccess")

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
        conVals.put("SubjectName", subjectName)
        return db.insert("Subject", null, conVals)
    }

    fun deleteSubject(subjectId : Long) {
        val db = this.writableDatabase
        db.delete(
            "Subject",
            "SubjectId = ?",
            Array<String>(1){subjectId.toString()}
        )
    }

}