package ru.bey_sviatoslav.android.dogbreedsapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds.SubbreedsModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.BreedsResult
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.SubbreedsResult
import java.lang.Exception

class SubbreedsRepository(private val dogApiService: DogApiService) {
    private val _liveData = MutableLiveData<SubbreedsModelState>()
    val liveData: LiveData<SubbreedsModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getAllBreeds(breedname : String,isRefresher: Boolean = false){
        _liveData.value =
            if (isRefresher) SubbreedsModelState.SubbreedsRefresherLoading
            else SubbreedsModelState.SubbreedsLoading

        val call = dogApiService.getSubbreeds(breedname)

        call.enqueue(object : Callback<SubbreedsResult?> {

            override fun onResponse(call: Call<SubbreedsResult?>, response: Response<SubbreedsResult?>) {
                val breeds =
                    response.body()
                _liveData.postValue(SubbreedsModelState.SubbreedsLoaded(breeds = breeds!!.message))
            }

            override fun onFailure(call: Call<SubbreedsResult?>, t: Throwable) {
                _liveData.postValue(
                    if (isRefresher)
                        SubbreedsModelState.SubbreedsRefresherLoadingFailed(t as Exception)
                    else
                        SubbreedsModelState.SubbreedsLoadingFailed(t as Exception)
                )
            }

        })
    }


    fun clear() {
        _liveData.value = SubbreedsModelState.Init
    }

    companion object {
        private lateinit var _instance: SubbreedsRepository
        fun getInstance(): SubbreedsRepository {
            if (!::_instance.isInitialized)
                _instance = SubbreedsRepository(DogApiService.create())
            return _instance
        }
    }
}