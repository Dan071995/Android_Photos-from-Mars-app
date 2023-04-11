package com.example.recyclerviewproject.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.recyclerviewproject.databinding.RecyclerViewElementBinding
import com.example.recyclerviewproject.entity.PhotoInfo


class NasaApiPagingDataAdapter(
    private val onClick: (PhotoInfo)->Unit
): PagingDataAdapter<PhotoInfo, ViewHolderForDataAdapter>(DiffUtilCallBack()) {

    //В данной функции необходимо просто создать разметку для элемента хранителя xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderForDataAdapter {
        val binding = RecyclerViewElementBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderForDataAdapter(binding)
    }

    //В этой функции устанавливаем данные в элемент
    override fun onBindViewHolder(holder: ViewHolderForDataAdapter, position: Int) {
        val item = getItem(position)//Достаем конкретное фото из массива

        holder.binding.apply {
            if (item != null) {
                //Ссылка с сервера имеет вид "http:...
                //Но протакол шифравания изменился на защищенный "https:..., но
                //ссылка приходит старая. Если попробовать загрузить старую ссылку, то ничего не выйдет
                //Создам фильтр, который меняет "http: на "https:
                val imageUrl = item.imgSrc.replace("http","https")
                imageViewMarsPicture.load(imageUrl)

                textViewRoverName.text = item.roverInfo.name
                textViewSol.text = item.sol.toString()
                textViewDate.text = item.earthDate
                textViewCameraName.text = item.cameraInfo.name
            }
        }

        holder.binding.root.setOnClickListener {
            item?.let { onClick(it) }
        }

    }

}

//Создаем требуемый для DataAdapter Callback. В нем мы описываем логику по которой будут сравниваться элементы между собой
class DiffUtilCallBack: DiffUtil.ItemCallback<PhotoInfo>(){
    //Вызывается чтобы проверить, что 2 разных объекта описывают один и тот же элемент из набора данных
    override fun areItemsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo): Boolean =
        oldItem.id == newItem.id
    //Вызывается чтобы проверить, что у 2ух разных объектов одни и те-же данные
    override fun areContentsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo): Boolean = oldItem == newItem
}

class ViewHolderForDataAdapter(val binding: RecyclerViewElementBinding): RecyclerView.ViewHolder(binding.root)