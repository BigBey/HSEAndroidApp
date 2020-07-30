package ru.bey_sviatoslav.android.dogbreedsapplication.ui.favourite_breeds

import java.lang.Exception

class FavoriteBreedsViewState(
    val favoriteBreeds: List<String>,
    val isBreedsLoading: Boolean,
    val errorLoadingBreeds: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = FavoriteBreedsViewState(
            favoriteBreeds = emptyList(),
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedsLoading() = FavoriteBreedsViewState(
            favoriteBreeds = emptyList(),
            isBreedsLoading = true,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedsLoaded(favoriteBreeds: List<String>) = FavoriteBreedsViewState(
            favoriteBreeds = favoriteBreeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedsLoadingFailed(errorLoadingBreeds: Exception?) = FavoriteBreedsViewState(
            favoriteBreeds = emptyList(),
            isBreedsLoading = false,
            errorLoadingBreeds = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun favoriteBreedsRefreshingLoading(favoriteBreeds: List<String>) = FavoriteBreedsViewState(
            favoriteBreeds = favoriteBreeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun favoriteBreedsRefreshingLoadingFailed(favoriteBreeds: List<String>, errorRefreshLoading: Exception?) = FavoriteBreedsViewState(
            favoriteBreeds = favoriteBreeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}