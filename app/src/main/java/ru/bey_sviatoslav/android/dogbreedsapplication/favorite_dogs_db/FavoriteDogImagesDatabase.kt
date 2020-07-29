package ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoriteDogImage::class), version = DB_VERSION)
abstract class FavoriteDogImagesDatabase : RoomDatabase(){
    abstract fun favoriteDogImageDao(): FavoriteDogImageDao

    companion object{
        @Volatile
        private var databaseInstance: FavoriteDogImagesDatabase? = null

        fun getDatabaseIstance(mContext: Context): FavoriteDogImagesDatabase =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDatabaseInstance(mContext).also {
                    databaseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, FavoriteDogImagesDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}

const val DB_VERSION = 1
const val DB_NAME = "FavoriteDogImages.db"