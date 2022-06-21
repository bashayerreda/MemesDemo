package com.example.memeskotlin.repositry

import com.example.memeskotlin.database.MemesDao
import com.example.memeskotlin.pojos.Memes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataBaseRepo @Inject constructor(private val memesDao : MemesDao) {
   suspend  fun insertData(memes : Memes) : Flow<Long>{
  return flow {
      emit(memesDao.insertDataFromRoom(memes))
  }


   }
   suspend fun selectData(): Flow<MutableList<Memes>> {
        return flow {

                emit(memesDao.selectDataFromDb())


        }
    }

   suspend fun deleteDataFromDb(memeName : String) {
        flow {
           emit(memesDao.deleteDataFromDb(memeName))
       }
   }


}