package com.example.memeskotlin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memeskotlin.database.MemesDao
import com.example.memeskotlin.pojos.Memes

@Database(entities = [Memes::class], version = 2, exportSchema = false)
abstract class MemesDataBase : RoomDatabase() {
   abstract fun dataBaseCreated(): MemesDao

}
