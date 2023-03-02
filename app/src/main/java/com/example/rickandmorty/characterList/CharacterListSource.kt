package com.example.rickandmorty.characterList

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.models.Character

class CharacterListSource : PagingSource<Int, Character>() {

    private val repository = CharacterListRepository()

    override fun getRefreshKey(state: PagingState<Int, Character>): Int = FIRST_PAGE


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getCharacterList(page)

        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}