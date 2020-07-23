package ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds

import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.model.Breed
import java.lang.Exception

sealed class BreedsModelState {
    abstract fun reduce(oldState: BreedsViewState): BreedsViewState

    object Init : BreedsModelState() {
        override fun reduce(oldState: BreedsViewState): BreedsViewState = BreedsViewState.init()
    }

    object BreedsLoading : BreedsModelState() {
        override fun reduce(oldState: BreedsViewState): BreedsViewState =
            BreedsViewState.breedsLoading()

    }

    class BreedsLoaded(private val breeds: List<Breed>) : BreedsModelState() {
        override fun reduce(oldState: BreedsViewState): BreedsViewState =
            BreedsViewState.breedsLoaded(breeds = breeds)

    }

    class BreedsLoadingFailed(private val error: Exception) : BreedsModelState() {
        override fun reduce(oldState: BreedsViewState): BreedsViewState =
            BreedsViewState.breedsLoadingFailed(errorLoadingBreeds = error)

    }

    object BreedsRefresherLoading : BreedsModelState() {
        override fun reduce(oldState: BreedsViewState): BreedsViewState =
            BreedsViewState.breedsRefreshingLoading(oldState.breeds)

    }

    class BreedsRefresherLoadingFailed(private val error: Exception) : BreedsModelState() {
        override fun reduce(oldState: BreedsViewState): BreedsViewState =
            BreedsViewState.breedsRefreshingLoadingFailed(
                breeds = oldState.breeds,
                errorRefreshLoading = error
            )

    }

}