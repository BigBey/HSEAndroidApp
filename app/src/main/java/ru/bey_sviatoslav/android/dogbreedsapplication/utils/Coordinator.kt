package ru.bey_sviatoslav.android.dogbreedsapplication.utils

import androidx.fragment.app.FragmentManager
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsFragment
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favourite_breeds.FavouriteBreedsFragment

object Coordinator {

    fun onBreedsScreen(fm: FragmentManager){
        fm.beginTransaction()
            .replace(R.id.for_fragment, BreedsFragment.newInstance())
            .commit()
    }

    fun onFavouriteBreedsScreen(fm: FragmentManager){
        fm.beginTransaction()
            .replace(R.id.for_fragment, FavouriteBreedsFragment.newInstance())
            .commit()
    }
}