package ru.bey_sviatoslav.android.dogbreedsapplication.ui.favourite_breeds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favourite_breeds.*
import kotlinx.android.synthetic.main.fragment_subbreeds.*

import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.RecyclerState
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.breeds.BreedsFragment
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds.SubbreedsAdapter
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.subbreeds.SubbreedsFragment
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.Coordinator
import ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel.FavoriteBreedsViewModel
import ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel.SubbreedsViewModel

class FavouriteBreedsFragment : Fragment() {
    private lateinit var viewModel: FavoriteBreedsViewModel
    private lateinit var adapter: FavoriteBreedsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_breeds, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(FavoriteBreedsViewModel::class.java)
        viewModel.setFavoriteBreedsRepository(context!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.viewStateData.observe(this.viewLifecycleOwner, Observer {
            val state = when {
                it.isBreedsLoading -> RecyclerState.LOADING
                it.errorLoadingBreeds != null -> RecyclerState.ERROR
                it.favoriteBreeds.isEmpty() -> RecyclerState.EMPTY
                else -> RecyclerState.ITEMS
            }
            val isRefreshable = !(it.isBreedsLoading || it.errorLoadingBreeds != null)

            refresher_favorite_breeds.isEnabled = isRefreshable
            refresher_favorite_breeds.isRefreshing = it.isRefreshLoading
            adapter.setItems(it.favoriteBreeds, state)

            if (it.errorRefreshLoading != null)
                Snackbar.make(
                    fragment_favorite_breeds,
                    it.errorRefreshLoading.localizedMessage ?: "",
                    Snackbar.LENGTH_SHORT
                ).show()
        })
    }

    private fun initViews() {

        refresher_favorite_breeds.setOnRefreshListener {
            viewModel.onRefresh()
        }
        refresher_favorite_breeds.setColorSchemeResources(R.color.colorAccent)

        adapter = FavoriteBreedsAdapter({
            //
        }, { viewModel.onRefresh()}, context!!)

        recycler_favorite_breeds.adapter = adapter
        recycler_favorite_breeds.layoutManager = LinearLayoutManager(this.activity)
        recycler_favorite_breeds.setHasFixedSize(true)
    }

    companion object {
        fun newInstance() = FavouriteBreedsFragment()
    }
}
