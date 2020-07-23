package ru.bey_sviatoslav.android.dogbreedsapplication.ui

enum class RecyclerState(val viewType: Int) {
    LOADING(4000),
    BREEDS(0),
    EMPTY(5000),
    ERROR(6000)
}