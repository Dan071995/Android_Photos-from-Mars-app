package com.example.recyclerviewproject.data

import com.example.recyclerviewproject.constants.NASA_API_HOST
import com.example.recyclerviewproject.constants.NASA_API_PATH
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class NasaApiDataSource{

    suspend fun getMarsImages(sol: Int,page: Int):Call<NasaApiDto>{
        return RetrofitInstance.nasaImagesApi.getInfo(sol,page)
    }

}

private object RetrofitInstance{
    //Создадим клиен okHttp - с помощью него, мы будем мониторить (в логах) какой именно запрос на сервер мы отправили
    //и что именно нам вернулось.
    //Создаем ИНТЕРСЕПТОР
    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY //Будем выводить в ЛОГ только ТЕЛО ответа
    }
    //Создаем okHttp КЛИЕНТ:
    val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    //Экземпляр Ретрофита NASA API
    private val retrofitNasaAPI = Retrofit.Builder()
        .baseUrl(NASA_API_HOST) //С этим URL работает Postman
        //.baseUrl("https://api.nasa.gov/mars-photos/api") //URL из примера, с ним не работает даже Postman
        .client(okHttpClient) //Передаем КЛИЕНТ для перехвата и вывода ТЕЛА ответа в ЛОГ
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Ретрофит сервис NASA API:
    val nasaImagesApi:NasaImagesApi = retrofitNasaAPI.create(NasaImagesApi::class.java)

}
private interface NasaImagesApi{
    @GET(NASA_API_PATH)
    fun getInfo(@Query("sol") sol:Int = 1,@Query("page") page:Int = 1): Call<NasaApiDto>
}
