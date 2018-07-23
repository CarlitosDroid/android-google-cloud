package com.carlitosdroid.androidgooglecloud.ui.photo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.carlitosdroid.androidgooglecloud.R
import com.carlitosdroid.androidgooglecloud.ui.commons.AppSettingsDialogFragment
import android.net.Uri
import android.provider.Settings

import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity(), AppSettingsDialogFragment.OnCameraRationaleListener {

    override fun onAccept() {
        openAppSettings()
    }

    companion object {
        const val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1
        const val REQUEST_TO_MEDIA = 2
    }

    private var readExternalFilePermission = Manifest.permission.READ_EXTERNAL_STORAGE
    private var showCustomReadPermissionDialog = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        setSupportActionBar(toolbar)

        fabGallery.setOnClickListener {
            validatePermission(readExternalFilePermission)
        }
    }

    private fun validatePermission(manifestPermission: String) {
        if (ContextCompat.checkSelfPermission(this, manifestPermission)
                == PackageManager.PERMISSION_GRANTED) {
            openGalleryExternalApp()
        } else {
            Log.e("VEAMOSS", "VEAMOSSSS $showCustomReadPermissionDialog")
            if (showCustomReadPermissionDialog) {
                showAppSettingsDialogFragment("Storage")
            } else {
                requestPermissions()
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(readExternalFilePermission),
                REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
    }

    private fun showAppSettingsDialogFragment(message: String) {
        AppSettingsDialogFragment.newInstance(message).show(supportFragmentManager, "tag_app_settings")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE && readExternalFilePermission
                == Manifest.permission.READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGalleryExternalApp()
            } else {
                // shouldShowRequestPermissionRationale return false if the user check "Don't ask again" or "Permission disabled"
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    showCustomReadPermissionDialog = true
                }
            }
        }
    }

    private fun openGalleryExternalApp() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQUEST_TO_MEDIA)
    }

    private fun openAppSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}
