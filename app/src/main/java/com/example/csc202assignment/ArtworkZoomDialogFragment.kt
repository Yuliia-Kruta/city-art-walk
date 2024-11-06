package com.example.csc202assignment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.csc202assignment.databinding.FragmentArtworkZoomBinding
import java.io.File

class ArtworkZoomDialogFragment: DialogFragment() {

    private var _binding: FragmentArtworkZoomBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentArtworkZoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoFileName = arguments?.getSerializable("PHOTO_URI") as? String
        if (photoFileName != null) {
            val photoFile = File(requireContext().filesDir, photoFileName)
            if (photoFile.exists()) {
                val bitmap = BitmapFactory.decodeFile(photoFile.path)
                binding.zoomedArtworkPhoto.setImageBitmap(bitmap)
            } else {
                binding.zoomedArtworkPhoto.setImageResource(R.drawable.ic_camera)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(photoFileName: String): ArtworkZoomDialogFragment {
            val frag = ArtworkZoomDialogFragment()
            val args = Bundle().apply {
                putSerializable("PHOTO_URI", photoFileName)
            }
            frag.arguments = args
            return frag
        }
    }
}