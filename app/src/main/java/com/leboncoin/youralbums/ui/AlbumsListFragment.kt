package com.leboncoin.youralbums.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leboncoin.MyApplication
import com.leboncoin.youralbums.R
import com.leboncoin.youralbums.databinding.AlbumItemBinding
import com.leboncoin.youralbums.databinding.AlbumsListFragmentBinding
import com.leboncoin.youralbums.domain.Album
import com.leboncoin.youralbums.viewmodels.AlbumViewModel
import com.leboncoin.youralbums.viewmodels.ViewModelFactory

/**
 * Show a list of Albums on screen.
 */
class AlbumsListFragment : Fragment() {

    /**
     * RecyclerView Adapter for list of Album
     */
    private var recyAdapter: AlbumsRecyAdapter? = null

    private val viewModel by viewModels<AlbumViewModel> {
        ViewModelFactory((requireContext().applicationContext as MyApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.albums.observe(viewLifecycleOwner, { albums ->
            albums?.apply {
                recyAdapter?.albums = albums
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: AlbumsListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.albums_list_fragment, container, false)

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        recyAdapter = AlbumsRecyAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = recyAdapter
        }

        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    /**
     * Method for displaying a Toast error message for network errors.
     */
    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class AlbumsRecyAdapter() : RecyclerView.Adapter<AlbumViewHolder>() {

    /**
     * The albums that our Adapter will show
     */
    var albums: List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), AlbumViewHolder.LAYOUT, parent, false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun getItemCount() = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
        }
    }
}

class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.album_item
    }
}