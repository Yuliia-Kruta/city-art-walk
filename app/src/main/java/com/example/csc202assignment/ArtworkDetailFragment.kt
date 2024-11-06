package com.example.csc202assignment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.csc202assignment.ArtworkDetailFragmentDirections
import com.example.csc202assignment.databinding.FragmentArtworkDetailBinding
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date

private const val DATE_FORMAT = "EEE, MMM, dd"

class ArtworkDetailFragment: Fragment() {

    private var _binding: FragmentArtworkDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: ArtworkDetailFragmentArgs by navArgs()

    private val artworkDetailViewModel: ArtworkDetailViewModel by viewModels {
        ArtworkDetailViewModelFactory(args.artworkId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentArtworkDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            artworkTitle.doOnTextChanged{
                text, _, _, _ ->
                artworkDetailViewModel.updateArtwork {
                    oldArtwork -> oldArtwork.copy(title = text.toString())
                }
            }

            artworkAddress.doOnTextChanged{
                    text, _, _, _ ->
                artworkDetailViewModel.updateArtwork {
                        oldArtwork -> oldArtwork.copy(address = text.toString())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                artworkDetailViewModel.artwork.collect { artwork ->
                    artwork?.let { updateUi(it) }
                }
            }
        }

        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { _, bundle ->
            val newDate =
                bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            artworkDetailViewModel.updateArtwork { it.copy(date = newDate) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(artwork: Artwork) {
        binding.apply {
            if (artworkTitle.text.toString() != artwork.title) {
                artworkTitle.setText(artwork.title)
            }
            if (artworkAddress.text.toString() != artwork.address) {
                artworkAddress.setText(artwork.address)
            }
            //artworkDate.text = artwork.date.toString()
            artworkDate.text = Utils.formatArtworkDate(artwork.date)
            artworkDate.setOnClickListener {
                findNavController().navigate(
                    ArtworkDetailFragmentDirections.selectDate(artwork.date)
                )
            }
        }
    }
}