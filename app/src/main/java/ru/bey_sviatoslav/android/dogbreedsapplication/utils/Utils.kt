package ru.bey_sviatoslav.android.dogbreedsapplication.utils

fun getSubbreedsSuffix(count: Int): String{
    if(count == 1){
        return "subbreed"
    }else{
        return "subbreeds"
    }
}