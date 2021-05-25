package ru.bey_sviatoslav.android.dogbreedsapplication.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.BreedImagesModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.BreedImagesResult
import java.lang.Exception

const val TAGBreedImages = "BreedImages"

class BreedImagesRepository(private val dogApiService: DogApiService) {
    private val _liveData = MutableLiveData<BreedImagesModelState>()
    val liveData: LiveData<BreedImagesModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getBreedImages(breedname : String,isRefresher: Boolean = false){
        _liveData.value =
            if (isRefresher) {
                Log.d(TAGBreedImages,"Включен refresher")
                BreedImagesModelState.BreedImagesRefresherLoading
            }
            else{
                Log.d(TAGBreedImages,"Выключен refresher")
                BreedImagesModelState.BreedImagesLoading
            }

        val call = dogApiService.getBreedImages(breedname)

        call.enqueue(object : Callback<BreedImagesResult?> {

            override fun onResponse(call: Call<BreedImagesResult?>, response: Response<BreedImagesResult?>) {
                val breedImages =
                    response.body()
                _liveData.postValue(BreedImagesModelState.BreedImagesLoaded(breedImages = breedImages!!.message))
                Log.d(TAGBreedImages,"Подгуржаем данные...")
            }

            override fun onFailure(call: Call<BreedImagesResult?>, t: Throwable) {
                _liveData.postValue(
                    if (isRefresher) {
                        Log.d (TAGBreedImages, "Включен refresher, но данные пока не получили - возможно ошибка")
                        BreedImagesModelState.BreedImagesRefresherLoadingFailed(t as Exception)
                    }

                    else {
                        Log.d(TAGBreedImages, "Выключен refresher, но данные пока не получили - возможно ошибка")
                        BreedImagesModelState.BreedImagesLoadingFailed(t as Exception)
                    }
                )
            }

        })
    }


    fun clear() {
        Log.d(TAGBreedImages,"Очищаем данные...")
        _liveData.value = BreedImagesModelState.Init
    }

    companion object {
        private lateinit var _instance: BreedImagesRepository
        fun getInstance(): BreedImagesRepository {
            if (!::_instance.isInitialized)
                _instance = BreedImagesRepository(DogApiService.create())
            Log.d(TAGBreedImages,"Получаем Instance")
            return _instance
        }
    }
}