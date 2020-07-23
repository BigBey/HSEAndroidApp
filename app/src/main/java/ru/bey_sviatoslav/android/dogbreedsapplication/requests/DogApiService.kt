package ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.Breeds

interface DogApiService {

    @GET("breeds/list/all")
    fun getAllBreeds(): Observable<Breeds>

    /**
     * Companion object to create the DogApiService
     */
    companion object Factory{
        fun create(): DogApiService{
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://dog.ceo/api/")
                .build()
            return retrofit.create(DogApiService::class.java)
        }
    }
}