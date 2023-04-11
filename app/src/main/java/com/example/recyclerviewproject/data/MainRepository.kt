package com.example.recyclerviewproject.data

import retrofit2.Call


class MainRepository{

    suspend fun getInfoFromNasaAPI(sol:Int, page:Int): Call<NasaApiDto> {
        return NasaApiDataSource().getMarsImages(sol,page)
    }

}