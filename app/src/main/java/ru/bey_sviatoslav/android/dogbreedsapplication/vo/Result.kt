package ru.bey_sviatoslav.android.dogbreedsapplication.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class Result (
    val message: List<JSONArray>,
    val status: String
)