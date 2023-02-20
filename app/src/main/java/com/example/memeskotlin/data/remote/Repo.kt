package com.example.memeskotlin.data.remote

import androidx.room.withTransaction
import com.example.memeskotlin.data.local.MemesDao
import com.example.memeskotlin.data.local.MemesDataBase
import com.example.memeskotlin.domain.MemesRepoIn
import com.example.memeskotlin.networkBoundResource
import com.example.memeskotlin.domain.models.MemesResults
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class Repo @Inject constructor(private val apiServices : ApiServices,private val memesDao : MemesDao,private val db:MemesDataBase) : MemesRepoIn {

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
    override suspend fun returnMemes(): Flow<MemesResults> {
        return flow {
            try { emit(apiServices.returnMemes()) }
            catch (e : Exception){ throw e }
        }
    }
}