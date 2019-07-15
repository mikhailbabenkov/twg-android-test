package nz.co.warehouseandroidtest.ui.permissions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings

import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer

import nz.co.warehouseandroidtest.domain.utils.PermissionChecker
import nz.co.warehouseandroidtest.R
import nz.co.warehouseandroidtest.domain.utils.viewModelProvider
import nz.co.warehouseandroidtest.ui.BaseActivity

class PermissionActivity : BaseActivity() {

    val PACKAGE_URL_SCHEME = "package:"

    val PERMISSION_REQUEST_CODE = 0

    lateinit var viewModel: PermissionViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        viewModel = viewModelProvider(viewModelFactory)
        viewModel.permissions = intent.getStringArrayExtra(PERMISSION_EXTRA_FLAG)
        observeData()
    }

    private fun observeData() {
        viewModel.isAllPermissionsGranted.observe(this, Observer {
            if(it) allPermissionsGranted() else showMissingPermissionDialog()
        })
        viewModel.shouldCheckPermissions.observe(this, Observer {
            if(it) {
                requestPermissions(viewModel.permissions)
            } else {
                allPermissionsGranted()
            }
        })
    }

    public override fun onResume() {
        super.onResume()
        viewModel.checkForMissingPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if(PERMISSION_REQUEST_CODE == requestCode) {
            viewModel.checkPermissionResults(grantResults)
        }
    }

    fun requestPermissions(permissions: Array<String>) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
    }

    private fun allPermissionsGranted() {
        setResult(PERMISSION_GRANTED)
        finish()
    }

    private fun showMissingPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Help")
        builder.setMessage("Current app lacks necessary permissions. \n\nPlease click \"Settings\" - \"Permission\" - to grant permissions. \n\nThen click back button twice to return.")

        builder.setNeutralButton("Quit") { dialog, which ->
            setResult(PERMISSION_DENIED)
            finish()
        }

        builder.setPositiveButton("Settings") { dialog, which -> startAppSettings() }

        builder.show()
    }


    fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse(PACKAGE_URL_SCHEME + packageName)
        startActivity(intent)
    }

    companion object {

        var PERMISSION_GRANTED = 0
        var PERMISSION_DENIED = 1

        val PERMISSION_EXTRA_FLAG = "nz.co.warehouseandroidtest.permission.extra_permission"

        fun startActivityForResult(activity: Activity, requestCode: Int, permissions: Array<String>) {
            val intent = Intent()
            intent.setClass(activity, PermissionActivity::class.java)
            intent.putExtra(PERMISSION_EXTRA_FLAG, permissions)
            ActivityCompat.startActivityForResult(activity, intent, requestCode, null)
        }
    }
}
