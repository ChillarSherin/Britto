package com.chillarcards.britto.ui


import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chillarcards.britto.databinding.ActivityNetworkErrorBinding
import com.chillarcards.britto.utills.ConnectivityReceiver
import com.chillarcards.britto.utills.NetworkHelper

/**
 * Created by Sherin on 20-02-2024.
 */
class NetworkErrorActivity : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityReceiverListener {

    lateinit var binding: ActivityNetworkErrorBinding

    companion object {
        var active = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNetworkErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        binding.retryButton.setOnClickListener {
            val networkHelper = NetworkHelper(this)
            if (networkHelper.isNetworkConnected())
                finish()
        }
    }

    override fun onResume() {
        super.onResume()
        active = true
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
        active = false
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected)
            finish()
    }
}