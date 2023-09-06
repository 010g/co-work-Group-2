package app.appworks.school.stylish

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.ZoomControls
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import app.appworks.school.stylish.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var currentLocation:Location
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private val permissionCode= 101
    private lateinit var myMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var zoomControls: ZoomControls

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "附近店家"

        zoomControls = findViewById(R.id.zoomControls)


        // Manually set the initial location
        currentLocation = Location("").apply {
            latitude = 25.03850903955603
            longitude = 121.53235946791851
        }

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }

        val task = fusedLocationProvider.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(
                    this,
                    currentLocation.latitude.toString() + "" + currentLocation.longitude.toString(),
                    Toast.LENGTH_SHORT
                ).show()

                val supporMapFragment = (supportFragmentManager.findFragmentById(R.id.myMap) as
                        SupportMapFragment?)!!
                supporMapFragment.getMapAsync(this)
            }
        }
    }
    private fun vectorToBitmap(vectorDrawable: Drawable): BitmapDescriptor {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val currentMarkerOptions  = MarkerOptions().position(latLng).title("I am here!!")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f))
        googleMap?.addMarker(currentMarkerOptions)

        // Add more locations
        val locations = listOf(
            LatLng(25.037911620399765, 121.5324229778428) to "STYLiSH 仁愛旗艦門市",
            LatLng(24.48108077729567, 118.12400676438334) to "STYLiSH 廈門門市",
            LatLng(30.2104898831962, 120.16136532037513) to "STYLiSH 杭州門市",
            LatLng(35.208591844898116, 129.07072927270588) to "STYLiSH 釜山門市",
            LatLng(25.063788091488846, 121.59015238268216) to "STYLiSH 內湖門市",
            LatLng(24.11801646091066, 120.64038149016848) to "STYLiSH 台中門市",
            LatLng(22.98597920923129, 120.24297266189228) to "STYLiSH 台南門市"
        )

        // Customized BitmapDescriptor for heart-shaped icon
        val heartDrawable = ContextCompat.getDrawable(this, R.drawable.ic_red_heart) ?: return
        val heartIcon = vectorToBitmap(heartDrawable)

        for ((position, title) in locations) {
            val markerOptions = MarkerOptions().position(position).title(title).icon(heartIcon)
            googleMap?.addMarker(markerOptions)
        }

        myMap = googleMap

        // Set up zoom controls
        setupZoomControls()

    }

    private fun setupZoomControls() {
        zoomControls.setOnZoomInClickListener {
            myMap.animateCamera(CameraUpdateFactory.zoomIn())
        }

        zoomControls.setOnZoomOutClickListener {
            myMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            permissionCode -> if (grantResults.isEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }
        }
    }
}