package ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds

import java.lang.Exception

class SubbreedsViewState(
    val breeds: Map<String, List<String>>,
    val isBreedsLoading: Boolean,
    val errorLoadingBreeds: Exception?,
    val isRefreshLoading: Boolean,
    val errorRefreshLoading: Exception?
) {
    companion object{
        fun init() = SubbreedsViewState(
            breeds = emptyMap(),
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedsLoading() = SubbreedsViewState(
            breeds = emptyMap(),
            isBreedsLoading = true,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedsLoaded(breeds: Map<String, List<String>>) = SubbreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedsLoadingFailed(errorLoadingBreeds: Exception?) = SubbreedsViewState(
            breeds = emptyMap(),
            isBreedsLoading = false,
            errorLoadingBreeds = errorLoadingBreeds,
            isRefreshLoading = false,
            errorRefreshLoading = null
        )

        fun subbreedsRefreshingLoading(breeds: Map<String, List<String>>) = SubbreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = true,
            errorRefreshLoading = null
        )

        fun subbreedsRefreshingLoadingFailed(breeds: Map<String, List<String>>, errorRefreshLoading: Exception?) = SubbreedsViewState(
            breeds = breeds,
            isBreedsLoading = false,
            errorLoadingBreeds = null,
            isRefreshLoading = false,
            errorRefreshLoading = errorRefreshLoading
        )
    }
}