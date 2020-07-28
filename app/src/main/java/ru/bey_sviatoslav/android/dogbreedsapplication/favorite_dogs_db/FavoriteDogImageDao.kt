package ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface FavoriteDogImageDao {
    @Query("SELECT * FROM favoritedogimage")
    fun getAll(): List<FavoriteDogImage>

    @Query("SELECT * FROM favoritedogimage WHERE dogImageLink = (:dogImageLink)")
    fun findDogImageByLink(dogImageLink: String) : FavoriteDogImage

    @Insert
    fun insertAll(vararg dogImages: FavoriteDogImage)

    @Insert
    fun insert(dogImage: FavoriteDogImage)

    @Delete
    fun delete(dogImage: FavoriteDogImage)
}