package com.example.freebite2.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.freebite2.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MapsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Call method to request location updates
        requestLocationUpdates()

        // Additional logic for your MapsActivity goes here
    }

    private fun requestLocationUpdates() {
        // Check if location permission is granted
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            return
        }

        // Request location updates
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Handle location update success
                if (location != null) {
                    // Use the location data as needed
                    Toast.makeText(
                        this@MapsActivity,
                        "Latitude: ${location.latitude}, Longitude: ${location.longitude}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@MapsActivity, DashboardActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MapsActivity, "Location not found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener { e ->
                // Handle location update failure
                Toast.makeText(
                    this@MapsActivity,
                    "Error getting location: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1001
    }
}