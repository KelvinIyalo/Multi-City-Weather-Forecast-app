package com.kelvin.weatherappkelvin.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kelvin.weatherappkelvin.R
import com.kelvin.weatherappkelvin.common.Permissions.currentLocation
import kotlinx.coroutines.delay
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.Locale

object Utility {



    fun showDialog(
        context: Activity,
        message: String,
        onCancel: () -> Unit,
        onSettings: () -> Unit
    ) {
        val dialog = MaterialAlertDialogBuilder(context)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("return") { _, _ ->
                onCancel.invoke()
            }
            .setNegativeButton("Goto Settings") { _, _ ->
                onSettings.invoke()
            }
        dialog.show()
    }



    fun showMessage(message: String, context: View) {
        Snackbar.make(context, message, Snackbar.LENGTH_LONG).show()
    }

    const val REQUEST_CODE_LOCATION_PERMISSION = 100

    fun Activity.goToSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Handle any unexpected exceptions
            Log.d("settings Exception", e.toString())
        }
    }


    fun updateImageByPercentage(progress: Float): Int {
        return when {
            progress < 25 -> R.drawable.humidity_low_24dp
            progress < 50 -> R.drawable.humidity_mid
            else -> R.drawable.humidity_high_24dp
        }
    }

    fun showWeatherImage(word: String): Int {
        return when {
            word.contains("rain", ignoreCase = true) -> R.drawable.rain_drawable
            word.contains("cloudy", ignoreCase = true) -> R.drawable.new_cloud_drawable
            else -> R.drawable.sun_drawable
        }
    }

    fun showWeatherAnimation(word: String): Int {
        return when {
            word.contains("rain", ignoreCase = true) -> R.raw.rain
            word.contains("cloudy", ignoreCase = true) -> R.raw.cloud
            word.contains("sunny", ignoreCase = true) -> R.raw.sunny
            else -> R.raw.moon
        }
    }

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH)
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date ?:"")
    }

    fun formatTimeAndDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy hh:mm a", Locale.ENGLISH)
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date ?:"")
    }

    fun View.startSlideInAnimation() {
        val animation = AnimationUtils.loadAnimation(this.context, R.anim.slide_in_left_right)
        startAnimation(animation)
    }

     suspend fun waitForLocation(maxRetries: Int = 3, delayMillis: Long = 3000L): Location? {
        var retries = 0

        while (retries < maxRetries) {
            val location = currentLocation
            if (location != null) {
                return location
            }
            retries++
            delay(delayMillis)
        }

        return null // Return null if all retries fail
    }

}