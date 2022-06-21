package com.example.memeskotlin.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_meme")
class Memes {
 var isFavorite :Boolean = false
  @PrimaryKey(autoGenerate = true)
   var id  : Int? = null
    var name : String? = null
    var url : String? = null
    var width : Int? = null
    var box_count :Int? = null

}