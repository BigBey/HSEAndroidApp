package ru.bey_sviatoslav.android.dogbreedsapplication.ui.breedimages

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_breed_images.*
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.ui.RecyclerState
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.Coordinator
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.sharePhoto
import ru.bey_sviatoslav.android.dogbreedsapplication.viewmodel.SubbreedImagesViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class SubbreedImagesFragment : Fragment() {
    private lateinit var viewModel: SubbreedImagesViewModel
    private lateinit var adapter: BreedImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breed_images, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(SubbreedImagesViewModel::class.java)
        viewModel.onRetry(arguments?.getString("breedname") ?: "Breed name", arguments?.getString("subbreedname") ?: "Subbreed name")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.viewStateData.observe(this.viewLifecycleOwner, Observer {
            val state = when{
                it.isSubbreedImagesLoading -> RecyclerState.LOADING
                it.errorLoadingSubbreedImages != null -> RecyclerState.ERROR
                it.subbreedImages.isEmpty() -> RecyclerState.EMPTY
                else -> RecyclerState.ITEMS
            }
            val isRefreshable = !(it.isSubbreedImagesLoading || it.errorLoadingSubbreedImages != null)

            refresher_breed_images.isEnabled = isRefreshable
            refresher_breed_images.isRefreshing = it.isRefreshLoading
            adapter.setItems(it.subbreedImages, state)

            if (it.errorRefreshLoading != null)
                Snackbar.make(
                    fragment_breed_images,
                    it.errorRefreshLoading.localizedMessage ?: "",
                    Snackbar.LENGTH_SHORT
                ).show()
        })
    }

    private fun initViews(){
        imgvw_back_to_breeds_from_images.setOnClickListener {
            Coordinator.onSubbreedsPop(requireActivity().supportFragmentManager)
        }

        imgvw_share.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle(resources.getString(R.string.share_dialog_title))
                .setNegativeButton(resources.getString(R.string.share_dialog_cancel_button)){ dialog, which ->
                    //Cancel
                }
                .setPositiveButton(resources.getString(R.string.share_dialog_confirm_button)){ dialog, which ->
                    sharePhoto(adapter.getCurrentItem(viewpager_breed_images.currentItem), context!!)
                }
                .show()
        }

        refresher_breed_images.setOnRefreshListener {
            viewModel.onRefresh(arguments?.getString("breedname") ?: "Breed name", arguments?.getString("subbreedname") ?: "Subbreed name")
        }
        refresher_breed_images.setColorSchemeResources(R.color.colorAccent)

        adapter = BreedImagesAdapter({viewModel.onRefresh(arguments?.getString("breedname") ?: "Breed name", arguments?.getString("subbreedname") ?: "Subbreed name")}, {})

        viewpager_breed_images.adapter = adapter
        viewpager_breed_images.orientation = ViewPager2.ORIENTATION_VERTICAL

        val subbreedname = arguments?.getString("subbreedname") ?: "Subbreed name"
        toolbar_title_breed_images.text = subbreedname[0].toUpperCase() + subbreedname.substring(1)
    }

    companion object {
        fun newInstance(breedname: String, subbreedname: String): SubbreedImagesFragment {
            val fragment = SubbreedImagesFragment()
            val args = Bundle().apply {
                putString("breedname", breedname)
                putString("subbreedname", subbreedname)
            }
            return fragment.apply { arguments = args }
        }
    }
}
