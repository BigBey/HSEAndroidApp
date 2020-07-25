package ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.bey_sviatoslav.android.dogbreedsapplication.repo.DogRepository
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsViewState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.SubbreedsViewState

class BreedsViewModel : ViewModel() {
    private val dogRepository = DogRepository.getInstance()

    private val _viewStateData = MediatorLiveData<BreedsViewState>()
    val viewStateData: LiveData<BreedsViewState>
        get() = _viewStateData

    init {
        _viewStateData.value = BreedsViewState.init()

        _viewStateData.addSource(dogRepository.liveData){
            _viewStateData.value = it.reduce(_viewStateData.value!!)
        }

        dogRepository.getAllBreeds()
    }

    fun onRetry() {
        dogRepository.getAllBreeds()
    }

    fun onRefresh() {
        dogRepository.getAllBreeds(isRefresher = true)
    }
}