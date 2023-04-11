package com.example.recyclerviewproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recyclerviewproject.entity.PhotoInfo
import kotlinx.coroutines.flow.Flow

class MainFragmentViewModel():ViewModel() {

    private var sole = 230

    val pagedPhotoFromNasaApi: Flow<PagingData<PhotoInfo>> = Pager(
        config = PagingConfig(pageSize = 10),
        initialKey = null,
        pagingSourceFactory = {NasaApiPagingSource(sole)}
    ).flow.cachedIn(viewModelScope)

    fun checkUserSol(text:CharSequence?):Boolean{
        return try {
            val textAsInt = text.toString().toInt()
            if (textAsInt > 1000 || textAsInt < 0) return false
            sole = textAsInt
            true
        } catch (t:Throwable){
            false
        }
    }


}