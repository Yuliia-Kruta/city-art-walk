package com.example.csc202assignment

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.doOnLayout
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
import com.example.csc202assignment.Utils.getScaledBitmap
import com.example.csc202assignment.databinding.FragmentArtworkDetailBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date


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

    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { didTakePhoto: Boolean ->
        if (didTakePhoto && photoName != null) {
            artworkDetailViewModel.updateArtwork { oldArtwork ->
                oldArtwork.copy(photoFileName = photoName)
            }
        }
    }

    private var photoName: String? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLocation()
        } else {
            Log.d("GPS", "Permission denied.")
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
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

            artworkCamera.setOnClickListener {
                photoName = "IMG_${Date()}.JPG"
                val photoFile = File(requireContext().applicationContext.filesDir, photoName)
                val photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.csc202assignment.fileprovider",
                    photoFile
                )

                takePhoto.launch(photoUri)
            }

            val captureImageIntent = takePhoto.contract.createIntent(
                requireContext(),
                Uri.parse("")
            )
            artworkCamera.isEnabled = canResolveIntent(captureImageIntent)

            artworkGetGps.setOnClickListener {
                artworkCoordinates.text = getString(R.string.getting_location)
                getLocation()
            }
            binding.artworkShowMap.setOnClickListener {
                val artwork = artworkDetailViewModel.artwork.value
                if (artwork?.latitude != null && artwork.longitude != null) {
                    val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                        putExtra("LATITUDE", artwork.latitude)
                        putExtra("LONGITUDE", artwork.longitude)
                    }
                    startActivity(intent)
                } else {
                    Log.d("GPS", "Coordinates not available.")
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
            artworkDate.text = Utils.formatArtworkDate(artwork.date)
            artworkDate.setOnClickListener {
                findNavController().navigate(
                    ArtworkDetailFragmentDirections.selectDate(artwork.date)
                )
            }

            updatePhoto(artwork.photoFileName)

            artworkPhoto.setOnClickListener {
                artwork.photoFileName?.let { photoFileName ->
                    ArtworkZoomDialogFragment.newInstance(photoFileName)
                        .show(childFragmentManager, "zoomedArtwork")
                }
            }

            if (artwork.latitude != null && artwork.longitude != null) {
                val coordinatesText = "Lat: ${artwork.latitude}, Lon: ${artwork.longitude}"
                artworkCoordinates.text = coordinatesText
                artworkShowMap.isEnabled = true
            } else {
                artworkCoordinates.text = getString(R.string.coordinates_not_available)
                artworkShowMap.isEnabled = false
            }

        }
    }

    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }

    private fun updatePhoto(photoFileName: String?) {
        if (binding.artworkPhoto.tag != photoFileName) {
            val photoFile = photoFileName?.let {
                File(requireContext().applicationContext.filesDir, it)
            }

            if (photoFile?.exists() == true) {
                binding.artworkPhoto.doOnLayout { measuredView ->
                    val scaledBitmap = getScaledBitmap(
                        photoFile.path,
                        measuredView.width,
                        measuredView.height
                    )
                    binding.artworkPhoto.setImageBitmap(scaledBitmap)
                    binding.artworkPhoto.tag = photoFileName
                }
            } else {
                binding.artworkPhoto.setImageBitmap(null)
                binding.artworkPhoto.tag = null
            }
        }
    }


    private fun getLocation() {
        when {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("GPS", "Have permissions. Try to get a location")
                if (GoogleApiAvailability.getInstance()
                        .isGooglePlayServicesAvailable(requireContext()) == ConnectionResult.SUCCESS) {
                    fusedLocationProviderClient.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        object : CancellationToken() {
                            override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                                CancellationTokenSource().token

                            override fun isCancellationRequested() = false
                        }
                    ).addOnSuccessListener { location: Location? ->
                        location?.let {
                            artworkDetailViewModel.updateArtwork { oldArtwork ->
                                oldArtwork.copy(latitude = location.latitude, longitude = location.longitude)
                            }
                            binding.artworkCoordinates.text = "Lat: ${location.latitude}, Lon: ${location.longitude}"
                        }
                        Log.d("GPS", "Got a location: $location")
                    }.addOnFailureListener {
                        binding.artworkCoordinates.text = getString(R.string.coordinates_not_available)
                        Log.d("GPS", "Failed to get location.")
                }
                    }
            }
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }


}