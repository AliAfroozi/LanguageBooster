package com.example.languagebooster.db.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.languagebooster.db.DBContract
import com.example.languagebooster.models.Word
import java.lang.Exception

class WordDBHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SQL_DELETE_QUERY)
        onCreate(db)
    }

    fun insertWord(word: Word): Boolean {
        return try {
            val db = this.writableDatabase
            val values = ContentValues()
            values.put(DBContract.WordEntity.COLUMN_KEYWORD, word.keyword)
            values.put(DBContract.WordEntity.COLUMN_MEANING, word.meaning)
            values.put(DBContract.WordEntity.CULUMN_STARED, if (word.stared) 1 else 0 )
            db.insert(DBContract.WordEntity.TABLE_NAME, null, values)
            true
        } catch (exception: Exception) {
            false
        }
    }


    fun updateWord(word: Word): Boolean {
        return try {
            val db = this.writableDatabase
            val values = ContentValues()
            values.put(DBContract.WordEntity.COLUMN_MEANING, word.meaning)
            values.put(DBContract.WordEntity.CULUMN_STARED, if (word.stared) 1 else 0 )
            val where = "${DBContract.WordEntity.COLUMN_KEYWORD} = ?"
            val args = arrayOf(word.keyword)
            db.update(DBContract.WordEntity.TABLE_NAME , values , where , args)
            true
        } catch (exception: Exception) {
            false
        }
    }


    @SuppressLint("Range")
    fun getAllWords(): ArrayList<Word> {
        val words = ArrayList<Word>()
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM ${DBContract.WordEntity.TABLE_NAME}", null)
        } catch (e: Exception) {
            db.execSQL(SQL_CREATE_QUERY)
            return ArrayList()
        }
        var keyword: String
        var meaning: String
        var stared: Boolean
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                keyword =
                    cursor.getString(cursor.getColumnIndex(DBContract.WordEntity.COLUMN_KEYWORD))
                meaning =
                    cursor.getString(cursor.getColumnIndex(DBContract.WordEntity.COLUMN_MEANING))
                stared =
                    cursor.getInt(cursor.getColumnIndex(DBContract.WordEntity.CULUMN_STARED)) == 1
                words.add(Word(keyword, meaning, stared))
                cursor.moveToNext()
            }
            cursor.close()
        }

        return words

    }

    companion object {

        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "LanguageBooster.db"

        private val SQL_CREATE_QUERY =
            "CREATE TABLE ${DBContract.WordEntity.TABLE_NAME}  (" +
                    "${DBContract.WordEntity.COLUMN_KEYWORD} TEXT  PRIMARY KEY" +
                    ", ${DBContract.WordEntity.COLUMN_MEANING}  TEXT" +
                    ", ${DBContract.WordEntity.CULUMN_STARED} NUMERIC)"


        private val SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS ${DBContract.WordEntity.TABLE_NAME}"
    }

}