package ru.bey_sviatoslav.android.dogbreedsapplication.utils

import androidx.fragment.app.FragmentManager
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages.BreedImagesFragment
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsFragment
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.favourite_breeds.FavouriteBreedsFragment
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds.SubbreedsFragment

object Coordinator {

    fun onBreedsScreen(fm: FragmentManager){
        fm.beginTransaction()
            .replace(R.id.for_fragment, BreedsFragment.newInstance())
            .commit()
    }

    fun onBreedClicked(fm: FragmentManager, breedname: String, countOfSubbreeds: Int){
        if(countOfSubbreeds > 0){
            goToSubbreedsScreen(fm, breedname)
        }else{
            goToBreedImagesScreen(fm, breedname)
        }
    }

    fun goToSubbreedsScreen(fm: FragmentManager, breedname: String){
        fm.beginTransaction()
            .replace(R.id.for_fragment, SubbreedsFragment.newInstance(breedname))
            .addToBackStack("BREEDS")
            .commit()
    }

    fun goToBreedImagesScreen(fm: FragmentManager, breedname: String){
        fm.beginTransaction()
            .replace(R.id.for_fragment, BreedImagesFragment.newInstance(breedname))
            .addToBackStack("BREEDS")
            .commit()
    }

    fun onFavouriteBreedsScreen(fm: FragmentManager){
        fm.beginTransaction()
            .replace(R.id.for_fragment, FavouriteBreedsFragment.newInstance())
            .commit()
    }

    fun onSubbreedsPop(fm: FragmentManager){
        fm.popBackStack()
    }
}