package ru.bey_sviatoslav.android.dogbreedsapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.BreedImagesModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.BreedImagesResult
import java.lang.Exception

class SubbreedImagesRepository(private val dogApiService: DogApiService) {
    private val _liveData = MutableLiveData<BreedImagesModelState>()
    val liveData: LiveData<BreedImagesModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getSubbreedImages(breedname : String, subbreedname: String, isRefresher: Boolean = false){
        _liveData.value =
            if (isRefresher) BreedImagesModelState.BreedImagesRefresherLoading
            else BreedImagesModelState.BreedImagesLoading

        val call = dogApiService.getSubbreedImages(breedname, subbreedname)

        call.enqueue(object : Callback<BreedImagesResult?> {

            override fun onResponse(call: Call<BreedImagesResult?>, response: Response<BreedImagesResult?>) {
                val breedImages =
                    response.body()
                _liveData.postValue(BreedImagesModelState.BreedImagesLoaded(breedImages = breedImages!!.message))
            }

            override fun onFailure(call: Call<BreedImagesResult?>, t: Throwable) {
                _liveData.postValue(
                    if (isRefresher)
                        BreedImagesModelState.BreedImagesRefresherLoadingFailed(t as Exception)
                    else
                        BreedImagesModelState.BreedImagesLoadingFailed(t as Exception)
                )
            }

        })
    }


    fun clear() {
        _liveData.value = BreedImagesModelState.Init
    }

    companion object {
        private lateinit var _instance: SubbreedImagesRepository
        fun getInstance(): SubbreedImagesRepository {
            if (!::_instance.isInitialized)
                _instance = SubbreedImagesRepository(DogApiService.create())
            return _instance
        }
    }
}