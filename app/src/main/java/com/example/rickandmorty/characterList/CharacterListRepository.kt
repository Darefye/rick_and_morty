package com.example.rickandmorty.characterList

import com.example.rickandmorty.api.RetrofitServices
import com.example.rickandmorty.models.Character
import kotlinx.coroutines.delay

class CharacterListRepository {
    suspend fun getCharacterList(page: Int): List<Character> {
        return RetrofitServices.searchApi.getAllCharacter(page).results
    }
}