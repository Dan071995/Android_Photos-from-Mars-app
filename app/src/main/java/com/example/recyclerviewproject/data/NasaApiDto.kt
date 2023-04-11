package com.example.recyclerviewproject.data

import com.example.recyclerviewproject.entity.NasaApiServerResponse
import com.example.recyclerviewproject.entity.PhotoInfo
import com.google.gson.annotations.SerializedName

data class NasaApiDto(
    @SerializedName("photos") override val photos: List<PhotoInfo>
):NasaApiServerResponse