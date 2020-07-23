package ru.bey_sviatoslav.android.dogbreedsapplication.repo

import io.reactivex.Observable
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.Breeds

class DogRepository(private val dogApiService: DogApiService) {
    fun getAllBreeds(): Observable<Breeds>{
        return dogApiService.getAllBreeds()
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