package ru.bey_sviatoslav.android.dogbreedsapplication.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Breeds (
    val message: List<String>,
    val status: String
)