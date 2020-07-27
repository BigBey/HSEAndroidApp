package ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.bey_sviatoslav.android.dogbreedsapplication.repo.SubbreedImagesRepository
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.BreedImagesViewState

class SubbreedImagesViewModel: ViewModel() {
    private val breedImagesRepository = SubbreedImagesRepository.getInstance()

    private val _viewStateData = MediatorLiveData<BreedImagesViewState>()
    val viewStateData: LiveData<BreedImagesViewState>
        get() = _viewStateData

    init {
        _viewStateData.value = BreedImagesViewState.init()

        _viewStateData.addSource(breedImagesRepository.liveData){
            _viewStateData.value = it.reduce(_viewStateData.value!!)
        }
    }

    fun onRetry(breedname: String, subbreedname: String) {
        breedImagesRepository.getSubbreedImages(breedname, subbreedname)
    }

    fun onRefresh(breedname: String, subbreedname: String) {
        breedImagesRepository.getSubbreedImages(breedname, subbreedname, isRefresher = true)
    }
}