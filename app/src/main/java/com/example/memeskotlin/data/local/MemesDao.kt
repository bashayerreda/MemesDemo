package com.example.memeskotlin.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memeskotlin.domain.models.Memes
import kotlinx.coroutines.flow.Flow

@Dao
interface MemesDao {
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend  fun insertDataFromRoom(memes : Memes) : Long

 @Query("select * from fav_meme where isFavorite = 1 ")
 suspend fun selectDataFromDb(): MutableList<Memes>

 @Query("delete from fav_meme where name = :memeName & isFavorite = 0")
 suspend  fun deleteDataFromDb(memeName : String)

 //for caching

   @Query("select * from fav_meme")
   fun showDataFromDb(): Flow<List<Memes>>

    @Query("DELETE FROM fav_meme")
    suspend fun deleteAllMemes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemes(memes: MutableList<Memes>)
}