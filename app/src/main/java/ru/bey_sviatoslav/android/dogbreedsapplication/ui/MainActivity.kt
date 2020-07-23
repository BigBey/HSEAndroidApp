package ru.bey_sviatoslav.android.dogbreedsapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.Result


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dogRepository = DogApiService.create()
        val call = dogRepository.getAllBreeds()
        call.enqueue(object : Callback<JSONObject?>{

            override fun onResponse(call: Call<JSONObject?>, response: Response<JSONObject?>) {
                val breeds =
                    response.body()
                val a = 1
                //Log.d("DOGS", "Number of breeds" + (breeds?.size ?: 0))
            }

            override fun onFailure(call: Call<JSONObject?>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}
