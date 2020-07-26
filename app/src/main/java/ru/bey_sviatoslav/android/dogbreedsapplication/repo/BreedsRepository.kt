package ru.bey_sviatoslav.android.dogbreedsapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.BreedsResult
import java.lang.Exception

class BreedsRepository(private val dogApiService: DogApiService) {
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

        call.enqueue(object : Callback<BreedsResult?> {

            override fun onResponse(call: Call<BreedsResult?>, response: Response<BreedsResult?>) {
                val breeds =
                    response.body()
                _liveData.postValue(BreedsModelState.BreedsLoaded(breeds = breeds!!.message))
            }

            override fun onFailure(call: Call<BreedsResult?>, t: Throwable) {
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
        private lateinit var _instance: BreedsRepository
        fun getInstance(): BreedsRepository {
            if (!::_instance.isInitialized)
                _instance = BreedsRepository(DogApiService.create())
            return _instance
        }
    }
}