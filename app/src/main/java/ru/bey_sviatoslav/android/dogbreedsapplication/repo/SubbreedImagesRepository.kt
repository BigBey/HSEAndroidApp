package ru.bey_sviatoslav.android.dogbreedsapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.BreedImagesModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.SubbreedImagesModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.BreedImagesResult
import java.lang.Exception

class SubbreedImagesRepository(private val dogApiService: DogApiService) {
    private val _liveData = MutableLiveData<SubbreedImagesModelState>()
    val liveData: LiveData<SubbreedImagesModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getSubbreedImages(breedname : String, subbreedname: String, isRefresher: Boolean = false){
        _liveData.value =
            if (isRefresher) SubbreedImagesModelState.SubbreedImagesRefresherLoading
            else SubbreedImagesModelState.SubbreedImagesLoading

        val call = dogApiService.getSubbreedImages(breedname, subbreedname)

        call.enqueue(object : Callback<BreedImagesResult?> {

            override fun onResponse(call: Call<BreedImagesResult?>, response: Response<BreedImagesResult?>) {
                val subbreedImages =
                    response.body()
                _liveData.postValue(SubbreedImagesModelState.SubbreedImagesLoaded(subbreedImages = subbreedImages!!.message))
            }

            override fun onFailure(call: Call<BreedImagesResult?>, t: Throwable) {
                _liveData.postValue(
                    if (isRefresher)
                        SubbreedImagesModelState.SubbreedImagesRefresherLoadingFailed(t as Exception)
                    else
                        SubbreedImagesModelState.SubbreedImagesLoadingFailed(t as Exception)
                )
            }

        })
    }


    fun clear() {
        _liveData.value = SubbreedImagesModelState.Init
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