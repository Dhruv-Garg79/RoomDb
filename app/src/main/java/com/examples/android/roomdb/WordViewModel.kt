package com.examples.android.roomdb

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val wordRepository : WordRepository
    val allWords : LiveData<List<Word>>

    init {
        val wordDao = WordRoomDatabase.getDatabase(application, scope).wordDao()
        wordRepository = WordRepository(wordDao)
        allWords = wordRepository.allWords
    }

    fun insert(word: Word) = scope.launch(Dispatchers.IO) {
        wordRepository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}