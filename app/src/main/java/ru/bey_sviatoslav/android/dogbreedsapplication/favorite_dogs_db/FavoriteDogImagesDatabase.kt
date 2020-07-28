package ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoriteDogImage::class), version = 1)
abstract class FavoriteDogImagesDatabase : RoomDatabase(){
    abstract fun favoriteDogImageDao(): FavoriteDogImageDao
}