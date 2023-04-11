package com.example.recyclerviewproject.entity

import com.google.gson.annotations.SerializedName

interface NasaApiServerResponse {
    val photos:List<PhotoInfo>
}

data class PhotoInfo(
    @SerializedName("id") val id:Int,
    @SerializedName("sol") val sol:Int,
    @SerializedName("camera") val cameraInfo:CameraInfo,
    @SerializedName("img_src") val imgSrc:String,
    @SerializedName("earth_date") val earthDate:String,
    @SerializedName("rover") val roverInfo:RoverInfo,

    )

data class CameraInfo(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("rover_id") val roverId:Int,
    @SerializedName("full_name") val fullName:String
)

data class RoverInfo(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("landing_date") val landingDate:String,
    @SerializedName("launch_date") val launchDate:String,
    @SerializedName("status") val status:String,
)