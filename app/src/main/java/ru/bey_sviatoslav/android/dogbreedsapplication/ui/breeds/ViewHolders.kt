package ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.item_breed.view.*
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.model.Breed

class BreedViewHolder(view: View, itemListener: (MutableMap.MutableEntry<String, List<String>>) -> Unit) : RecyclerView.ViewHolder(view){
    private val name = view.findViewById<TextView>(R.id.breed_name)

    private lateinit var breed: MutableMap.MutableEntry<String, List<String>>

    init {
        view.setOnClickListener {itemListener(breed)}
    }

    fun bind(breed: MutableMap.MutableEntry<String, List<String>>){
        name.text = breed.key
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