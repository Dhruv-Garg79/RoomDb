package com.examples.android.roomdb

import android.support.annotation.WorkerThread

class WordRepository(private val wordDao: WordDao) {
    val allWords = wordDao.getAllWords()

    @WorkerThread
    fun insert(word : Word){
        wordDao.insert(word)
    }
}