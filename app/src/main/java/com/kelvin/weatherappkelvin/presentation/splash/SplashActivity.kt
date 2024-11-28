package com.kelvin.weatherappkelvin.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kelvin.weatherappkelvin.R
import com.kelvin.weatherappkelvin.common.Permissions
import com.kelvin.weatherappkelvin.common.Utility
import com.kelvin.weatherappkelvin.common.Utility.goToSettings
import com.kelvin.weatherappkelvin.presentation.home_root.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (!Permissions.hasPermission(this)) {
            Permissions.requestPermission(this)
        } else {
            lifecycleScope.launch {
                delay(2000L)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        lifecycleScope.launch {
            Permissions.requestLocation(this@SplashActivity)
            delay(1200L)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            // Permission is permanently denied, show rationale
            Utility.showDialog(
                this,
                "Please grant the location permissions in settings",
                onSettings = {
                    goToSettings()
                }, onCancel = {
                    finish()
                }
            )
        } else {
            Permissions.requestPermission(this)
        }
    }
}