package ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds

import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.model.Breed
import java.lang.Exception

class BreedsViewState(
    val breeds: List<Breed>,
    val isBreedsLoading: Boolean,
    val errorLoadingBreeds: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = BreedsViewState(
            breeds = emptyList(),
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsLoading() = BreedsViewState(
            breeds = emptyList(),
            isBreedsLoading = true,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsLoaded(breeds: List<Breed>) = BreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsLoadingFailed(errorLoadingBreeds: Exception?) = BreedsViewState(
            breeds = emptyList(),
            isBreedsLoading = false,
            errorLoadingBreeds = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun breedsRefreshingLoading(breeds: List<Breed>) = BreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun breedsRefreshingLoadingFailed(breeds: List<Breed>, errorRefreshLoading: Exception?) = BreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}