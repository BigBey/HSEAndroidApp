package ru.bey_sviatoslav.android.dogbreedsapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.SubbreedsModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.ResultBreeds
import java.lang.Exception

class DogRepository(private val dogApiService: DogApiService) {
    private val _liveData = MutableLiveData<BreedsModelState>()
    val liveData: LiveData<BreedsModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getAllBreeds(isRefresher: Boolean = false){
        _liveData.value =
            if (isRefresher) BreedsModelState.BreedsRefresherLoading
            else BreedsModelState.BreedsLoading

        val call = dogApiService.getAllBreeds()

        call.enqueue(object : Callback<ResultBreeds?> {

            override fun onResponse(call: Call<ResultBreeds?>, response: Response<ResultBreeds?>) {
                val breeds =
                    response.body()
                _liveData.postValue(BreedsModelState.BreedsLoaded(breeds = breeds!!.message))
            }

            override fun onFailure(call: Call<ResultBreeds?>, t: Throwable) {
                _liveData.postValue(
                    if (isRefresher)
                        BreedsModelState.BreedsRefresherLoadingFailed(t as Exception)
                    else
                        BreedsModelState.BreedsLoadingFailed(t as Exception)
                )
            }

        })
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