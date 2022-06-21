package com.example.memeskotlin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memeskotlin.repositry.DataBaseRepo
import com.example.memeskotlin.pojos.Memes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavoritesViewModel @Inject constructor(private val dbRepo : DataBaseRepo) : ViewModel(){
    val memesLiveStateFlowDb : MutableStateFlow<MutableList<Memes>> = MutableStateFlow(mutableListOf())
    fun takeDataFromDBRepo() {
        viewModelScope.launch(Dispatchers.IO) {
                dbRepo.selectData().collect {

                    memesLiveStateFlowDb.emit(it)

                }
        }

    }
        fun deleteDataFromDb(memeName : String){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteDataFromDb(memeName)
        }
        }
    }
