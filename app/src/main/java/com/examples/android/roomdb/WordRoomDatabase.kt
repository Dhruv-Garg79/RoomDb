package com.examples.android.roomdb

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao() : WordDao

    companion object {
        @Volatile
        private var INSTANCE : WordRoomDatabase? = null

        fun getDatabase(context : Context, scope : CoroutineScope) : WordRoomDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                                context.applicationContext,
                                WordRoomDatabase::class.java,
                            "word_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let {database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.wordDao())
                    }
                }
            }

            private fun populateDatabase(wordDao: WordDao) {
                wordDao.deleteAll()

                var word = Word("Hello")
                wordDao.insert(word)
                word = Word("World!")
                wordDao.insert(word)
            }
        }
    }
}