package ru.bey_sviatoslav.android.dogbreedsapplication.utils

import androidx.fragment.app.FragmentManager
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsFragment

object Coordinator {

    fun onBreedsScreen(fm: FragmentManager){
        fm.beginTransaction()
            .add(R.id.main_activity, BreedsFragment.newInstance())
            .commitAllowingStateLoss()
    }
}