package com.example.memeskotlin.viewmodels

import android.util.Log
import com.example.memeskotlin.pojos.Memes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.memeskotlin.repositry.DataBaseRepo
import com.example.memeskotlin.repositry.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class MemesViewModel @Inject constructor(private val repo: Repo, private val dbRepo: DataBaseRepo) : ViewModel() {
    val memesData = repo.getMemes().asLiveData()
    /*val memesLiveStateFlow: MutableStateFlow<MutableList<Memes>> = MutableStateFlow(mutableListOf())
    fun returnData() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.returnMemes().onStart { }
                .onCompletion {}
                .catch {
                    it.message
                }
                .collect {
                    print(it.success)
                    print(it.data)
                    it.data?.memes?.let {

                            it ->
                        memesLiveStateFlow.emit(it)

                    }

                }
        }
    }*/

    fun insertDataFromDb(memes: Memes) {
        viewModelScope.launch(Dispatchers.IO) {

                dbRepo.insertData(memes).catch {
                        Log.d("bb",it.localizedMessage)
                }
                    .collect()
            }


        }



}