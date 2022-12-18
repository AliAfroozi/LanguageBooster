package com.example.languagebooster.db

import android.provider.BaseColumns

object DBContract {

    // model 1 class
    class WordEntity : BaseColumns {
        companion object {
            val TABLE_NAME = "words"
            val COLUMN_KEYWORD = "keyword"
            val COLUMN_MEANING = "meaning"
            val CULUMN_STARED = "stared"
        }
    }

    // model 2 class ( There is not second one yet)

}