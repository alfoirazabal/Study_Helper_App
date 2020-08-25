package com.alfoirazaballevy.studyhelper.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(
            context: Context?,
            name: String?,
            factory: SQLiteDatabase.CursorFactory?,
            version: Int
    ) : SQLiteOpenHelper(
            context,
            context!!.getExternalFilesDir(null)!!.absolutePath + "/" + DATABASE_NAME,
            factory,
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
                SubjectName CHAR(30) NOT NULL
            );
            CREATE TABLE Topic(
                TopicId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                TopicName CHAR(30) NOT NULL,
                SubjectId CHAR(30) NOT NULL,
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
        TODO("Not yet implemented")
    }

}