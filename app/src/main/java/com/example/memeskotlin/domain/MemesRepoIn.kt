package com.example.memeskotlin.domain

import com.example.memeskotlin.domain.models.MemesResults
import kotlinx.coroutines.flow.Flow


interface MemesRepoIn {
    suspend fun returnMemes(): Flow<MemesResults>
}