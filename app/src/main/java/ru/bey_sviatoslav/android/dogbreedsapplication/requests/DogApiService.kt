package ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.ResultBreeds
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.ResultSubbreeds

interface DogApiService {

    @GET("breeds/list/all")
    fun getAllBreeds(): Call<ResultBreeds>

    @GET("breed/{breed_name}/list")
    fun getSubbreeds(@Path("breed_name") breedName: String): Call<ResultSubbreeds>

    /**
     * Companion object to create the DogApiService
     */
    companion object Factory{
        fun create(): DogApiService{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(DogApiService::class.java)
        }
    }
}