package com.example.memeskotlin.di

import android.content.Context
import androidx.room.Room
import com.example.memeskotlin.util.Constants
import com.example.memeskotlin.data.local.MemesDao
import com.example.memeskotlin.data.local.MemesDataBase
import com.example.memeskotlin.data.remote.ApiServices
import com.example.memeskotlin.data.remote.Repo
import com.example.memeskotlin.domain.MemesRepoIn
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    companion object{
        @Provides
        fun returnRetrofitInstance(): ApiServices {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ApiServices::class.java)
        }
    }
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

  /* @Binds
   abstract fun getMemesRepo(defaultRepo : Repo) : MemesRepoIn
*/
}