package com.example.memeskotlin.repositry

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.withTransaction
import com.example.memeskotlin.R
import com.example.memeskotlin.database.MemesDao
import com.example.memeskotlin.database.MemesDataBase
import com.example.memeskotlin.networkBoundResource
import com.example.memeskotlin.pojos.Memes
import com.example.memeskotlin.pojos.MemesResults
import com.example.memeskotlin.services.ApiServices
import com.example.memeskotlin.ui.FavoriteFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class Repo @Inject constructor(private val apiServices : ApiServices,private val memesDao : MemesDao,private val db:MemesDataBase) {
    suspend fun returnMemes(): Flow<MemesResults> {
        return flow {
            try { emit(apiServices.returnMemes()) }
            catch (e : Exception){ throw e }

        }
    }
    //for caching
    fun getMemes() = networkBoundResource(
        query = {
            memesDao.showDataFromDb()
        },
        fetch = {
            delay(2000)
            apiServices.returnMemes()

        },
        saveFetchResult = { memes ->
            db.withTransaction {
                memesDao.deleteAllMemes()
                memes.data?.memes?.let {
                    memesDao.insertMemes(it)
                 }
            }
        }
    )
}