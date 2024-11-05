package com.example.csc202assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csc202assignment.databinding.ListItemArtworkBinding
import java.util.UUID

class ArtworkHolder(
    private val binding: ListItemArtworkBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(artwork: Artwork, onArtworkClicked: (artworkId: UUID) -> Unit) {
        binding.artworkTitle.text = artwork.title
        binding.artworkDate.text = artwork.date.toString()
        binding.artworkAddress.text = artwork.address

        binding.root.setOnClickListener {
            onArtworkClicked(artwork.id)
        }

    }
}

class ArtworkListAdapter(
    private val artworks: List<Artwork>,
    private val onArtworkClicked: (artworkId: UUID) -> Unit
) : RecyclerView.Adapter<ArtworkHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtworkHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemArtworkBinding.inflate(inflater, parent, false)
        return ArtworkHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtworkHolder, position: Int) {
        val artwork = artworks[position]
        holder.bind(artwork, onArtworkClicked)
    }

    override fun getItemCount() = artworks.size
}