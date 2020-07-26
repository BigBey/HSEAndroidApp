package ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds

import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsViewState
import java.lang.Exception

sealed class SubbreedsModelState {
    abstract fun reduce(oldState: SubbreedsViewState): SubbreedsViewState

    object Init : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState = SubbreedsViewState.init()
    }

    object SubbreedsLoading : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.breedsLoading()

    }

    class SubbreedsLoaded(private val breeds: List<String>) : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.breedsLoaded(breeds = breeds)

    }

    class SubbreedsLoadingFailed(private val error: Exception) : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.breedsLoadingFailed(errorLoadingBreeds = error)

    }

    object SubbreedsRefresherLoading : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.breedsRefreshingLoading(oldState.breeds)

    }

    class SubbreedsRefresherLoadingFailed(private val error: Exception) : SubbreedsModelState() {
        override fun reduce(oldState: SubbreedsViewState): SubbreedsViewState =
            SubbreedsViewState.breedsRefreshingLoadingFailed(
                breeds = oldState.breeds,
                errorRefreshLoading = error
            )

    }

}