package ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.item_breed.view.*
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.model.Breed

class BreedViewHolder(view: View, itemListener: (Pair<String, List<String>>) -> Unit) : RecyclerView.ViewHolder(view){
    private val name = view.findViewById<TextView>(R.id.breed_name)
    private val countOfSubbreeds = view.findViewById<TextView>(R.id.count_of_subbreeds)

    private lateinit var breed: Pair<String, List<String>>

    init {
        view.setOnClickListener {itemListener(breed)}
    }

    fun bind(breed: Pair<String, List<String>>){
        name.text = breed.first[0].toUpperCase() + breed.first.substring(1)
        countOfSubbreeds.text = if (breed.second.size == 0)  "" else  "(${breed.second.size} subbreeds)"
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