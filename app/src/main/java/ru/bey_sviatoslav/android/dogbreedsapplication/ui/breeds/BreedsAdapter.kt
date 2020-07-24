package ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.model.Breed
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.RecyclerState
import java.util.*

class BreedsAdapter(
    private val itemListener: (MutableMap.MutableEntry<String, List<String>>) -> Unit,
    private val reloadListener: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableMapOf<String, List<String>>()
    private lateinit var itemsIterator: Iterator<Map.Entry<String, List<String>>>
    private var state: RecyclerState = RecyclerState.LOADING

    private val layoutId: Int
        get() =
            when (state) {
                RecyclerState.LOADING -> R.layout.item_loading
                RecyclerState.BREEDS -> R.layout.item_breed
                RecyclerState.EMPTY -> R.layout.item_empty
                else -> R.layout.item_error
            }
    private val buttonId: Int
        get() = R.id.retry_button

    fun setItems(items: Map<String, List<String>>, state: RecyclerState) {
        if (state == RecyclerState.BREEDS) {
            if (this.state == RecyclerState.BREEDS) {
                this.state = state
            } else {
                this.state = state
                notifyItemRemoved(0)
            }
            this.items.clear()
            this.items.putAll(items)
            itemsIterator = items.iterator()
        } else {
            if (this.state == RecyclerState.BREEDS) {
                notifyItemRangeRemoved(0, itemCount)
                this.items.clear()
                this.state = state
                notifyItemInserted(0)
            } else {
                this.state = state
                notifyItemChanged(0)
            }
        }
    }

    override fun getItemCount(): Int = if (items.isNotEmpty()) items.size else 1

    override fun getItemViewType(position: Int) =
        if (position == 0) state.viewType else position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (state) {
            RecyclerState.BREEDS -> return BreedViewHolder(
                view = LayoutInflater.from(parent.context).inflate(
                    layoutId,
                    parent,
                    false
                ),
                itemListener = itemListener
            )
            RecyclerState.LOADING, RecyclerState.EMPTY -> return LoadingViewHolder(
                view = LayoutInflater.from(parent.context).inflate(
                    layoutId,
                    parent,
                    false
                )
            )
            else -> return MessageViewHolder(
                view = LayoutInflater.from(parent.context).inflate(
                    layoutId,
                    parent,
                    false
                ),
                buttonId = buttonId,
                listener = reloadListener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BreedViewHolder && itemsIterator.hasNext())
            holder.bind(itemsIterator.next() as MutableMap.MutableEntry<String, List<String>>)
    }
}