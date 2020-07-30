package ru.bey_sviatoslav.android.dogbreedsapplication.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImagesDatabase
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favorite_breed_images.FavoriteBreedImagesModelState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favourite_breeds.FavoriteBreedsModelState
import java.lang.Exception

class FavoriteBreedImagesRepository(private val favoriteDogImagesDb: FavoriteDogImagesDatabase) {
    protected val compositeDisposable = CompositeDisposable()
    private val _liveData = MutableLiveData<FavoriteBreedImagesModelState>()
    val liveData: LiveData<FavoriteBreedImagesModelState>
        get() = _liveData

    init {
        clear()
    }

    fun getBreedImages(breedname: String, isRefresher: Boolean = false) {
        _liveData.value =
            if (isRefresher) FavoriteBreedImagesModelState.FavoriteBreedImagesRefresherLoading
            else FavoriteBreedImagesModelState.FavoriteBreedImagesLoading

        favoriteDogImagesDb.favoriteDogImageDao().findDogImagesByBreed(breedname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                _liveData.postValue(FavoriteBreedImagesModelState.FavoriteBreedImagesLoaded(favoriteBreedImages = it))
            }, {
                _liveData.postValue(
                    if (isRefresher)
                        FavoriteBreedImagesModelState.FavoriteBreedImagesRefresherLoadingFailed(it as Exception)
                    else
                        FavoriteBreedImagesModelState.FavoriteBreedImagesLoadingFailed(it as Exception)
                )
            })?.let { compositeDisposable.add(it) }
    }


    fun clear() {
        _liveData.value = FavoriteBreedImagesModelState.Init
    }

    companion object {
        private lateinit var _instance: FavoriteBreedImagesRepository
        fun getInstance(context: Context): FavoriteBreedImagesRepository {
            if (!::_instance.isInitialized)
                _instance =
                    FavoriteBreedImagesRepository(FavoriteDogImagesDatabase.getDatabaseIstance(context))
            return _instance
        }
    }
}