package co.anbora.labs.sample

import android.Manifest
import android.content.IntentSender.SendIntentException
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.anbora.component.location.CallbackLocation
import co.anbora.component.location.LocationComponent
import co.anbora.component.location.LocationSettings
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var locationComponent: LocationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        locationComponent = LocationComponent.Builder()
            .locationRequest(10000, 10000, 100)
            .build(this)

        locationComponent
            .onLastLocation(LastLocationCallback())
            .whenLocationChange()
            .onLocationChanged(object : CallbackLocation {
                override fun onLocationResult(location: Location?) {
                    location?.run {
                        current_location.text = getString(R.string.location, this.latitude, this.longitude)
                    }
                }

                override fun onLocationError() {
                }
            }).attachState().observe(lifecycle)

    }

    private fun requestPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Toast.makeText(applicationContext, "Granted", Toast.LENGTH_LONG).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
            .check()
    }

    inner class LastLocationCallback: CallbackLocation {
        override fun onLocationResult(location: Location?) {
            location?.run {
                last_location.text = getString(R.string.location, this.latitude, this.longitude)
            }
        }

        override fun onLocationError() {
        }
    }
}
