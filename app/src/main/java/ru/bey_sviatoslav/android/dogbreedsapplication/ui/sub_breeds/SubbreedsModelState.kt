package ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds

import java.lang.Exception

sealed class SubbreedsModelState {
    abstract fun reduce(oldState: SubbreedsViewState): SubbreedsViewState

    object Init : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState = SubbreedsViewState.init()
    }

    object SubbreedsLoading : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsLoading()

    }

    class SubbreedsLoaded(private val breeds: Map<String, List<String>>) : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsLoaded(breeds = breeds)

    }

    class SubbreedsLoadingFailed(private val error: Exception) : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsLoadingFailed(errorLoadingBreeds = error)

    }

    object SubbreedsRefresherLoading : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsRefreshingLoading(oldState.breeds)

    }

    class SubbreedsRefresherLoadingFailed(private val error: Exception) : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.subbreedsRefreshingLoadingFailed(
                breeds = oldState.breeds,
                errorRefreshLoading = error
            )

    }

}