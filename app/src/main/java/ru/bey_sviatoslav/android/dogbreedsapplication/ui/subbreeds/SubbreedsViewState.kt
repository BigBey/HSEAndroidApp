package ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds

import java.lang.Exception

class SubbreedsViewState(
    val breeds: List<String>,
    val isBreedsLoading: Boolean,
    val errorLoadingBreeds: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = SubbreedsViewState(
            breeds = emptyList(),
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsLoading() = SubbreedsViewState(
            breeds = emptyList(),
            isBreedsLoading = true,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsLoaded(breeds: List<String>) = SubbreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsLoadingFailed(errorLoadingBreeds: Exception?) = SubbreedsViewState(
            breeds = emptyList(),
            isBreedsLoading = false,
            errorLoadingBreeds = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsRefreshingLoading(breeds: List<String>) = SubbreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun breedsRefreshingLoadingFailed(breeds: List<String>, errorRefreshLoading: Exception?) = SubbreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}