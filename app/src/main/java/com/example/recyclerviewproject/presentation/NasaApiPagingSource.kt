package com.example.recyclerviewproject.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recyclerviewproject.constants.PAGE_SIZE
import com.example.recyclerviewproject.domain.GetPhotoArrayFromNasaAPIUseCase
import com.example.recyclerviewproject.entity.PhotoInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking



class NasaApiPagingSource (private val sol:Int=230) :PagingSource<Int, PhotoInfo>(){

    private val repository = GetPhotoArrayFromNasaAPIUseCase()

    override fun getRefreshKey(state: PagingState<Int, PhotoInfo>): Int? {
        return 1
    }

    //В этой функции загружаем данные и возвращаем их в виде LoadResult<Int, PhotoInfo>
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoInfo> {

        val page = params.key ?: 1
        PAGE_SIZE = page

        //Получаем и обрабатываем объект титпа Call от сервера синхронной функцией .execute()
        return kotlin.runCatching {
            runBlocking(Dispatchers.Default) { repository.execute(sol,page)}
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it!!,
                    prevKey = null,
                    nextKey = if(it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }
}