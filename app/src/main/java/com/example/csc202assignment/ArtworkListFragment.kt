package com.example.csc202assignment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csc202assignment.ArtworkListFragmentDirections
import com.example.csc202assignment.databinding.FragmentArtworkListBinding
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class ArtworkListFragment: Fragment() {

    private var _binding: FragmentArtworkListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val artworkListViewModel: ArtworkListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtworkListBinding.inflate(inflater, container, false)

        binding.artworkRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                artworkListViewModel.artworks.collect { artworks ->
                    binding.artworkRecyclerView.adapter =
                        ArtworkListAdapter(artworks) { artworkId ->
                            findNavController().navigate(
                                ArtworkListFragmentDirections.showArtworkDetail(artworkId, false)
                            )
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_artwork_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_artwork -> {
                showNewArtwork()
                true
            }
            R.id.show_help -> {
                val intent = Intent(requireContext(), HelpActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNewArtwork() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newArtwork = Artwork(
                id = UUID.randomUUID(),
                title = "",
                date = Date(),
                address = ""
            )
            artworkListViewModel.addArtwork(newArtwork)
            findNavController().navigate(
                ArtworkListFragmentDirections.showArtworkDetail(newArtwork.id, true)
            )
        }
    }
}