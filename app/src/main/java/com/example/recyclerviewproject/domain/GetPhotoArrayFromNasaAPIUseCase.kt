package com.example.recyclerviewproject.domain

import com.example.recyclerviewproject.data.MainRepository
import com.example.recyclerviewproject.entity.PhotoInfo
import kotlinx.coroutines.delay


class GetPhotoArrayFromNasaAPIUseCase{
    suspend fun execute(sol:Int,page:Int): List<PhotoInfo>? {
        //delay(2000)
        return MainRepository().getInfoFromNasaAPI(sol,page).execute().body()?.photos
    }
}