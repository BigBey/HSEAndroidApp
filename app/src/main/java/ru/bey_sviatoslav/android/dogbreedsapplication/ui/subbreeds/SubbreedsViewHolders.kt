package ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.getSubbreedsSuffix

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