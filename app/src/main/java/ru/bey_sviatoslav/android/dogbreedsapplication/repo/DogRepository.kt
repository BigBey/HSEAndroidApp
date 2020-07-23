package ru.bey_sviatoslav.android.dogbreedsapplication.repo

import android.text.method.TextKeyListener.clear
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.Breeds

class DogRepository(private val dogApiService: DogApiService) {
    private val _liveData = MutableLiveData<BreedsModelState>()
    val liveData: LiveData<BreedsModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getAllBreeds(isRefresher: Boolean = false): Observable<Breeds> {
        _liveData.value =
            if (isRefresher) BreedsModelState.BreedsRefresherLoading
            else BreedsModelState.BreedsLoading

        return dogApiService.getAllBreeds()
    }


    fun clear() {
        _liveData.value = BreedsModelState.Init
    }

    companion object {
        private lateinit var _instance: DogRepository
        fun getInstance(): DogRepository {
            if (!::_instance.isInitialized)
                _instance = DogRepository(DogApiService.create())
            return _instance
        }
    }
}