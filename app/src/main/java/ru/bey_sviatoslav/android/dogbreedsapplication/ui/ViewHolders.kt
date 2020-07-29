package ru.bey_sviatoslav.android.dogbreedsapplication.ui

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage
import ru.bey_sviatoslav.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImagesDatabase
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.getFavoriteDogImageFromLink
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.getSubbreedsSuffix
import ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel.LikeViewModel


class BreedViewHolder(view: View, itemListener: (Pair<String, List<String>>) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.breed_name)
    private val countOfSubbreeds = view.findViewById<TextView>(R.id.count_of_subbreeds)

    private lateinit var breed: Pair<String, List<String>>

    init {
        view.setOnClickListener { itemListener(breed) }
    }

    fun bind(breed: Pair<String, List<String>>) {
        this.breed = breed
        name.text = breed.first[0].toUpperCase() + breed.first.substring(1)
        countOfSubbreeds.text =
            if (breed.second.size == 0) "" else "(${breed.second.size} ${getSubbreedsSuffix(breed.second.size)})"
    }
}

class SubbreedViewHolder(view: View, itemListener: (String) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.subbreed_name)

    private lateinit var subbreed: String

    init {
        view.setOnClickListener { itemListener(subbreed) }
    }

    fun bind(subbreed: String) {
        this.subbreed = subbreed
        name.text = subbreed[0].toUpperCase() + subbreed.substring(1)
    }
}

class BreedImageViewHolder(view: View, itemListener: (String) -> Unit, val fragment: Fragment) :
    RecyclerView.ViewHolder(view) {
    private val breedImage = view.findViewById<ImageView>(R.id.breed_image_holder)
    private val likeBtn = view.findViewById<Button>(R.id.favBtn)
    private var likeState: Boolean = false
    private lateinit var breedImageLink: String

    init {
        likeBtn.setOnClickListener {
            onLikeClicked()
        }
    }

    fun bind(breedImageLink: String) {
        this.breedImageLink = breedImageLink
        Glide.with(itemView).load(breedImageLink).placeholder(R.drawable.ic_dog).into(breedImage)
        checkIfImageInFavorites(breedImageLink)
        setLikeState()
    }


    private fun checkIfImageInFavorites(breedImageLink: String) {
        var dogImage: FavoriteDogImage? = null
        runBlocking {
            launch {
                dogImage = FavoriteDogImagesDatabase.getDatabaseIstance(fragment.context!!)
                    .favoriteDogImageDao().findDogImageByLinkAsync(breedImageLink)
                likeState = dogImage != null
            }
        }
    }

    private fun setLikeState() {
        if (likeState) {
            likeBtn.setBackgroundResource(R.drawable.ic_favourite_red)
        } else {
            likeBtn.setBackgroundResource(R.drawable.ic_favourite_shadow)
        }
    }

    private fun onLikeClicked() {
        if (likeState) {
            runBlocking { launch {
                FavoriteDogImagesDatabase.getDatabaseIstance(fragment.context!!)
                    .favoriteDogImageDao().deleteAsync(getFavoriteDogImageFromLink(breedImageLink))
                checkIfImageInFavorites(breedImageLink)
                setLikeState()
            } }
        }else{
            runBlocking { launch {
                FavoriteDogImagesDatabase.getDatabaseIstance(fragment.context!!)
                    .favoriteDogImageDao().insertAsync(getFavoriteDogImageFromLink(breedImageLink))
                checkIfImageInFavorites(breedImageLink)
                setLikeState()
            } }
        }
    }
}

class MessageViewHolder(
    view: View,
    buttonId: Int,
    listener: () -> Unit
) : RecyclerView.ViewHolder(view) {
    init {
        view.findViewById<MaterialButton>(buttonId).setOnClickListener { listener() }
    }
}

class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)