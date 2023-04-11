package com.example.recyclerviewproject.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewproject.R
import com.example.recyclerviewproject.constants.*
import com.example.recyclerviewproject.databinding.FragmentMainBinding
import com.example.recyclerviewproject.entity.PhotoInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //Инстанс ViewModel
    private val viewModel:MainFragmentViewModel by viewModels()
    //Инстанс Adapter
    private val pagedAdapter = NasaApiPagingDataAdapter { photoInfo -> onItemClick(photoInfo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.RecyclerView.adapter = pagedAdapter.withLoadStateFooter(MyLoadStateAdapter()) //передаем адаптер в RV

        //Заполняем RecyclerView элементами (ПАГИНАЦИЯ)
        viewModel.pagedPhotoFromNasaApi.onEach {
            pagedAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        //Выводим информацию о кол-ве загруженных элементов / страниц
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (true) {
                delay(500)
                binding.textViewImageValue.text = pagedAdapter.itemCount.toString()
                binding.textViewPageDownload.text = PAGE_SIZE.toString()
            }
        }

        //Слущаем поле EditTExt и загружаем данные с нового Sol, если пользователь ввел допустимый сол:
        binding.editTextPutSol.doOnTextChanged { text, _, _, _ ->
            if (TEXT_OLD_VALUE != text.toString()) {
                if (viewModel.checkUserSol(text)) {
                    pagedAdapter.refresh()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        TEXT_OLD_VALUE = binding.editTextPutSol.text.toString()
    }

    private fun onItemClick(item:PhotoInfo){
        val bundle = Bundle().apply {
            putString(ROVER_NAME_KEY,item.roverInfo.name)
            putString(SOL_KEY,item.sol.toString())
            putString(DATA_KEY,item.earthDate)
            putString(IMAGE_URL,item.imgSrc)
        }
        findNavController().navigate(R.id.action_mainFragment_to_itemClickInfoFragment,bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}