package com.example.memeskotlin

import android.content.Context
import androidx.room.Room
import com.example.memeskotlin.database.MemesDao
import com.example.memeskotlin.database.MemesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideMemesDatabase(@ApplicationContext appContext: Context): MemesDataBase {
        return Room.databaseBuilder(
            appContext,
           MemesDataBase::class.java ,
            "fav_db"
        ).fallbackToDestructiveMigration()
            .build()

    }
    @Provides
    fun provideMovieDao(memesDataBase: MemesDataBase): MemesDao {
        return memesDataBase.dataBaseCreated()
    }



}