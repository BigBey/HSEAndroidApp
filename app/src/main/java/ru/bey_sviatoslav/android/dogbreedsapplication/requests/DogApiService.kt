package ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http

import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.Result

interface DogApiService {

    @GET("breeds/list/all")
    fun getAllBreeds(): Call<JSONObject>

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