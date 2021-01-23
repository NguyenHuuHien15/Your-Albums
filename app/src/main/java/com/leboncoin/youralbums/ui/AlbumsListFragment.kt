package com.leboncoin.youralbums.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leboncoin.youralbums.R
import com.leboncoin.youralbums.databinding.AlbumItemBinding
import com.leboncoin.youralbums.databinding.AlbumsListFragmentBinding
import com.leboncoin.youralbums.domain.Album
import com.leboncoin.youralbums.viewmodels.AlbumViewModel

/**
 * Show a list of Albums on screen.
 */
class AlbumsListFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: AlbumViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, AlbumViewModel.Factory(activity.application)).get(AlbumViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of Album to cards.
     */
    private var viewModelAdapter: AlbumsRecyAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.albums.observe(viewLifecycleOwner, { albums ->
            albums?.apply {
                viewModelAdapter?.albums = albums
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: AlbumsListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.albums_list_fragment, container, false)

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModelAdapter = AlbumsRecyAdapter()

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = viewModelAdapter
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
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
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

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
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

/**
 * ViewHolder for Albums items. All work is done by data binding.
 */
class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.album_item
    }
}