package com.example.rickandmorty.characterList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.models.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterListViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val filterEnabled = MutableStateFlow(false)



    val pageCharacter: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { CharacterListSource() }
    ).flow.cachedIn(viewModelScope)
}