package com.mark.weatherlogger.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.mark.weatherlogger.R
import com.mark.weatherlogger.databinding.ActivityMainBinding
import com.mark.weatherlogger.databinding.DialogDetailsBinding
import com.mark.weatherlogger.ui.base.BaseMVVMActivity
import com.mark.weatherlogger.view_models.MainViewModel

class MainActivity : BaseMVVMActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    private val permissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.all { map -> map.value })
            refreshData()
    }

    private lateinit var locationClient: FusedLocationProviderClient

    override fun initDataBindingVariables() {
        binding.imageUpdate.setOnClickListener {
            refreshData()
        }
        binding.buttonMore.setOnClickListener {
            showMoreDialog()
        }
    }

    private fun showMoreDialog() {
        val dialog = Dialog(this)
        val dialogBinding = DialogDetailsBinding.inflate(layoutInflater)
        dialogBinding.data = viewModel.weatherData.value?.details

        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    override fun initViewModelVariables() {
        viewModel.isNetworkAvailable.observe(this, {
            if(it)
                refreshData()
        })
    }

    private fun initNetworkListener() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
                NetworkRequest.Builder()
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        .build(), object : ConnectivityManager.NetworkCallback(){

            private val availableNetworks = hashSetOf<Network>()

            override fun onAvailable(network: Network) {
                availableNetworks.add(network)
                runOnUiThread {
                    viewModel.changeNetworkState(availableNetworks.size > 0)
                }
            }

            override fun onUnavailable() {
                runOnUiThread {
                    viewModel.changeNetworkState(false)
                }
            }

            override fun onLost(network: Network) {
                availableNetworks.remove(network)
                runOnUiThread {
                    viewModel.changeNetworkState(availableNetworks.size > 0)
                }
            }
        })
    }

    override fun assignVMToBinding() {
        binding.viewModel = viewModel
    }

    override fun initOthers() {
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        initNetworkListener()
    }

    @SuppressLint("MissingPermission")
    private fun refreshData() {
        if(!checkPermissions()) {
            requestPermissions()
            return
        }

        locationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token).addOnSuccessListener {
            if(it == null)
                Toast.makeText(this, "Cannot get location", Toast.LENGTH_LONG).show()
            else
                viewModel.updateInfo(it.latitude, it.longitude)
        }
    }

    private fun requestPermissions(){
        permissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

}