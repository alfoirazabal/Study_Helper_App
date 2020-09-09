package com.alfoirazaballevy.studyhelper.db

import android.database.sqlite.SQLiteDatabase
import java.util.*
import kotlin.collections.ArrayList

private interface DBDefVals {
    val identName : String?
    val typeArgs : String?
}

enum class DBSubject(override val identName : String, override val typeArgs : String) : DBDefVals {
    TABLE_TITLE("Subject", ""),
    COL_ID("SubjectId", "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"),
    COL_NAME("SubjectName", "CHAR(30) NOT NULL"),
    COL_LASTACCESS("LastAccess", "DATE NOT NULL DEFAULT CURRENT_TIMESTAMP")
}
enum class DBTopic(override val identName : String, override val typeArgs : String) : DBDefVals {
    TABLE_TITLE("Topic", ""),
    COL_ID("TopicId", "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"),
    COL_NAME("TopicName", "CHAR(30) NOT NULL"),
    COL_SUBJECT("SubjectId", "CHAR(30) NOT NULL"),
    COL_LASTACCESS("LastAccess", "DATE NOT NULL DEFAULT CURRENT_TIMESTAMP"),
    CONST_FK_SUBJECT("", "FOREIGN KEY (SubjectId) REFERENCES Subject(SubjectId) ON UPDATE CASCADE ON DELETE CASCADE")
}
enum class DBTopicAnswer(override val identName : String, override val typeArgs: String) : DBDefVals {
    TABLE_TITLE("TopicAnswer", ""),
    COL_ANSWER_ID("AnswerId", "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"),
    COL_TOPIC_ID("TopicId", "INTEGER NOT NULL"),
    COL_QUESTION_TEXT("QuestionText", "CHAR(120) NOT NULL"),
    CONST_FK_TOPIC("", "FOREIGN KEY (TopicId) REFERENCES Topic(TopicId) ON UPDATE CASCADE ON DELETE CASCADE")
}
enum class DBTrueOrFalse(override val identName : String, override val typeArgs: String) : DBDefVals {
    TABLE_TITLE("TrueOrFalse", ""),
    COL_ANSWER_ID("AnswerId", "INTEGER NOT NULL"),
    COL_QUESTION_POSITIVE("QuestionPositive", "CHAR(120) NOT NULL"),
    COL_QUESTION_NEGATIVE("QuestionNegative", "CHAR(120) NOT NULL"),
    COL_SCORE("Score", "REAL(3, 1) NOT NULL DEFAULT 1"),
    CONST_FK_TOPIC_ANSWER("", "FOREIGN KEY (AnswerId) REFERENCES TopicAnswer(AnswerId) ON UPDATE CASCADE ON DELETE CASCADE")
}
enum class DBMultOpc(override val identName : String, override val typeArgs: String) : DBDefVals {
    TABLE_TITLE("MultOpc", ""),
    COL_ANSWER_ID("AnswerId", "INTEGER NOT NULL"),
    COL_ANSWER_TEXT("AnswerText", "CHAR(120) NOT NULL"),
    COL_SCORE("Score", "REAL(3, 1) NOT NULL DEFAULT 1"),
    CONST_FK_TOPIC_ANSWER("", "FOREIGN KEY (AnswerId) REFERENCES TopicAnswer(AnswerId) ON UPDATE CASCADE ON DELETE CASCADE")
}
enum class DBTextualQuestion(override val identName : String, override val typeArgs: String) : DBDefVals {
    TABLE_TITLE("TextualQuestion", ""),
    COL_ANSWER_ID("AnswerId", "INTEGER NOT NULL"),
    COL_ANSWER_TEXT("AnswerText", "CHAR(120) NOT NULL"),
    COL_SCORE("Score", "REAL(3, 1) NOT NULL DEFAULT 1"),
    CONST_FK_TOPIC_ANSWER("", "FOREIGN KEY (AnswerId) REFERENCES TopicAnswer(AnswerId) ON UPDATE CASCADE ON DELETE CASCADE")
}
enum class DBResult(override val identName : String, override val typeArgs: String) : DBDefVals {
    TABLE_TITLE("Result", ""),
    COL_RESULT_ID("ResultId", "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"),
    COL_TOPIC_ID("TopicId", "INTEGER NOT NULL"),
    COL_DESCRIPTION("Description", "CHAR(120) NOT NULL"),
    COL_DATE("Date", "DATE NOT NULL DEFAULT CURRENT_TIMESTAMP"),
    CONST_FK_TOPIC("", "FOREIGN KEY (TopicId) REFERENCES Topic(TopicId) ON UPDATE CASCADE ON DELETE CASCADE")
}
enum class DBResultAnswer (override val identName : String, override val typeArgs: String) : DBDefVals {
    TABLE_TITLE("ResultAnswer", ""),
    COL_RESULT_ID("ResultId", "INTEGER NOT NULL"),
    COL_ANSWER_ID("AnswerId", "INTEGER NOT NULL"),
    COL_SCORE("Score", "REAL(3, 1) NOT NULL DEFAULT 0"),
    CONST_FK_RESULT("", "FOREIGN KEY (ResultId) REFERENCES Result(ResultId) ON UPDATE CASCADE ON DELETE CASCADE"),
    CONST_FK_TOPIC_ANSWER("", "FOREIGN KEY (AnswerId) REFERENCES TopicAnswer(AnswerId) ON UPDATE CASCADE ON DELETE CASCADE")
}

class DBTablesCreator(val db : SQLiteDatabase?) {

    fun generateTables() {

        db?.execSQL(createFromTableEntity(DBSubject.values()))
        db?.execSQL(createFromTableEntity(DBTopic.values()))
        db?.execSQL(createFromTableEntity(DBTopicAnswer.values()))
        db?.execSQL(createFromTableEntity(DBTrueOrFalse.values()))
        db?.execSQL(createFromTableEntity(DBMultOpc.values()))
        db?.execSQL(createFromTableEntity(DBTextualQuestion.values()))
        db?.execSQL(createFromTableEntity(DBResult.values()))
        db?.execSQL(createFromTableEntity(DBResultAnswer.values()))

    }

    private fun<T : DBDefVals?> createFromTableEntity(dbVals : Array<T>) : String
    {
        var stringConst = ""

        stringConst += "CREATE TABLE ${dbVals[0]!!.identName}("

        for (x in 1 until dbVals.size) {
            stringConst += "${dbVals[x]?.identName} ${dbVals[x]!!.typeArgs}"
            if (x != dbVals.size - 1) stringConst += ", "
        }

        stringConst += ");"

        return stringConst

    }

}