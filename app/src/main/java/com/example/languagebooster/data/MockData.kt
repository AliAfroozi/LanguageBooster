package com.example.languagebooster.data

import com.example.languagebooster.models.Word

object MockData {
    private val list = ArrayList<Word>()

    fun addWord(word: Word) {
        list.add(word)
    }

    fun getList(): ArrayList<Word> {
        return list
    }
}