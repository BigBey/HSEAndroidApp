package ru.bey_sviatoslav.android.dogbreedsapplication.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.getSubbreedsSuffix

class BreedViewHolder(view: View, itemListener: (Pair<String, List<String>>) -> Unit) : RecyclerView.ViewHolder(view){
    private val name = view.findViewById<TextView>(R.id.breed_name)
    private val countOfSubbreeds = view.findViewById<TextView>(R.id.count_of_subbreeds)

    private lateinit var breed: Pair<String, List<String>>

    init {
        view.setOnClickListener {itemListener(breed)}
    }

    fun bind(breed: Pair<String, List<String>>){
        this.breed = breed
        name.text = breed.first[0].toUpperCase() + breed.first.substring(1)
        countOfSubbreeds.text = if (breed.second.size == 0)  "" else  "(${breed.second.size} ${getSubbreedsSuffix(breed.second.size)})"
    }
}

class SubbreedViewHolder(view: View, itemListener: (String) -> Unit) : RecyclerView.ViewHolder(view){
    private val name = view.findViewById<TextView>(R.id.subbreed_name)

    private lateinit var subbreed: String

    init {
        view.setOnClickListener {itemListener(subbreed)}
    }

    fun bind(subbreed: String){
        name.text = subbreed[0].toUpperCase() + subbreed.substring(1)
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