package com.example.memeskotlin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memeskotlin.domain.models.Memes

@Database(entities = [Memes::class], version = 2, exportSchema = false)
abstract class MemesDataBase : RoomDatabase() {
   abstract fun dataBaseCreated(): MemesDao

}
